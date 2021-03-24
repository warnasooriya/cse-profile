package com.stock.soft.socksoft.Dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class TopGainersDto {
    @Id
    private String id;
    private String name;
    private String company;
    private BigDecimal gain;
    private BigDecimal investment;
    private boolean deltaUp;
    private double gainPresentage;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigDecimal getGain() {
        return gain;
    }

    public void setGain(BigDecimal gain) {
        this.gain = gain;
    }

    public boolean isDeltaUp() {
        return deltaUp;
    }

    public void setDeltaUp(boolean deltaUp) {
        this.deltaUp = deltaUp;
    }

    public double getGainPresentage() {
        return gainPresentage;
    }

    public void setGainPresentage(double gainPresentage) {
        this.gainPresentage = gainPresentage;
    }

    public BigDecimal getInvestment() {
        return investment;
    }

    public void setInvestment(BigDecimal investment) {
        this.investment = investment;
    }
}
