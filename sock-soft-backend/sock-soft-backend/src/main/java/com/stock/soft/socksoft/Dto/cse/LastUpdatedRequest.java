package com.stock.soft.socksoft.Dto.cse;

import java.math.BigInteger;

public class LastUpdatedRequest {
    BigInteger id;
    String lastUpdatedTime;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
