package com.stock.soft.socksoft.Dto;

import java.math.BigDecimal;
import java.util.List;

public class MonthlyDBSPChardDto {
    List<String> labels;
    List<BigDecimal> deposit;
    List<BigDecimal> buy;
    List<BigDecimal> sell;
    List<BigDecimal> profit;

    public List<BigDecimal> getDeposit() {
        return deposit;
    }

    public void setDeposit(List<BigDecimal> deposit) {
        this.deposit = deposit;
    }

    public List<BigDecimal> getBuy() {
        return buy;
    }

    public void setBuy(List<BigDecimal> buy) {
        this.buy = buy;
    }

    public List<BigDecimal> getSell() {
        return sell;
    }

    public void setSell(List<BigDecimal> sell) {
        this.sell = sell;
    }

    public List<BigDecimal> getProfit() {
        return profit;
    }

    public void setProfit(List<BigDecimal> profit) {
        this.profit = profit;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
