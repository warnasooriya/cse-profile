package com.stock.soft.socksoft.dao;

import com.stock.soft.socksoft.Dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface DashboardDao {
    InvesmentDatDto getAllDepositAmountByUser(String userId);

    TotalEarningDto getTotalEarnings(String userId);

    MonthlyDBSPChardDto getMonthlyDBSPCharts(String userId, String period);

    List<TransactionDto> getTransactions(FilterDto filterDto);

    List<TopGainersDto> getTopGainers(String userId);

    BigDecimal getCashInHandByUser(String userId);

    List<ProfitDetailsDto> getProfitDetails(FilterDto filterDto);

    InvesmentDatDto getTotalInvestmentByUser(String userId);
}
