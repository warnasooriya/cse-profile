package com.stock.soft.socksoft.serviceImpl;

import com.stock.soft.socksoft.Dto.*;
import com.stock.soft.socksoft.dao.DashboardDao;
import com.stock.soft.socksoft.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardDao dashboardDao;
    @Override
    public InvesmentDatDto getAllDepositAmountByUser(String userId) {
        return dashboardDao.getAllDepositAmountByUser(userId);
    }

    @Override
    public TotalEarningResDto getTotalEarnings(String userId) {
        return dashboardDao.getTotalEarnings(userId);
    }

    @Override
    public MonthlyDBSPChardDto getMonthlyDBSPCharts(String userId, String period) {
        return dashboardDao.getMonthlyDBSPCharts(userId,period);
    }

    @Override
    public List<TransactionDto> getTransactions(FilterDto filterDto) {
        return dashboardDao.getTransactions(filterDto);
    }

    @Override
    public List<TopGainersDto> getTopGainers(String userId) {
        return dashboardDao.getTopGainers(userId);
    }

    @Override
    public BigDecimal getCashInHandByUser(String userId) {
        return dashboardDao.getCashInHandByUser(userId);
    }

    @Override
    public List<ProfitDetailsDto> getProfitDetails(FilterDto filterDto) {
        return dashboardDao.getProfitDetails(filterDto);
    }

    @Override
    public InvesmentDatDto getTotalInvestmentByUser(String userId) {
        return dashboardDao.getTotalInvestmentByUser(userId);
    }
}
