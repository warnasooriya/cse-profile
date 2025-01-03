package com.stock.soft.socksoft.Dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class StockSumDto {
    @Id
    private String id;
    private String company;
    private String code;
    private String date;
    private BigDecimal qty;
    private BigDecimal price;
    private BigDecimal avgPrice;
    private BigDecimal chargers;
    private BigDecimal amount;
    private String lotNumbers;
    private BigDecimal currentPrice;
    private BigDecimal unrealizeProfit;
    private BigDecimal Percentage;
    private BigDecimal commission;

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getChargers() {
        return chargers;
    }

    public void setChargers(BigDecimal chargers) {
        this.chargers = chargers;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLotNumbers() {
        return lotNumbers;
    }

    public void setLotNumbers(String lotNumbers) {
        this.lotNumbers = lotNumbers;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getUnrealizeProfit() {
        return unrealizeProfit;
    }

    public void setUnrealizeProfit(BigDecimal unrealizeProfit) {
        this.unrealizeProfit = unrealizeProfit;
    }

    public BigDecimal getPercentage() {
        return Percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        Percentage = percentage;
    }
}
