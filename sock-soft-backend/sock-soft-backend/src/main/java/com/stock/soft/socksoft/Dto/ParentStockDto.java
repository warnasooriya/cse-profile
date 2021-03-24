package com.stock.soft.socksoft.Dto;

import java.math.BigDecimal;
import java.util.Date;

public class ParentStockDto {
    private String kind;
    private String Company;
    private BigDecimal Qty;
    private BigDecimal AvgPrice;
    private BigDecimal Charges;
    private BigDecimal Amount;
    private Date Date;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public BigDecimal getQty() {
        return Qty;
    }

    public void setQty(BigDecimal qty) {
        Qty = qty;
    }

    public BigDecimal getAvgPrice() {
        return AvgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        AvgPrice = avgPrice;
    }

    public BigDecimal getCharges() {
        return Charges;
    }

    public void setCharges(BigDecimal charges) {
        Charges = charges;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }
}
