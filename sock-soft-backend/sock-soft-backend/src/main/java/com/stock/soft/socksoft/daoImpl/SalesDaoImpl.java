package com.stock.soft.socksoft.daoImpl;

import com.stock.soft.socksoft.Dto.*;
import com.stock.soft.socksoft.dao.SalesDao;
import com.stock.soft.socksoft.model.CashBook;
import com.stock.soft.socksoft.model.Sales;
import com.stock.soft.socksoft.model.StockLedger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Transactional
@Repository
public class SalesDaoImpl implements SalesDao {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public TransactionResponse CreateSales(Sales sales) {

        EntityManager em = entityManagerFactory.createEntityManager();
        TransactionResponse transactionResponse = new TransactionResponse();
        em.getTransaction().begin();
        try {
        // check stock Lots Availability
        List<ChildStockDto> stockDtoList =  entityManager.createNativeQuery("call getStocksAvailableByUserByCommanies(?,?)",ChildStockDto.class)
                .setParameter(1,sales.getUserId())
                .setParameter(2,sales.getCompany())
                .getResultList();

        AtomicReference<BigInteger> pendingQty = new AtomicReference<>(sales.getQty());
        // check current stock available for sales qty
        BigInteger availableStock = stockDtoList.stream()
                .map(x -> x.getQty())    // map
                .reduce(BigDecimal.ZERO, BigDecimal::add).toBigInteger();
        if(availableStock.compareTo(pendingQty.get())<0){
            throw  new Exception("Insufficent Stock");
        }

        // stock deductions
            AtomicReference<BigDecimal> totalAmountForSales= new AtomicReference<>(new BigDecimal(0));
            AtomicReference<BigDecimal> commissionAmountSales= new AtomicReference<>(new BigDecimal(0));
            AtomicReference<BigDecimal> netAmountSales= new AtomicReference<>(new BigDecimal(0));


            List<StockLedger> stockLedgerList =  new ArrayList<>();
            List<CashBook> cashBookList = new ArrayList<>();
            AtomicReference<BigInteger> saveQty = new AtomicReference<>(sales.getQty());
        stockDtoList.forEach(lotsStock -> {
            if(pendingQty.get().compareTo(BigInteger.ZERO)>0){
                 if(pendingQty.get().compareTo(lotsStock.getQty().toBigInteger())>0){
                     pendingQty.set(pendingQty.get().subtract(lotsStock.getQty().toBigInteger()));
                     saveQty.set(lotsStock.getQty().toBigInteger());
                 }else{
                     saveQty.set(pendingQty.get());
                     pendingQty.set(BigInteger.ZERO);
                 }

                String lotNumber = lotsStock.getLotNumber();
                // get purchase Id
                String purchaseId =null;
                try {
                    String sql="select trans_id from stock_ledger where lot_number=? and trans_type='BUY'";
                    purchaseId = entityManager.createNativeQuery(sql).setParameter(1,lotNumber).getSingleResult().toString();
                }catch (Exception e){
                    String sql="select trans_id from stock_ledger where lot_number=? and trans_type='DIVIDEND'";
                    purchaseId = entityManager.createNativeQuery(sql).setParameter(1,lotNumber).getSingleResult().toString();
                }

                sales.setPurchaseId(purchaseId);

                BigDecimal commissionRate = new BigDecimal(0);
                BigDecimal commission = BigDecimal.ZERO;
                if(checkCommissionPlayable(sales.getPurchaseId())){
                    commissionRate = new BigDecimal(entityManager.createNativeQuery("select commission_amount from commission").getSingleResult().toString());
                    commission = sales.getPrice().multiply(new BigDecimal(saveQty.get())).divide(new BigDecimal(100)).multiply(commissionRate);

                }

                BigDecimal totalAmount = sales.getPrice().multiply(new BigDecimal(saveQty.get()));
                totalAmountForSales.set(totalAmountForSales.get().add(totalAmount));
                BigDecimal netAmount = totalAmount.subtract(commission);
                commissionAmountSales.set(commissionAmountSales.get().add(commission));
                netAmountSales.set(netAmountSales.get().add(netAmount));

                StockLedger stockLedger = new StockLedger();
                stockLedger.setUserId(sales.getUserId());
                stockLedger.setCommissionRate(commissionRate);
                stockLedger.setCommissionAmount(commission);
                stockLedger.setLotNumber(lotNumber);
                stockLedger.setPrice(sales.getPrice());
                stockLedger.setOutStock(saveQty.get());
                stockLedger.setStockIn(BigInteger.ZERO);
                stockLedger.setCompany(sales.getCompany());
                stockLedger.setTransDate(sales.getTransDate());
                stockLedger.setTransType("SALE");
                stockLedger.setTotalAmount(totalAmount);
                stockLedger.setNetAmount(netAmount);
                stockLedger.setOutAmount(netAmount);
                stockLedger.setInAmount(BigDecimal.ZERO);
                stockLedger.setTransId(sales.getId());
                stockLedgerList.add(stockLedger);

                CashBook cashBook = new CashBook();
                cashBook.setCompany(sales.getCompany());
                cashBook.setInAmount(netAmount);
                cashBook.setOutAmount(new BigDecimal(0));
                cashBook.setUserId(sales.getUserId());
                cashBook.setTransDate(sales.getTransDate());
                cashBook.setTransType("SALE");

                cashBookList.add(cashBook);


            }
        });
            // save to sales
            sales.setCommission(commissionAmountSales.get());
            sales.setTotalAmount(totalAmountForSales.get());
            sales.setNetAmount(netAmountSales.get());
            em.persist(sales);

            // save to stock Ledger
            stockLedgerList.forEach(stockLedger -> {
                stockLedger.setTransId(sales.getId());
                em.persist(stockLedger);
            });
            // save to cashbook
            cashBookList.forEach(cashBook -> {
                cashBook.setTransRefId(sales.getId());
                em.persist(cashBook);
            });

            em.getTransaction().commit();
            transactionResponse.setMessage("Sales Saved Successfully !");
            transactionResponse.setStatus("SALE");
            transactionResponse.setResObj(sales);
        }catch (Exception e){
            em.getTransaction().rollback();
            transactionResponse.setMessage("Sales Saving Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(e);
        }
        return transactionResponse;
    }

    @Override
    public TransactionResponse UpdateSales(Sales sales) {
        EntityTransaction transaction = entityManager.getTransaction();
        TransactionResponse transactionResponse = new TransactionResponse();
        transaction.begin();
        try {
            Sales sales1 = entityManager.find(Sales.class,sales.getId());
            StockLedger stockLedger = (StockLedger) entityManager.createNativeQuery("select * from stock_ledger where trans_id=? and trans_type='SALE' ",StockLedger.class)
                    .setParameter(1,sales.getId()).getSingleResult();
            stockLedger.setUserId(sales.getUserId());
            stockLedger.setOutStock(sales.getQty());
            stockLedger.setStockIn(new BigInteger("0"));
            stockLedger.setTransDate(sales.getTransDate());
            stockLedger.setTransType("SALE");
            stockLedger.setTransId(sales.getId());
            entityManager.persist(stockLedger);

            sales1.setCompany(sales.getCompany());
            sales1.setPrice(sales.getPrice());
            sales1.setQty(sales.getQty());
            sales1.setTransDate(sales.getTransDate());
            sales1.setUserId(sales.getUserId());
            if(checkCommissionPlayable(sales.getPurchaseId())){
                BigDecimal commission = new BigDecimal(entityManager.createNativeQuery("select commission_amount from commission").getSingleResult().toString());
                sales1.setCommission(commission);
            }else {
                sales1.setCommission(new BigDecimal(0));
            }
            entityManager.persist(sales1);
            transaction.commit();
            transactionResponse.setMessage("Sales Updated Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(sales1);
        }catch (Exception e){
            transaction.rollback();
            transactionResponse.setMessage("Sales Updating Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(e);
        }
        return transactionResponse;
    }

    @Override
    public TransactionResponse DeleteSales(String salesId,String userId) {

        EntityManager em = entityManagerFactory.createEntityManager();
        TransactionResponse transactionResponse = new TransactionResponse();
        em.getTransaction().begin();
        try {
            Sales sales  = em.find(Sales.class,salesId);
            StockLedger stockLedger = (StockLedger) em.createNativeQuery("select * from stock_ledger where trans_id=? and  user_id= ? and trans_type='SALE' ",StockLedger.class)
                    .setParameter(1,salesId)
                    .setParameter(2,userId)
                    .getSingleResult();
            em.remove(stockLedger);

            em.createNativeQuery("delete  from cash_book where user_id=? and trans_ref_id=? and trans_type='SALE' " )
                    .setParameter(1,userId)
                    .setParameter(2,salesId)
                    .executeUpdate();

            em.remove(sales);
            em.getTransaction().commit();
            transactionResponse.setMessage("Sales Deleted Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(sales);
        }catch (Exception e){
            em.getTransaction().rollback();
            transactionResponse.setMessage("Sales Deleting Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(e);
        }
        return transactionResponse;
    }

    private boolean checkCommissionPlayable(String purchaseId){
        boolean status = true;
        int rows = Integer.parseInt(entityManager.createNativeQuery("select count(*) as c from purchase where DATE(trans_date)=DATE(NOW()) and id=? ")
                .setParameter(1,purchaseId)
                .getSingleResult().toString());

        if(rows>0){
            status = false;
        }
        return status;
    }

    @Override
    public PurchaseResponseDto getSalesByUserId(FilterDto filterDto) {
        PurchaseResponseDto purchaseResponseDto = new PurchaseResponseDto();
        List<PurchaseDataDto> purchaseDataDtoList  = entityManager.createNativeQuery("select  DATE_FORMAT(p.trans_date,'%Y-%m-%d') as trans_date,p.id,c.code as company, p.total_amount,p.user_id,p.qty,p.price,p.commission,p.net_amount from sales p inner join  companies c on p.company=c.id where p.user_id = ? AND DATE(p.trans_date) between DATE(?) and  DATE(?) ", PurchaseDataDto.class)
                .setParameter(1,filterDto.getUserId())
                .setParameter(2,filterDto.getFromDate())
                .setParameter(3,filterDto.getToDate())
                .getResultList();

        BigDecimal sum =purchaseDataDtoList.stream().map(x->x.getNetAmount()).reduce(BigDecimal.ZERO,BigDecimal::add);
        purchaseResponseDto.setPurchaseList(purchaseDataDtoList);
        purchaseResponseDto.setSumTotal(sum);
        return purchaseResponseDto;
    }
}
