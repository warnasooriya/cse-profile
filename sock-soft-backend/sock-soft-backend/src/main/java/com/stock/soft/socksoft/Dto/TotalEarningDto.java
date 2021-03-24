package com.stock.soft.socksoft.Dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class TotalEarningDto {
    @Id
    private String id;
    private BigDecimal totalEquityHoldings;
    private BigDecimal totalEarnings;
    private BigDecimal currentMonthEarnings;
    private BigDecimal todayEarnings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getTotalEquityHoldings() {
        return totalEquityHoldings;
    }

    public void setTotalEquityHoldings(BigDecimal totalEquityHoldings) {
        this.totalEquityHoldings = totalEquityHoldings;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public BigDecimal getCurrentMonthEarnings() {
        return currentMonthEarnings;
    }

    public void setCurrentMonthEarnings(BigDecimal currentMonthEarnings) {
        this.currentMonthEarnings = currentMonthEarnings;
    }

    public BigDecimal getTodayEarnings() {
        return todayEarnings;
    }

    public void setTodayEarnings(BigDecimal todayEarnings) {
        this.todayEarnings = todayEarnings;
    }
}
