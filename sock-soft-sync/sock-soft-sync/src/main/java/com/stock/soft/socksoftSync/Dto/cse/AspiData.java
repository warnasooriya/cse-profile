package com.stock.soft.socksoftSync.Dto.cse;

import java.math.BigDecimal;

public class AspiData {
        private long id;
        private BigDecimal value;
        private BigDecimal change;
        private BigDecimal percentage;
        private BigDecimal sectorId;
        private String timestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getSectorId() {
        return sectorId;
    }

    public void setSectorId(BigDecimal sectorId) {
        this.sectorId = sectorId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
