package com.stock.soft.socksoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


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
