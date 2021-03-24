package com.stock.soft.socksoft.serviceImpl;

import com.stock.soft.socksoft.Dto.FilterDto;
import com.stock.soft.socksoft.Dto.PurchaseResponseDto;
import com.stock.soft.socksoft.Dto.TransactionResponse;
import com.stock.soft.socksoft.dao.PurchaseDao;
import com.stock.soft.socksoft.dao.SalesDao;
import com.stock.soft.socksoft.model.Purchase;
import com.stock.soft.socksoft.model.Sales;
import com.stock.soft.socksoft.service.PurchaseService;
import com.stock.soft.socksoft.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesDao salesDao;


    @Override
    public TransactionResponse CreateSales(Sales sales) {
        return salesDao.CreateSales(sales);
    }

    @Override
    public TransactionResponse UpdateSales(Sales sales) {
        return salesDao.UpdateSales(sales);
    }

    @Override
    public TransactionResponse DeleteSales(String salesId,String userId) {
        return salesDao.DeleteSales(salesId,userId);
    }

    @Override
    public PurchaseResponseDto getSalesByUserId(FilterDto filterDto) {
        return salesDao.getSalesByUserId(filterDto);
    }


}
