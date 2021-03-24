package com.stock.soft.socksoft.daoImpl;

import com.stock.soft.socksoft.Dto.FilterDto;
import com.stock.soft.socksoft.Dto.PurchaseDataDto;
import com.stock.soft.socksoft.Dto.PurchaseResponseDto;
import com.stock.soft.socksoft.Dto.TransactionResponse;
import com.stock.soft.socksoft.dao.PurchaseDao;
import com.stock.soft.socksoft.model.CashBook;
import com.stock.soft.socksoft.model.Commission;
import com.stock.soft.socksoft.model.Purchase;
import com.stock.soft.socksoft.model.StockLedger;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public class PurchaseDaoImpl implements PurchaseDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Override
    public TransactionResponse createPurchase(Purchase purchase) {
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();

        try {
            String transType ="BUY";
            BigDecimal commissionRate = new BigDecimal(entityManager.createNativeQuery("select commission_amount from commission").getSingleResult().toString());
            BigDecimal amount = purchase.getPrice().multiply(new BigDecimal(purchase.getQty()));
            BigDecimal commission = amount.divide(new BigDecimal(100)).multiply(commissionRate);
            BigDecimal netAmount = amount.add(commission);
            purchase.setCommission(commission);
            purchase.setTotalAmount(amount);
            purchase.setNetAmount(netAmount);
            em.persist(purchase);

            StockLedger stockLedger = new StockLedger();
            stockLedger.setUserId(purchase.getUserId());
            stockLedger.setStockIn(purchase.getQty());
            stockLedger.setOutStock(new BigInteger("0"));
            stockLedger.setTransDate(purchase.getTransDate());
            stockLedger.setTransType(transType);
            stockLedger.setTransId(purchase.getId());
            stockLedger.setOutAmount(netAmount);
            stockLedger.setInAmount(new BigDecimal(0));
            stockLedger.setLotNumber(UUID.randomUUID().toString());
            stockLedger.setCompany(purchase.getCompany());
            stockLedger.setPrice(purchase.getPrice());
            stockLedger.setTotalAmount(amount);
            stockLedger.setCommissionRate(commissionRate);
            stockLedger.setCommissionAmount(commission);
            stockLedger.setNetAmount(netAmount);
            em.persist(stockLedger);

            CashBook cashBook = new CashBook();
            cashBook.setCompany(purchase.getCompany());
            cashBook.setInAmount(new BigDecimal(0));
            cashBook.setOutAmount(netAmount);
            cashBook.setUserId(purchase.getUserId());
            cashBook.setTransDate(purchase.getTransDate());
            cashBook.setTransRefId(purchase.getId());
            cashBook.setTransType(transType);
            em.persist(cashBook);

            em.getTransaction().commit();
            transactionResponse.setMessage("Purchase Saved Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(purchase);
        }catch (Exception e){
            em.getTransaction().rollback();
            transactionResponse.setMessage("Purchase Saving Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(e);
        }
        return transactionResponse;
    }

    @Override
    public TransactionResponse UpdatePurchase(Purchase purchase) {
        EntityTransaction transaction = entityManager.getTransaction();
        TransactionResponse transactionResponse = new TransactionResponse();
        transaction.begin();
        try {
            Purchase purchase1 = entityManager.find(Purchase.class,purchase.getId());
            StockLedger stockLedger = (StockLedger) entityManager.createNativeQuery("select * from stock_ledger where trans_id=? and trans_type='BUY' ",StockLedger.class)
                    .setParameter(1,purchase1.getId()).getSingleResult();
            stockLedger.setUserId(purchase.getUserId());
            stockLedger.setStockIn(purchase.getQty());
            stockLedger.setOutStock(new BigInteger("0"));
            stockLedger.setTransDate(purchase.getTransDate());
            stockLedger.setTransType("BUY");
            stockLedger.setTransId(purchase.getId());
            entityManager.persist(stockLedger);

            BigDecimal commission = new BigDecimal(entityManager.createNativeQuery("select commission_amount from commission").getSingleResult().toString());

            purchase1.setCommission(commission);
            purchase1.setCompany(purchase.getCompany());
            purchase1.setPrice(purchase.getPrice());
            purchase1.setQty(purchase.getQty());
            purchase1.setTransDate(purchase.getTransDate());
            purchase1.setUserId(purchase.getUserId());
            entityManager.persist(purchase1);
            transaction.commit();
            transactionResponse.setMessage("Purchase Updated Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(purchase);
        }catch (Exception e){
            transaction.rollback();
            transactionResponse.setMessage("Purchase Updating Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(e);
        }
        return transactionResponse;
    }


    @Override
    public TransactionResponse DeletePurchase(String purchaseId,String userId) {

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();
        try {
            Purchase purchase = em.find(Purchase.class,purchaseId);
            StockLedger stockLedger = (StockLedger) em.createNativeQuery("select * from stock_ledger where trans_id=? and user_id=?  and trans_type='BUY' ",StockLedger.class)
                    .setParameter(1,purchaseId)
                    .setParameter(2,userId)
                    .getSingleResult();
            em.remove(stockLedger);

            em.createNativeQuery("delete  from cash_book where user_id=? and trans_ref_id=? and trans_type='BUY' " )
                    .setParameter(1,userId)
                    .setParameter(2,purchaseId)
                    .executeUpdate();

            em.remove(purchase);
            em.getTransaction().commit();
            transactionResponse.setMessage("Purchase Deleted Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(purchase);
        }catch (Exception e){
            em.getTransaction().rollback();
            transactionResponse.setMessage("Purchase Deleting Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(e);
        }
        return transactionResponse;
    }

    @Override
    public PurchaseResponseDto getPurchaseByUserId(FilterDto filterDto) {
        PurchaseResponseDto purchaseResponseDto = new PurchaseResponseDto();
        List<PurchaseDataDto> purchaseDataDtoList  = entityManager.createNativeQuery("select  DATE_FORMAT(p.trans_date,'%Y-%m-%d') as trans_date,p.id,c.code as company, p.total_amount,p.user_id,p.qty,p.price,p.commission,p.net_amount from purchase p inner join  companies c on p.company=c.id where p.user_id = ? AND DATE(p.trans_date) between DATE(?) and  DATE(?) ", PurchaseDataDto.class)
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
