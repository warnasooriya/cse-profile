package com.stock.soft.socksoft.Dto;

import java.math.BigDecimal;

public class TotalEarningResDto {
    private String id;
    private BigDecimal totalEquityHoldings;
    private String totalEquityHoldingsInWord;
    private BigDecimal totalEarnings;
    private String totalEarningsInWord;
    private BigDecimal currentMonthEarnings;
    private String currentMonthEarningsInWord;
    private BigDecimal todayEarnings;
    private String todayEarningsInWord;
    private BigDecimal unrealizeNetWorth;
    private String unrealizeNetWorthInWord;

    public String getTotalEquityHoldingsInWord() {
        return totalEquityHoldingsInWord;
    }

    public void setTotalEquityHoldingsInWord(String totalEquityHoldingsInWord) {
        this.totalEquityHoldingsInWord = totalEquityHoldingsInWord;
    }

    public String getTotalEarningsInWord() {
        return totalEarningsInWord;
    }

    public void setTotalEarningsInWord(String totalEarningsInWord) {
        this.totalEarningsInWord = totalEarningsInWord;
    }

    public String getCurrentMonthEarningsInWord() {
        return currentMonthEarningsInWord;
    }

    public void setCurrentMonthEarningsInWord(String currentMonthEarningsInWord) {
        this.currentMonthEarningsInWord = currentMonthEarningsInWord;
    }

    public String getTodayEarningsInWord() {
        return todayEarningsInWord;
    }

    public void setTodayEarningsInWord(String todayEarningsInWord) {
        this.todayEarningsInWord = todayEarningsInWord;
    }

    public String getUnrealizeNetWorthInWord() {
        return unrealizeNetWorthInWord;
    }

    public void setUnrealizeNetWorthInWord(String unrealizeNetWorthInWord) {
        this.unrealizeNetWorthInWord = unrealizeNetWorthInWord;
    }

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

    public BigDecimal getUnrealizeNetWorth() {
        return unrealizeNetWorth;
    }

    public void setUnrealizeNetWorth(BigDecimal unrealizeNetWorth) {
        this.unrealizeNetWorth = unrealizeNetWorth;
    }
}
