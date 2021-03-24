package com.stock.soft.socksoft.Dto;

import java.util.List;

public class StockFullResponseDto {
    private StockSumDto data;
    private List<ChildrenDto> children;
    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public StockSumDto getData() {
        return data;
    }

    public void setData(StockSumDto data) {
        this.data = data;
    }

    public List<ChildrenDto> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenDto> children) {
        this.children = children;
    }
}
