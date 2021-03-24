package com.stock.soft.socksoft.Dto;

import java.math.BigDecimal;

public class InvesmentDatDto {
    private BigDecimal totalInvesment;
    private BigDecimal currentMonthInvestment;

    public BigDecimal getTotalInvesment() {
        return totalInvesment;
    }

    public void setTotalInvesment(BigDecimal totalInvesment) {
        this.totalInvesment = totalInvesment;
    }

    public BigDecimal getCurrentMonthInvestment() {
        return currentMonthInvestment;
    }

    public void setCurrentMonthInvestment(BigDecimal currentMonthInvestment) {
        this.currentMonthInvestment = currentMonthInvestment;
    }
}
