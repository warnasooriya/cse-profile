package com.stock.soft.socksoft.Dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class StockSumDtoChild {
    @Id
    private String id;
    private String kind;
    private String Company;
    private BigDecimal Qty;
    private BigDecimal Price;
    private BigDecimal AvgPrice;
    private BigDecimal Charges;
    private BigDecimal Amount;
    private String Date;
    private String lotNumbers;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public String getLotNumbers() {
        return lotNumbers;
    }

    public void setLotNumbers(String lotNumbers) {
        this.lotNumbers = lotNumbers;
    }
}
