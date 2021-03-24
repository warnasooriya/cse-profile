package com.stock.soft.socksoft.controller;


import com.stock.soft.socksoft.Dto.*;
import com.stock.soft.socksoft.model.Deposit;
import com.stock.soft.socksoft.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(value = "/getAllDepositAmount/{userId}" ,method = RequestMethod.GET)
    public InvesmentDatDto getAllDepositAmountByUser(@PathVariable(value = "userId") String userId ){
        return dashboardService.getAllDepositAmountByUser(userId);
    }

    @RequestMapping(value = "/getTotalEarnings/{userId}" ,method = RequestMethod.GET)
    public TotalEarningDto getTotalEarnings(@PathVariable(value = "userId") String userId ){
        return dashboardService.getTotalEarnings(userId);
    }

    @RequestMapping(value = "/getMonthlyDBSPCharts/{userId}/{period}" ,method = RequestMethod.GET)
    public MonthlyDBSPChardDto getMonthlyDBSPCharts(@PathVariable(value = "userId") String userId ,@PathVariable(value = "period") String period  ){
        return dashboardService.getMonthlyDBSPCharts(userId,period);
    }


    @RequestMapping(value = "/getTopGainers/{userId}" ,method = RequestMethod.GET)
    public List<TopGainersDto> getTopGainers(@PathVariable(value = "userId") String userId  ){
        return dashboardService.getTopGainers(userId);
    }


    @RequestMapping(value = "/getAccountSummary/{userId}" ,method = RequestMethod.GET)
    public AccountSummaryDto getAccountSummary(@PathVariable(value = "userId") String userId  ){
        AccountSummaryDto accountSummaryDto = new AccountSummaryDto();
        InvesmentDatDto invesmentDatDto = getAllDepositAmountByUser(userId);
        accountSummaryDto.setTotalInvesment(invesmentDatDto.getTotalInvesment());
        TotalEarningDto totalEarningDto= dashboardService.getTotalEarnings(userId);
        accountSummaryDto.setTotalProfit(totalEarningDto.getTotalEarnings());
        BigDecimal totalEquityHolding = totalEarningDto.getTotalEquityHoldings();
        BigDecimal cashInHand = dashboardService.getCashInHandByUser(userId);
        BigDecimal netWorth = totalEquityHolding.add(cashInHand);
        accountSummaryDto.setCashInHand(cashInHand);
        accountSummaryDto.setNetWorth(netWorth);
        return accountSummaryDto;
    }

    @RequestMapping(value = "/getProfitDetails" ,method = RequestMethod.POST)
    public List<ProfitDetailsDto> getProfitDetails(@RequestBody FilterDto filterDto){
        return dashboardService.getProfitDetails(filterDto);
    }
}