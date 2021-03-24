package com.stock.soft.socksoft.serviceImpl;

import com.stock.soft.socksoft.Dto.FilterDto;
import com.stock.soft.socksoft.Dto.PurchaseResponseDto;
import com.stock.soft.socksoft.Dto.TransactionResponse;
import com.stock.soft.socksoft.dao.PurchaseDao;
import com.stock.soft.socksoft.model.Purchase;
import com.stock.soft.socksoft.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;
    @Override
    public TransactionResponse createPurchase(Purchase purchase) {
        return purchaseDao.createPurchase(purchase);
    }

    @Override
    public TransactionResponse UpdatePurchase(Purchase purchase) {
        return purchaseDao.UpdatePurchase(purchase);
    }

    @Override
    public TransactionResponse DeletePurchase(String purchaseId,String userId) {
        return purchaseDao.DeletePurchase(purchaseId,userId);
    }

    @Override
    public PurchaseResponseDto getPurchaseByUserId(FilterDto filterDto) {
        return purchaseDao.getPurchaseByUserId(filterDto);
    }
}
