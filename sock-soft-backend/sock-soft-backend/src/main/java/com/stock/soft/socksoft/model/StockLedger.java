package com.stock.soft.socksoft.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;
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
@ToString
@Table(name="stock_ledger",
        indexes = {
                @Index(name = "STOCK_INDEX_0",columnList = "lotNumber",unique = false),
                @Index(name = "STOCK_INDEX_1",columnList = "transId",unique = false) ,
                @Index(name = "STOCK_INDEX_2",columnList = "userId",unique = false) ,
                @Index(name = "STOCK_INDEX_3",columnList = "transType",unique = false) ,
                @Index(name = "STOCK_INDEX_4",columnList = "company",unique = false) ,
        })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)

public class StockLedger implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    private String id;
    @Column(nullable = false)
    private String lotNumber;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private BigInteger stockIn;
    @Column(nullable = false)
    private BigInteger outStock;
    @Column(nullable = false)
    private Date transDate;
    @Column(nullable = false)
    private String transType;
    @Column(nullable = false)
    private String transId;
    private BigDecimal inAmount;
    private BigDecimal outAmount;
    @Column(nullable = false)
    private String company;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private BigDecimal totalAmount;
    @Column(nullable = false)
    private BigDecimal commissionRate;
    @Column(nullable = false)
    private BigDecimal commissionAmount;
    @Column(nullable = false)
    private BigDecimal netAmount;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigInteger getStockIn() {
        return stockIn;
    }

    public void setStockIn(BigInteger stockIn) {
        this.stockIn = stockIn;
    }

    public BigInteger getOutStock() {
        return outStock;
    }

    public void setOutStock(BigInteger outStock) {
        this.outStock = outStock;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getTransId() {
        return transId;
    }

    public BigDecimal getInAmount() {
        return inAmount;
    }

    public void setInAmount(BigDecimal inAmount) {
        this.inAmount = inAmount;
    }

    public BigDecimal getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(BigDecimal outAmount) {
        this.outAmount = outAmount;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }
}
