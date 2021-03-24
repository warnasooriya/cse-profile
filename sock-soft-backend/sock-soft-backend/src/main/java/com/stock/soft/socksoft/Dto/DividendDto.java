package com.stock.soft.socksoft.Dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
public class DividendDto {
    @Id
    private String id;
    private String company;
    private String divDate;
    private String xdate;
    private BigDecimal price;
    private BigInteger equity;
    private BigDecimal amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDivDate() {
        return divDate;
    }

    public void setDivDate(String divDate) {
        this.divDate = divDate;
    }

    public String getXdate() {
        return xdate;
    }

    public void setXdate(String xdate) {
        this.xdate = xdate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigInteger getEquity() {
        return equity;
    }

    public void setEquity(BigInteger equity) {
        this.equity = equity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
