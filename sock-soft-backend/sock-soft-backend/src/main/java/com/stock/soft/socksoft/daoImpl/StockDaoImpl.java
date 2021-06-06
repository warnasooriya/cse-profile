package com.stock.soft.socksoft.daoImpl;

import com.stock.soft.socksoft.Dto.*;
import com.stock.soft.socksoft.dao.StockDao;
import com.stock.soft.socksoft.model.*;
import com.stock.soft.socksoft.repository.CompanyRepository;
import com.stock.soft.socksoft.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@Transactional
@Repository
public class StockDaoImpl implements StockDao {
    private final static Logger logger = Logger.getLogger(StockDaoImpl.class.getName());



    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private DepositRepository depositRepository;
    @Override
    public List<StockSumDto> getAvailableStock(String userId) {
        return entityManager.createNativeQuery("call getStocksAvailableByUser(?)",StockSumDto.class)
                .setParameter(1,userId).getResultList();

    }

    @Override
    public List<ChildStockDto> getAvailableStockDetails(String userId, String companyId) {
        List<ChildStockDto> stockDtoList =  entityManager.createNativeQuery("call getStocksAvailableByUserByCommanies(?,?)",ChildStockDto.class)
                .setParameter(1,userId)
                .setParameter(2,companyId)
                .getResultList();
        return stockDtoList;
    }


    @Override
    public TransactionResponse saveDividend(Dividend dividend) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();

        try {
            String transType ="DIVIDEND";

            em.persist(dividend);

            if(dividend.getEquityAvailable() && dividend.getEquity().compareTo(new BigDecimal(0))>0){

                BigDecimal amount = dividend.getCurrentPrice().multiply(dividend.getEquity());
                BigDecimal netAmount =  amount;

                StockLedger stockLedger = new StockLedger();
                stockLedger.setUserId(dividend.getUserId());
                stockLedger.setStockIn(dividend.getEquity().toBigInteger());
                stockLedger.setOutStock(new BigInteger("0"));
                stockLedger.setTransDate(dividend.getDivDate());
                stockLedger.setTransType(transType);
                stockLedger.setTransId(dividend.getId());
                stockLedger.setOutAmount(netAmount);
                stockLedger.setInAmount(new BigDecimal(0));
                stockLedger.setLotNumber(UUID.randomUUID().toString());
                stockLedger.setCompany(dividend.getCompany());
                stockLedger.setPrice(dividend.getCurrentPrice());
                stockLedger.setTotalAmount(amount);
                stockLedger.setCommissionRate(new BigDecimal(0));
                stockLedger.setCommissionAmount(new BigDecimal(0));
                stockLedger.setNetAmount(netAmount);
                em.persist(stockLedger);

            }

            if(dividend.getCashAvailable() && dividend.getCashAmount().compareTo(new BigDecimal(0))>0){
                CashBook cashBook = new CashBook();
                cashBook.setCompany(dividend.getCompany());
                cashBook.setInAmount(dividend.getCashAmount());
                cashBook.setOutAmount(new BigDecimal(0));
                cashBook.setUserId(dividend.getUserId());
                cashBook.setTransDate(dividend.getDivDate());
                cashBook.setTransRefId(dividend.getId());
                cashBook.setTransType(transType);
                em.persist(cashBook);
            }

            em.getTransaction().commit();
            transactionResponse.setMessage("Dividend Saved Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(dividend);

        }catch (Exception ex){
            logger.warning(ex.getMessage());
            System.out.println(ex);
            em.getTransaction().rollback();
            transactionResponse.setMessage("Dividend Saving Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(ex);
        }
        return transactionResponse;
    }


    @Override
    public TransactionResponse saveDeposit(Deposit deposit) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();

        try {
            String transType = "DEPOSIT";
            em.persist(deposit);
            CashBook cashBook = new CashBook();
            cashBook.setInAmount(deposit.getAmount());
            cashBook.setOutAmount(new BigDecimal(0));
            cashBook.setUserId(deposit.getUserId());
            cashBook.setTransDate(deposit.getDate());
            cashBook.setTransRefId(deposit.getId());
            cashBook.setTransType(transType);
            em.persist(cashBook);

            em.getTransaction().commit();
            transactionResponse.setMessage("Deposit Saved Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(deposit);

        }catch (Exception ex){
            logger.warning(ex.getMessage());
            System.out.println(ex);
            em.getTransaction().rollback();
            transactionResponse.setMessage("Deposit Saving Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(ex);
        }
        return  transactionResponse;
    }


    @Override
    public List<DepositDto> getAllDepositByUser(String userId) {
        String sql="SELECT id,amount,DATE_FORMAT(date,\"%Y-%m-%d\") AS date ,user_id FROM deposit where user_id=? order by date desc";
        List<DepositDto> depositDtoList = entityManager.createNativeQuery(sql,DepositDto.class)
                .setParameter(1,userId)
                .getResultList();
        return depositDtoList;
    }

    @Override
    public List<DividendDto> loadDividendToTable(String userId) {
        String sql="SELECT d.id,c.code as company,DATE_FORMAT(d.div_date,\"%Y-%m-%d\") as div_date,DATE_FORMAT(d.divxd_date,\"%Y-%m-%d\") as xdate,d.current_price as price,d.equity,d.cash_amount as amount FROM  divident d inner join companies c ON d.company=c.id where d.user_id=?";
        return entityManager.createNativeQuery(sql,DividendDto.class)
                .setParameter(1,userId)
                .getResultList();
    }

    @Override
    public TransactionResponse deleteDividend(String id, String userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();
        try {
            Dividend dividend =em.find(Dividend.class,id);
            if(dividend.getCashAvailable()){
                String sql="delete from cash_book where user_id=? and trans_type='DIVIDEND' and trans_ref_id=? ";
                em.createNativeQuery(sql)
                        .setParameter(1,userId)
                        .setParameter(2,id)
                        .executeUpdate();
            }

            if(dividend.getEquityAvailable()) {
                String sql = "delete from stock_ledger where user_id=? and trans_type='DIVIDEND' and trans_id=? ";
                em.createNativeQuery(sql)
                        .setParameter(1, userId)
                        .setParameter(2, id)
                        .executeUpdate();
            }

            String sql="delete from divident where user_id=? and  id=? ";
            em.createNativeQuery(sql)
                    .setParameter(1,userId)
                    .setParameter(2,id)
                    .executeUpdate();

            em.getTransaction().commit();
            transactionResponse.setMessage("Dividend Deleted Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(dividend);

        }catch (Exception ex){
            logger.warning(ex.getMessage());
            System.out.println(ex);
            em.getTransaction().rollback();
            transactionResponse.setMessage("Dividend Deleting Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(ex);
        }
        return transactionResponse;
    }

    @Override
    public TransactionResponse deleteDeposit(String id, String userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();
        try {

            String sql="delete from cash_book where user_id=? and trans_type='DEPOSIT' and trans_ref_id=? ";
            em.createNativeQuery(sql)
                    .setParameter(1,userId)
                    .setParameter(2,id)
                    .executeUpdate();
            Dividend dividend =em.find(Dividend.class,id);

            sql="delete from deposit where user_id=? and  id=? ";
            em.createNativeQuery(sql)
                    .setParameter(1,userId)
                    .setParameter(2,id)
                    .executeUpdate();

            em.getTransaction().commit();
            transactionResponse.setMessage("Deposit Deleted Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(dividend);
        }catch (Exception ex){
            logger.warning(ex.getMessage());
            System.out.println(ex);
            em.getTransaction().rollback();
            transactionResponse.setMessage("Deposit Deleting Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(ex);
        }
        return transactionResponse;
    }

    @Override
    public PreviousDeposits getPreviousDeposits(String userId) {
        String sql="SELECT uuid() id, (select ifnull(sum(amount),0)  from deposit where user_id=?) as total_deposit, ifnull(sum(amount),0) as current_month_deposit FROM deposit where  DATE_FORMAT(date,\"%Y-%m\")  =  DATE_FORMAT(current_date(),\"%Y-%m\")  and user_id=?";
        PreviousDeposits previousDeposits = new PreviousDeposits();
        List<PreviousDeposits> lst = entityManager.createNativeQuery(sql,PreviousDeposits.class)
                .setParameter(1,userId)
                .setParameter(2,userId)
                .getResultList();
        return  lst.get(0);
    }

    @Override
    public TransactionResponse saveStockSplit(Split split) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();
        try {
            em.persist(split);

            // check stock Lots Availability
            List<ChildStockDto> stockDtoList =  entityManager.createNativeQuery("call getStocksAvailableByUserByCommanies(?,?)",ChildStockDto.class)
                    .setParameter(1,split.getUserId())
                    .setParameter(2,split.getCompany())
                    .getResultList();

            // stock deductions
            List<StockLedger> stockLedgerList =  new ArrayList<>();
            AtomicReference<BigInteger> saveQty = new AtomicReference<>(split.getFutureQty());
            stockDtoList.forEach(lotsStock -> {
                    saveQty.set(lotsStock.getQty().toBigInteger());
                    String lotNumber = lotsStock.getLotNumber();
                    StockLedger stockLedger = new StockLedger();
                    stockLedger.setUserId(split.getUserId());
                    stockLedger.setCommissionRate(new BigDecimal(0));
                    stockLedger.setCommissionAmount(new BigDecimal(0));
                    stockLedger.setLotNumber(lotNumber);
                    stockLedger.setPrice(split.getCurrentPrice());
                    stockLedger.setOutStock(saveQty.get());
                    stockLedger.setStockIn(BigInteger.ZERO);
                    stockLedger.setCompany(split.getCompany());
                    stockLedger.setTransDate(split.getSplitDate());
                    stockLedger.setTransType("SPLIT-OUT");
                    stockLedger.setTotalAmount(new BigDecimal(0));
                    stockLedger.setNetAmount(new BigDecimal(0));
                    stockLedger.setOutAmount(new BigDecimal(0));
                    stockLedger.setInAmount(BigDecimal.ZERO);
                    stockLedger.setTransId(split.getId());
                    stockLedgerList.add(stockLedger);
            });

            // save to stock Ledger
            stockLedgerList.forEach(stockLedger -> {
                stockLedger.setTransId(split.getId());
                em.persist(stockLedger);
            });

            StockLedger stockLedger = new StockLedger();
            stockLedger.setUserId(split.getUserId());
            stockLedger.setStockIn(split.getFutureQty());
            stockLedger.setOutStock(new BigInteger("0"));
            stockLedger.setTransDate(split.getSplitDate());
            stockLedger.setTransType("SPLIT-IN");
            stockLedger.setTransId(split.getId());
            stockLedger.setOutAmount(new BigDecimal(0));
            stockLedger.setInAmount(new BigDecimal(0));
            stockLedger.setLotNumber(UUID.randomUUID().toString());
            stockLedger.setCompany(split.getCompany());
            stockLedger.setPrice(split.getNewPrice());
            stockLedger.setTotalAmount(new BigDecimal(0));
            stockLedger.setCommissionRate(new BigDecimal(0));
            stockLedger.setCommissionAmount(new BigDecimal(0));
            stockLedger.setNetAmount(new BigDecimal(0));
            em.persist(stockLedger);


            em.getTransaction().commit();
            transactionResponse.setMessage("Splited Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(split);


        }catch (Exception ex){
            logger.warning(ex.getMessage());
            System.out.println(ex);
            em.getTransaction().rollback();
            transactionResponse.setMessage("Split Deleting Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(ex);
        }
        return transactionResponse;
    }

    @Override
    public TransactionResponse saveIpoRI(IPORI ipori) {

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();

        try {
            String transType =ipori.getTransType();
            em.persist(ipori);

            StockLedger stockLedger = new StockLedger();
            stockLedger.setUserId(ipori.getUserId());
            stockLedger.setStockIn(ipori.getQty());
            stockLedger.setOutStock(new BigInteger("0"));
            stockLedger.setTransDate(ipori.getDate());
            stockLedger.setTransType(transType);
            stockLedger.setTransId(ipori.getId());
            stockLedger.setOutAmount(new BigDecimal(0));
            stockLedger.setInAmount(new BigDecimal(0));
            stockLedger.setLotNumber(UUID.randomUUID().toString());
            stockLedger.setCompany(ipori.getCompany());
            stockLedger.setPrice(ipori.getPrice());
            stockLedger.setTotalAmount(ipori.getAmount());
            stockLedger.setCommissionRate(new BigDecimal(0));
            stockLedger.setCommissionAmount(new BigDecimal(0));
            stockLedger.setNetAmount(ipori.getAmount());
            em.persist(stockLedger);

            String transTypes = "RIGHT-ISSUE-SUBMIT-DEPOSIT";
            if(ipori.getTransType().toLowerCase().equals("ipo")){
                transTypes="IPO-SUBMIT-DEPOSIT";
            }

            CashBook cashBookIn = new CashBook();
            cashBookIn.setCompany(ipori.getCompany());
            cashBookIn.setInAmount(ipori.getAmount());
            cashBookIn.setOutAmount(new BigDecimal(0));
            cashBookIn.setUserId(ipori.getUserId());
            cashBookIn.setTransDate(ipori.getDate());
            cashBookIn.setTransRefId(ipori.getId());
            cashBookIn.setTransType(transTypes);
            em.persist(cashBookIn);

            transTypes = "RIGHT-ISSUE-SUBMIT";
            if(ipori.getTransType().toLowerCase().equals("ipo")){
                transTypes="IPO-SUBMIT";
            }

            CashBook cashBookOut = new CashBook();
            cashBookOut.setCompany(ipori.getCompany());
            cashBookOut.setInAmount(new BigDecimal(0));
            cashBookOut.setOutAmount(ipori.getAmount());
            cashBookOut.setUserId(ipori.getUserId());
            cashBookOut.setTransDate(ipori.getDate());
            cashBookOut.setTransRefId(ipori.getId());
            cashBookOut.setTransType(transTypes);
            em.persist(cashBookOut);

            em.getTransaction().commit();
            transactionResponse.setMessage("Transaction Saved Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(ipori);

        }catch (Exception e){
            em.getTransaction().rollback();
            transactionResponse.setMessage("Transaction Saving Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(e);
        }
        return transactionResponse;
    }

    @Override
    public List<IPORIDto> getAllIpoRIByUser(String userId) {
        String sql="SELECT ipori.id,ipori.trans_type , companies.name as company,DATE_FORMAT(ipori.date,\"%Y-%m-%d\") AS date , ipori.qty , ipori.price , ipori.amount ,ipori.user_id FROM ipori inner join companies on ipori.company=companies.id where ipori.user_id=? order by ipori.date desc";
        List<IPORIDto> iporiDtos = entityManager.createNativeQuery(sql,IPORIDto.class)
                .setParameter(1,userId)
                .getResultList();
        return iporiDtos;
    }

    @Override
    public TransactionResponse deleteIpoRI(String id, String userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();
        try {
            String sql="delete from cash_book where user_id=?  and trans_ref_id=? ";
            em.createNativeQuery(sql)
                    .setParameter(1,userId)
                    .setParameter(2,id)
                    .executeUpdate();
            IPORI ipori =em.find(IPORI.class,id);


            sql="delete from stock_ledger where user_id=? AND  trans_id=? ";
            em.createNativeQuery(sql)
                    .setParameter(1,userId)
                    .setParameter(2,id)
                    .executeUpdate();

            sql="delete from ipori where user_id=? and  id=? ";
            em.createNativeQuery(sql)
                    .setParameter(1,userId)
                    .setParameter(2,id)
                    .executeUpdate();

            em.getTransaction().commit();
            transactionResponse.setMessage("Transaction Deleted Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(ipori);
        }catch (Exception ex){
            logger.warning(ex.getMessage());
            System.out.println(ex);
            em.getTransaction().rollback();
            transactionResponse.setMessage("Transaction Deleting Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(ex);
        }
        return transactionResponse;
    }


    @Override
    public List<SplitDto> getAllISplitsByUser(String userId) {
        String sql="SELECT s.id, c.name as company,s.current_price, s.current_qty, s.from_qty, s.future_qty, s.new_price, s.out_qty, s.split_date, s.user_id  FROM split s inner join companies c on s.company=c.id where s.user_id=? order by s.split_date desc ; ";
        List<SplitDto> splitDtos = entityManager.createNativeQuery(sql,SplitDto.class)
                .setParameter(1,userId)
                .getResultList();
        return splitDtos;
    }


    @Override
    public TransactionResponse deleteSplit(String id, String userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TransactionResponse transactionResponse = new TransactionResponse();
        try {

            Split split =em.find(Split.class,id);
            String sql="delete from stock_ledger where user_id=? AND  trans_id=? ";
            em.createNativeQuery(sql)
                    .setParameter(1,userId)
                    .setParameter(2,id)
                    .executeUpdate();

            sql="delete from split where user_id=? and  id=? ";
            em.createNativeQuery(sql)
                    .setParameter(1,userId)
                    .setParameter(2,id)
                    .executeUpdate();

            em.getTransaction().commit();
            transactionResponse.setMessage("Transaction Deleted Successfully !");
            transactionResponse.setStatus("success");
            transactionResponse.setResObj(split);
        }catch (Exception ex){
            logger.warning(ex.getMessage());
            System.out.println(ex);
            em.getTransaction().rollback();
            transactionResponse.setMessage("Transaction Deleting Problem !");
            transactionResponse.setStatus("fail");
            transactionResponse.setResObj(ex);
        }
        return transactionResponse;
    }
}
