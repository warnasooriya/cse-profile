package com.stock.soft.socksoftSync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


@Entity
@Table(name="companies",
        indexes = {
        @Index(name = "COMPANY_INDEX_0",columnList = "name"),
                @Index(name = "COMPANY_INDEX_1",columnList = "code",unique = true)
})
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Companies implements Serializable {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    private String id;
    @Column( nullable = false)
    private String name;
    @Column( nullable = false)
    private String code;
    @Column( nullable = true)
    private String lastTradedTime;
    @Column( nullable = true)
    private BigDecimal price;
    @Column( nullable = true)
    private BigDecimal turnover;
    @Column( nullable = true)
    private BigInteger sharevolume;
    @Column( nullable = true)
    private BigInteger tradevolume;
    @Column( nullable = true)
    private BigDecimal percentageChange;
    @Column( nullable = true)
    private BigDecimal changeValue;
    @Column( nullable = true)
    private BigDecimal high;
    @Column( nullable = true)
    private BigDecimal closingPrice;
    @Column( nullable = true)
    private BigDecimal crossingTradeVol;
    @Column( nullable = true)
    private BigDecimal crossingVolume;
    @Column( nullable = true)
    private BigDecimal low;
    @Column( nullable = true)
    private BigDecimal marketCap;
    @Column( nullable = true)
    private BigDecimal marketCapPercentage;
    @Column( nullable = true)
    private BigDecimal open;
    @Column( nullable = true)
    private BigDecimal previousClose;
    @Column( nullable = true)
    private BigDecimal status;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastTradedTime() {
        return lastTradedTime;
    }

    public void setLastTradedTime(String lastTradedTime) {
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

    public BigDecimal getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(BigDecimal changeValue) {
        this.changeValue = changeValue;
    }
}
