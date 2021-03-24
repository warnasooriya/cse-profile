package com.stock.soft.socksoft.Dto;

public class TransactionResponse {
    private String status;
    private String message;
    private Object resObj;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResObj() {
        return resObj;
    }

    public void setResObj(Object resObj) {
        this.resObj = resObj;
    }
}
