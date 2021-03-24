package com.stock.soft.socksoft.Dto;

import java.math.BigDecimal;
import java.util.List;
public class PurchaseResponseDto {

    private List<PurchaseDataDto> purchaseList;
    private BigDecimal sumTotal;

    public List<PurchaseDataDto> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<PurchaseDataDto> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public BigDecimal getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(BigDecimal sumTotal) {
        this.sumTotal = sumTotal;
    }
}
