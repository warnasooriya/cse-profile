package com.stock.soft.socksoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="split")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Split implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String company;
    @Column(nullable = false)
    private String userId;

    private BigInteger currentQty;
    private BigInteger fromQty;
    private BigInteger futureQty;
    private BigInteger outQty;

    private Date splitDate;
    private BigDecimal currentPrice;
    private BigDecimal newPrice;

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
}
