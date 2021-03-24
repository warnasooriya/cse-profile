package com.stock.soft.socksoft.service;

import com.stock.soft.socksoft.Dto.*;
import com.stock.soft.socksoft.model.Deposit;
import com.stock.soft.socksoft.model.Dividend;

import java.util.List;

public interface StockService {
    List<StockSumDto> getAvailableStock(String userId);

    List<ChildStockDto> getAvailableStockDetails(String userId, String companyId);

    TransactionResponse saveDividend(Dividend dividend);

    TransactionResponse saveDeposit(Deposit deposit);

    List<DepositDto> getAllDepositByUser(String userId);

    List<DividendDto> loadDividendToTable(String userId);

    TransactionResponse deleteDividend(String id, String userId);

    TransactionResponse deleteDeposit(String id, String userId);

    PreviousDeposits getPreviousDeposits(String userId);
}
