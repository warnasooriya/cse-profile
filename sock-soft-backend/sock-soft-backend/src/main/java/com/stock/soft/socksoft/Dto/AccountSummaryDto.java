package com.stock.soft.socksoft.Dto;

import java.math.BigDecimal;

public class AccountSummaryDto {
    private BigDecimal totalInvesment;
    private String totalInvesmentInWord;
    private BigDecimal totalProfit;
    private String totalProfitInWord;
    private BigDecimal cashInHand;
    private String cashInHandInWord;
    private BigDecimal netWorth;
    private String netWorthInWord;
    private BigDecimal unrealizeNw;
    private String unrealizeNwInWord;


    public String getTotalInvesmentInWord() {
        return totalInvesmentInWord;
    }

    public void setTotalInvesmentInWord(String totalInvesmentInWord) {
        this.totalInvesmentInWord = totalInvesmentInWord;
    }

    public String getTotalProfitInWord() {
        return totalProfitInWord;
    }

    public void setTotalProfitInWord(String totalProfitInWord) {
        this.totalProfitInWord = totalProfitInWord;
    }

    public String getCashInHandInWord() {
        return cashInHandInWord;
    }

    public void setCashInHandInWord(String cashInHandInWord) {
        this.cashInHandInWord = cashInHandInWord;
    }

    public String getNetWorthInWord() {
        return netWorthInWord;
    }

    public void setNetWorthInWord(String netWorthInWord) {
        this.netWorthInWord = netWorthInWord;
    }

    public String getUnrealizeNwInWord() {
        return unrealizeNwInWord;
    }

    public void setUnrealizeNwInWord(String unrealizeNwInWord) {
        this.unrealizeNwInWord = unrealizeNwInWord;
    }

    public BigDecimal getUnrealizeNw() {
        return unrealizeNw;
    }

    public void setUnrealizeNw(BigDecimal unrealizeNw) {
        this.unrealizeNw = unrealizeNw;
    }

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
