package com.stock.soft.socksoft.Dto.cse;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ReqAlphabetical {
    private int id;
    private String name;
    private String symbol;
    private DateTime lastTradedTime;
    private BigDecimal price;
    private BigDecimal turnover;
    private BigInteger sharevolume;
    private BigInteger tradevolume;
    private BigDecimal percentageChange;
    private BigDecimal change;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public DateTime getLastTradedTime() {
        return lastTradedTime;
    }

    public void setLastTradedTime(DateTime lastTradedTime) {
        this.lastTradedTime = lastTradedTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    public BigInteger getSharevolume() {
        return sharevolume;
    }

    public void setSharevolume(BigInteger sharevolume) {
        this.sharevolume = sharevolume;
    }

    public BigInteger getTradevolume() {
        return tradevolume;
    }

    public void setTradevolume(BigInteger tradevolume) {
        this.tradevolume = tradevolume;
    }

    public BigDecimal getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(BigDecimal percentageChange) {
        this.percentageChange = percentageChange;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }
}
