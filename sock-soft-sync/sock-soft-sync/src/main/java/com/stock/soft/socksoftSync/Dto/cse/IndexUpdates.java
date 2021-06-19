package com.stock.soft.socksoftSync.Dto.cse;

import java.math.BigDecimal;

public class IndexUpdates {
    private BigDecimal snpAmount;
    private BigDecimal snpPersentage;
    private BigDecimal aspiAmount;
    private BigDecimal aspiPersentage;
    private BigDecimal aspichange;
    private BigDecimal snpchange;

    public BigDecimal getAspichange() {
        return aspichange;
    }

    public void setAspichange(BigDecimal aspichange) {
        this.aspichange = aspichange;
    }

    public BigDecimal getSnpchange() {
        return snpchange;
    }

    public void setSnpchange(BigDecimal snpchange) {
        this.snpchange = snpchange;
    }

    public BigDecimal getSnpAmount() {
        return snpAmount;
    }

    public void setSnpAmount(BigDecimal snpAmount) {
        this.snpAmount = snpAmount;
    }

    public BigDecimal getSnpPersentage() {
        return snpPersentage;
    }

    public void setSnpPersentage(BigDecimal snpPersentage) {
        this.snpPersentage = snpPersentage;
    }

    public BigDecimal getAspiAmount() {
        return aspiAmount;
    }

    public void setAspiAmount(BigDecimal aspiAmount) {
        this.aspiAmount = aspiAmount;
    }

    public BigDecimal getAspiPersentage() {
        return aspiPersentage;
    }

    public void setAspiPersentage(BigDecimal aspiPersentage) {
        this.aspiPersentage = aspiPersentage;
    }
}
