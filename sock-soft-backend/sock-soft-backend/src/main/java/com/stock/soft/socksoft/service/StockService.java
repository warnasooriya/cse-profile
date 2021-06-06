package com.stock.soft.socksoft.service;

import com.stock.soft.socksoft.Dto.*;
import com.stock.soft.socksoft.model.Deposit;
import com.stock.soft.socksoft.model.Dividend;
import com.stock.soft.socksoft.model.IPORI;
import com.stock.soft.socksoft.model.Split;

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

    TransactionResponse saveStockSplit(Split split);

    TransactionResponse saveIpoRI(IPORI ipori);

    List<IPORIDto> getAllIpoRIByUser(String userId);

    TransactionResponse deleteIpoRI(String id, String userId);

    List<SplitDto> getAllISplitsByUser(String userId);

    TransactionResponse deleteSplit(String id, String userId);
}
