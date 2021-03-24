package com.stock.soft.socksoft.Dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class PreviousDeposits {
    private BigDecimal totalDeposit;
    private BigDecimal currentMonthDeposit;
    @Id
    private String id;
    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public BigDecimal getCurrentMonthDeposit() {
        return currentMonthDeposit;
    }

    public void setCurrentMonthDeposit(BigDecimal currentMonthDeposit) {
        this.currentMonthDeposit = currentMonthDeposit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
