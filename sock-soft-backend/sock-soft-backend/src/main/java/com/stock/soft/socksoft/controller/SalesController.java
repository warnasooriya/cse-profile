package com.stock.soft.socksoft.controller;

import com.stock.soft.socksoft.Dto.FilterDto;
import com.stock.soft.socksoft.Dto.PurchaseResponseDto;
import com.stock.soft.socksoft.Dto.TransactionResponse;
import com.stock.soft.socksoft.model.Purchase;
import com.stock.soft.socksoft.model.Sales;
import com.stock.soft.socksoft.service.PurchaseService;
import com.stock.soft.socksoft.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.logging.Logger;

@RestController
@RequestMapping("/sales")
@CrossOrigin(origins = "*")
public class SalesController {
    private final static Logger logger = Logger.getLogger(SalesController.class.getName());

    @Autowired
    private SalesService salesService;

    @RequestMapping(value = "/new" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse createSales(@RequestBody final Sales sales){
        int m = Calendar.getInstance().getTime().getMinutes();
        int h = Calendar.getInstance().getTime().getHours();
        int s = Calendar.getInstance().getTime().getSeconds();
        sales.getTransDate().setHours(h);
        sales.getTransDate().setMinutes(m);
        sales.getTransDate().setSeconds(s);
        return salesService.CreateSales(sales);
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse UpdateSales(@RequestBody final  Sales sales){
        return salesService.UpdateSales(sales);
    }

    @RequestMapping(value = "/delete/{salesId}/{userId}" ,method = RequestMethod.DELETE)
    public TransactionResponse DeleteSales(@PathVariable(value = "salesId") String salesId,@PathVariable(value = "userId") String userId){
        return salesService.DeleteSales(salesId,userId);
    }
    @RequestMapping(value = "/getByUserId" ,method = RequestMethod.POST)
    public PurchaseResponseDto getSalesByUserId(@RequestBody FilterDto filterDto){
        return salesService.getSalesByUserId(filterDto);
    }
}
