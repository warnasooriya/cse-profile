package com.stock.soft.socksoft.daoImpl;

import com.stock.soft.socksoft.Dto.*;
import com.stock.soft.socksoft.dao.DashboardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class DashboardDaoImpl implements DashboardDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public InvesmentDatDto getAllDepositAmountByUser(String userId) {
        InvesmentDatDto invesmentDatDto = new InvesmentDatDto();
        String sql="SELECT ifnull(sum(amount),0) FROM deposit where user_id=?";
        String sqlcm="SELECT ifnull(sum(amount),0) FROM deposit where user_id=? and DATE_FORMAT(date,\"%Y-%m\")  =  DATE_FORMAT(current_date(),\"%Y-%m\") ";
        String totalDepositAmount =entityManager.createNativeQuery(sql).setParameter(1,userId).getSingleResult().toString();
        String currentMonthDepositAmount =entityManager.createNativeQuery(sqlcm).setParameter(1,userId).getSingleResult().toString();
        invesmentDatDto.setTotalInvesment(new BigDecimal(totalDepositAmount));
        invesmentDatDto.setCurrentMonthInvestment(new BigDecimal(currentMonthDepositAmount));
        return invesmentDatDto;
    }

    @Override
    public TotalEarningDto getTotalEarnings(String userId) {
        String sql="call getTotalEarningsByUser(?)";
        return (TotalEarningDto) entityManager.createNativeQuery(sql,TotalEarningDto.class).setParameter(1,userId).getSingleResult();
    }

    @Override
    public MonthlyDBSPChardDto getMonthlyDBSPCharts(String userId, String period) {
        String p="M";
        if(period.equals("year")){
            p="Y";
        }
        String sql="call dataToDBSPCharts(?,?)";
        List<MonthlyDBSPTemplateDto> dtoList  = entityManager.createNativeQuery(sql,MonthlyDBSPTemplateDto.class)
                  .setParameter(1,userId)
                  .setParameter(2,p)
                  .getResultList();
        MonthlyDBSPChardDto m = new MonthlyDBSPChardDto();
        List<String> labels = new ArrayList<>();
        List<BigDecimal> buy = new ArrayList<>();
        List<BigDecimal> sell = new ArrayList<>();
        List<BigDecimal> deposit = new ArrayList<>();
        List<BigDecimal> profit = new ArrayList<>();
        dtoList.forEach(md -> {
            labels.add(md.getPeriod());
            buy.add(md.getBuy());
            sell.add(md.getSell());
            deposit.add(md.getDeposit());
            profit.add(md.getProfit());
        });
        m.setLabels(labels);
        m.setBuy(buy);
        m.setSell(sell);
        m.setDeposit(deposit);
        m.setProfit(profit);
        return m;
    }


    @Override
    public List<TransactionDto> getTransactions(FilterDto filterDto) {
        String sql="call generate_account_balance(?,?)";
        List<TransactionDto> transactionDtoList =   entityManager.createNativeQuery(sql,TransactionDto.class)
                .setParameter(1,filterDto.getUserId())
                .setParameter(2,filterDto.getFromDate())
                .getResultList();
        return transactionDtoList;
    }

    @Override
    public List<TopGainersDto> getTopGainers(String userId) {
        String sql="call topGainers(?)";
        return  entityManager.createNativeQuery(sql,TopGainersDto.class).setParameter(1,userId).getResultList();
    }

    @Override
    public BigDecimal getCashInHandByUser(String userId) {
           String sql="SELECT    ifnull(sum(in_amount) - sum(out_amount),0)  FROM cash_book  where user_id=?";
        return (BigDecimal) entityManager.createNativeQuery(sql).setParameter(1,userId).getSingleResult();
    }

    @Override
    public List<ProfitDetailsDto> getProfitDetails(FilterDto filterDto) {
        String sql="call getProfitDetailsByUser(?,?,?)";
        return  entityManager.createNativeQuery(sql,ProfitDetailsDto.class)
                .setParameter(1,filterDto.getUserId())
                .setParameter(2,filterDto.getFromDate())
                .setParameter(3,filterDto.getToDate())
                .getResultList();
    }

    @Override
    public InvesmentDatDto getTotalInvestmentByUser(String userId) {
        InvesmentDatDto invesmentDatDto = new InvesmentDatDto();
        String sql="SELECT ifnull(sum(in_amount),0) as investment FROM cash_book where user_id=? and trans_type IN ('DEPOSIT','IPO-SUBMIT-DEPOSIT','RIGHT-ISSUE-SUBMIT-DEPOSIT')";
        String sqlcm="SELECT ifnull(sum(in_amount),0) as investment FROM cash_book where user_id=? and DATE_FORMAT(trans_date,\"%Y-%m\")  =  DATE_FORMAT(current_date(),\"%Y-%m\") and trans_type IN ('DEPOSIT','IPO-SUBMIT-DEPOSIT','RIGHT-ISSUE-SUBMIT-DEPOSIT')";
        //String sqlcm="SELECT ifnull(sum(amount),0) FROM deposit where user_id=? and DATE_FORMAT(date,\"%Y-%m\")  =  DATE_FORMAT(current_date(),\"%Y-%m\") ";
        String totalDepositAmount =entityManager.createNativeQuery(sql).setParameter(1,userId).getSingleResult().toString();
        String currentMonthDepositAmount =entityManager.createNativeQuery(sqlcm).setParameter(1,userId).getSingleResult().toString();
        invesmentDatDto.setTotalInvesment(new BigDecimal(totalDepositAmount));
        invesmentDatDto.setCurrentMonthInvestment(new BigDecimal(currentMonthDepositAmount));
        return invesmentDatDto;
    }
}
