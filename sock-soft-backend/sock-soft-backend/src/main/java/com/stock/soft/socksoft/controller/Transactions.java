package com.stock.soft.socksoft.controller;

import com.stock.soft.socksoft.Dto.FilterDto;
import com.stock.soft.socksoft.Dto.PurchaseResponseDto;
import com.stock.soft.socksoft.Dto.TransactionDto;
import com.stock.soft.socksoft.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class Transactions {

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(value = "/getTransactions" ,method = RequestMethod.POST)
    public List<TransactionDto> getTransactions(@RequestBody FilterDto filterDto){
        return dashboardService.getTransactions(filterDto);
    }
}
