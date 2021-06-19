package com.stock.soft.socksoft.controller;

import com.stock.soft.socksoft.Dto.*;
import com.stock.soft.socksoft.model.*;
import com.stock.soft.socksoft.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "*")
public class StockController {

    @Autowired
    private StockService stockService;
    @RequestMapping(value = "/getAvailableStock/{userId}" ,method = RequestMethod.GET)
    public List<StockSumDto> getAvailableStock(@PathVariable(value = "userId") String userId){
        return stockService.getAvailableStock(userId);
    }

    @RequestMapping(value = "/getAvailableStockDetails/{userId}/{companyId}" ,method = RequestMethod.GET)
    public List<ChildStockDto> getAvailableStockDetails(@PathVariable(value = "userId") String userId , @PathVariable(value = "companyId") String companyId){
        return stockService.getAvailableStockDetails(userId,companyId);
    }

    @RequestMapping(value = "/saveDividend" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse saveDividend(@RequestBody final Dividend dividend){
        int m = Calendar.getInstance().getTime().getMinutes();
        int h = Calendar.getInstance().getTime().getHours();
        int s = Calendar.getInstance().getTime().getSeconds();
        dividend.getDivDate().setHours(h);
        dividend.getDivDate().setMinutes(m);
        dividend.getDivDate().setSeconds(s);
        return stockService.saveDividend(dividend);
    }

    @RequestMapping(value = "/saveDeposit" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse saveDeposit(@RequestBody final Deposit deposit){
        int m = Calendar.getInstance().getTime().getMinutes();
        int h = Calendar.getInstance().getTime().getHours();
        int s = Calendar.getInstance().getTime().getSeconds();
        deposit.getDate().setHours(h);
        deposit.getDate().setMinutes(m);
        deposit.getDate().setSeconds(s);
        return stockService.saveDeposit(deposit);
    }


    @RequestMapping(value = "/getAllDepositByUser/{userId}" ,method = RequestMethod.GET)
    public List<DepositDto> getAllDepositByUser(@PathVariable(value = "userId") String userId ){
        return stockService.getAllDepositByUser(userId);
    }

    @RequestMapping(value = "/loadDividendToTable/{userId}" ,method = RequestMethod.GET)
    public List<DividendDto> loadDividendToTable(@PathVariable(value = "userId") String userId ){
        return stockService.loadDividendToTable(userId);
    }

    @RequestMapping(value = "/deleteDividend/{id}/{userId}" ,method = RequestMethod.DELETE)
    public TransactionResponse deleteDividend(@PathVariable(value = "id") String id ,@PathVariable(value = "userId") String userId ){
        return stockService.deleteDividend(id,userId);
    }

    @RequestMapping(value = "/deleteDeposit/{id}/{userId}" ,method = RequestMethod.DELETE)
    public TransactionResponse deleteDeposit(@PathVariable(value = "id") String id ,@PathVariable(value = "userId") String userId ){
        return stockService.deleteDeposit(id,userId);
    }

    @RequestMapping(value = "/getPreviousDeposits/{userId}" ,method = RequestMethod.GET)
    public PreviousDeposits getPreviousDeposits(@PathVariable(value = "userId") String userId ){
        return stockService.getPreviousDeposits(userId);
    }

    @RequestMapping(value = "/getPreviousWidthdrws/{userId}" ,method = RequestMethod.GET)
    public PreviousWidthdrws getPreviousWidthdrws(@PathVariable(value = "userId") String userId ){
        return stockService.getPreviousWidthdrws(userId);
    }



    @RequestMapping(value = "/splitStock" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse saveStockSplit(@RequestBody final Split split){
        int m = Calendar.getInstance().getTime().getMinutes();
        int h = Calendar.getInstance().getTime().getHours();
        int s = Calendar.getInstance().getTime().getSeconds();
        split.getSplitDate().setHours(h);
        split.getSplitDate().setMinutes(m);
        split.getSplitDate().setSeconds(s);
        return stockService.saveStockSplit(split);
    }

    @RequestMapping(value = "/saveIpoRI" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse saveIpoRI(@RequestBody final IPORI ipori){
        int m = Calendar.getInstance().getTime().getMinutes();
        int h = Calendar.getInstance().getTime().getHours();
        int s = Calendar.getInstance().getTime().getSeconds();
        ipori.getDate().setHours(h);
        ipori.getDate().setMinutes(m);
        ipori.getDate().setSeconds(s);
        return stockService.saveIpoRI(ipori);
    }


    @RequestMapping(value = "/getAllIpoRIByUser/{userId}" ,method = RequestMethod.GET)
    public List<IPORIDto> getAllIpoRIByUser(@PathVariable(value = "userId") String userId ){
        return stockService.getAllIpoRIByUser(userId);
    }

    @RequestMapping(value = "/deleteIpoRI/{id}/{userId}" ,method = RequestMethod.DELETE)
    public TransactionResponse deleteIpoRI(@PathVariable(value = "id") String id ,@PathVariable(value = "userId") String userId ){
        return stockService.deleteIpoRI(id,userId);
    }

    @RequestMapping(value = "/loadSplitedDataToTable/{userId}" ,method = RequestMethod.GET)
    public List<SplitDto> getAllISplitsByUser(@PathVariable(value = "userId") String userId ){
        return stockService.getAllISplitsByUser(userId);
    }

    @RequestMapping(value = "/deleteSplit/{id}/{userId}" ,method = RequestMethod.DELETE)
    public TransactionResponse deleteSplit(@PathVariable(value = "id") String id ,@PathVariable(value = "userId") String userId ){
        return stockService.deleteSplit(id,userId);
    }


    @RequestMapping(value = "/saveWidthdrw" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse saveWidthdrw(@RequestBody final Widthdrw widthdrw){
        int m = Calendar.getInstance().getTime().getMinutes();
        int h = Calendar.getInstance().getTime().getHours();
        int s = Calendar.getInstance().getTime().getSeconds();
        widthdrw.getDate().setHours(h);
        widthdrw.getDate().setMinutes(m);
        widthdrw.getDate().setSeconds(s);
        return stockService.saveWidthdrw(widthdrw);
    }

    @RequestMapping(value = "/getAllWidthdrwByUser/{userId}" ,method = RequestMethod.GET)
    public List<DepositDto> getAllWidthdrwByUser(@PathVariable(value = "userId") String userId ){
        return stockService.getAllWidthdrwByUser(userId);
    }


    @RequestMapping(value = "/deleteWidthdrw/{id}/{userId}" ,method = RequestMethod.DELETE)
    public TransactionResponse deleteWidthdrw(@PathVariable(value = "id") String id ,@PathVariable(value = "userId") String userId ){
        return stockService.deleteWidthdrw(id,userId);
    }


}
