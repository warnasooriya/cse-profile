package com.stock.soft.socksoft.dao;

import com.stock.soft.socksoft.Dto.FilterDto;
import com.stock.soft.socksoft.Dto.PurchaseResponseDto;
import com.stock.soft.socksoft.Dto.TransactionResponse;
import com.stock.soft.socksoft.model.Purchase;

import java.util.Date;
import java.util.List;

public interface PurchaseDao {
    TransactionResponse createPurchase(Purchase purchase);

    TransactionResponse UpdatePurchase(Purchase purchase);

    TransactionResponse DeletePurchase(String purchaseId,String userId);

    PurchaseResponseDto getPurchaseByUserId(FilterDto filterDto );
}
