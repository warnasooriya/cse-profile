package com.stock.soft.socksoft.controller;

import com.stock.soft.socksoft.Dto.FilterDto;
import com.stock.soft.socksoft.Dto.PurchaseResponseDto;
import com.stock.soft.socksoft.Dto.TransactionResponse;
import com.stock.soft.socksoft.model.Companies;
import com.stock.soft.socksoft.model.Purchase;
import com.stock.soft.socksoft.model.Users;
import com.stock.soft.socksoft.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.FormParam;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/purchase")
@CrossOrigin(origins = "*")
public class PurchaseController {
    private final static Logger logger = Logger.getLogger(PurchaseController.class.getName());

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/new" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse createPurchase(@RequestBody final  Purchase purchase){
        int m = Calendar.getInstance().getTime().getMinutes();
        int h = Calendar.getInstance().getTime().getHours();
        int s = Calendar.getInstance().getTime().getSeconds();
        purchase.getTransDate().setHours(h);
        purchase.getTransDate().setMinutes(m);
        purchase.getTransDate().setSeconds(s);
        return purchaseService.createPurchase(purchase);
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse UpdatePurchase(@RequestBody final  Purchase purchase){
        return purchaseService.UpdatePurchase(purchase);
    }

    @RequestMapping(value = "/delete/{purchaseId}/{userId}" ,method = RequestMethod.DELETE)
    public TransactionResponse DeletePurchase(@PathVariable(value = "purchaseId") String purchaseId,@PathVariable(value = "userId") String userId){
        return purchaseService.DeletePurchase(purchaseId,userId);
    }

    @RequestMapping(value = "/getByUserId" ,method = RequestMethod.POST)
    public PurchaseResponseDto getPurchaseByUserId(@RequestBody FilterDto filterDto){
        return purchaseService.getPurchaseByUserId(filterDto);
    }



}
