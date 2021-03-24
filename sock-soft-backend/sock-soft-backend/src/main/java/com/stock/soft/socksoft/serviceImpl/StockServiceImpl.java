package com.stock.soft.socksoft.serviceImpl;

import com.stock.soft.socksoft.Dto.*;
import com.stock.soft.socksoft.dao.StockDao;
import com.stock.soft.socksoft.model.Deposit;
import com.stock.soft.socksoft.model.Dividend;
import com.stock.soft.socksoft.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;
    @Override
    public List<StockSumDto> getAvailableStock(String userId) {
        return stockDao.getAvailableStock(userId);
    }

    @Override
    public List<ChildStockDto> getAvailableStockDetails(String userId, String companyId) {
        return stockDao.getAvailableStockDetails(userId,companyId);
    }

    @Override
    public TransactionResponse saveDividend(Dividend dividend) {
        return stockDao.saveDividend(dividend);
    }

    @Override
    public TransactionResponse saveDeposit(Deposit deposit) {
        return stockDao.saveDeposit(deposit);
    }

    @Override
    public List<DepositDto> getAllDepositByUser(String userId) {
        return stockDao.getAllDepositByUser(userId);
    }

    @Override
    public List<DividendDto> loadDividendToTable(String userId) {
        return stockDao.loadDividendToTable(userId);
    }

    @Override
    public TransactionResponse deleteDividend(String id, String userId) {
        return stockDao.deleteDividend(id,userId);
    }

    @Override
    public TransactionResponse deleteDeposit(String id, String userId) {
        return stockDao.deleteDeposit(id,userId);
    }

    @Override
    public PreviousDeposits getPreviousDeposits(String userId) {
        return stockDao.getPreviousDeposits(userId);
    }
}
