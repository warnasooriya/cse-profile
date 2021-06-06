package com.stock.soft.socksoft.Dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
public class SplitDto {
    @Id
    private String id;
    private String company;
    private BigInteger currentQty;
    private BigInteger fromQty;
    private BigInteger futureQty;
    private BigInteger outQty;
    private Date splitDate;
    private BigDecimal currentPrice;
    private BigDecimal newPrice;


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

    public BigInteger getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(BigInteger currentQty) {
        this.currentQty = currentQty;
    }

    public BigInteger getFromQty() {
        return fromQty;
    }

    public void setFromQty(BigInteger fromQty) {
        this.fromQty = fromQty;
    }

    public BigInteger getFutureQty() {
        return futureQty;
    }

    public void setFutureQty(BigInteger futureQty) {
        this.futureQty = futureQty;
    }

    public BigInteger getOutQty() {
        return outQty;
    }

    public void setOutQty(BigInteger outQty) {
        this.outQty = outQty;
    }

    public Date getSplitDate() {
        return splitDate;
    }

    public void setSplitDate(Date splitDate) {
        this.splitDate = splitDate;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }
}
