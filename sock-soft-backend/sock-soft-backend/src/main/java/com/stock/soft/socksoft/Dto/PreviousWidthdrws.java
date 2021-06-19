package com.stock.soft.socksoft.Dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class PreviousWidthdrws {
    private BigDecimal totalWidthdrw;
    private BigDecimal currentMonthWidthdrw;
    @Id
    private String id;

    public BigDecimal getTotalWidthdrw() {
        return totalWidthdrw;
    }

    public void setTotalWidthdrw(BigDecimal totalWidthdrw) {
        this.totalWidthdrw = totalWidthdrw;
    }

    public BigDecimal getCurrentMonthWidthdrw() {
        return currentMonthWidthdrw;
    }

    public void setCurrentMonthWidthdrw(BigDecimal currentMonthWidthdrw) {
        this.currentMonthWidthdrw = currentMonthWidthdrw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
