package com.stock.soft.socksoftSync.Dto.cse;

import org.joda.time.DateTime;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ReqTradeSummery {
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
    private BigDecimal high;
    private BigDecimal closingPrice;
    private BigDecimal crossingTradeVol;
    private BigDecimal crossingVolume;
    private BigDecimal low;
    private BigDecimal marketCap;
    private BigDecimal marketCapPercentage;
    private BigDecimal open;
    private BigDecimal previousClose;
    private BigDecimal status;

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

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public BigDecimal getCrossingTradeVol() {
        return crossingTradeVol;
    }

    public void setCrossingTradeVol(BigDecimal crossingTradeVol) {
        this.crossingTradeVol = crossingTradeVol;
    }

    public BigDecimal getCrossingVolume() {
        return crossingVolume;
    }

    public void setCrossingVolume(BigDecimal crossingVolume) {
        this.crossingVolume = crossingVolume;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public BigDecimal getMarketCapPercentage() {
        return marketCapPercentage;
    }

    public void setMarketCapPercentage(BigDecimal marketCapPercentage) {
        this.marketCapPercentage = marketCapPercentage;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(BigDecimal previousClose) {
        this.previousClose = previousClose;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }
}
