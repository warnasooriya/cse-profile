package com.stock.soft.socksoft.dao;

import com.stock.soft.socksoft.Dto.FilterDto;
import com.stock.soft.socksoft.Dto.PurchaseResponseDto;
import com.stock.soft.socksoft.Dto.TransactionResponse;
import com.stock.soft.socksoft.model.Sales;

public interface SalesDao {
    TransactionResponse CreateSales(Sales sales);

    TransactionResponse UpdateSales(Sales sales);

    TransactionResponse DeleteSales(String salesId,String userId);

    PurchaseResponseDto getSalesByUserId(FilterDto filterDto);
}
