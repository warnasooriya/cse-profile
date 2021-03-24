package com.stock.soft.socksoft.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="divident")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Dividend implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    private String id;
    @Column( nullable = false)
    private String company;
    private String userId;
    @Column( nullable = false)
    private Date divDate;
    private Date divxdDate;
    @Column(columnDefinition = "boolean default false")
    private Boolean  equityAvailable;
    @Column(columnDefinition = "boolean default false")
    private Boolean  cashAvailable;
    private BigDecimal equity;
    private BigDecimal currentPrice;
    private BigDecimal cashAmount;


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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDivDate() {
        return divDate;
    }

    public void setDivDate(Date divDate) {
        this.divDate = divDate;
    }

    public Date getDivxdDate() {
        return divxdDate;
    }

    public void setDivxdDate(Date divxdDate) {
        this.divxdDate = divxdDate;
    }

    public Boolean getEquityAvailable() {
        return equityAvailable;
    }

    public void setEquityAvailable(Boolean equityAvailable) {
        this.equityAvailable = equityAvailable;
    }

    public Boolean getCashAvailable() {
        return cashAvailable;
    }

    public void setCashAvailable(Boolean cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public BigDecimal getEquity() {
        return equity;
    }

    public void setEquity(BigDecimal equity) {
        this.equity = equity;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }
}
