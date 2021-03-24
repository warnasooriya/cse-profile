package com.stock.soft.socksoft.Dto;

import java.math.BigDecimal;

public class AccountSummaryDto {
    private BigDecimal totalInvesment;
    private BigDecimal totalProfit;
    private BigDecimal cashInHand;
    private BigDecimal netWorth;

    public BigDecimal getTotalInvesment() {
        return totalInvesment;
    }

    public void setTotalInvesment(BigDecimal totalInvesment) {
        this.totalInvesment = totalInvesment;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getCashInHand() {
        return cashInHand;
    }

    public void setCashInHand(BigDecimal cashInHand) {
        this.cashInHand = cashInHand;
    }

    public BigDecimal getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
    }
}
