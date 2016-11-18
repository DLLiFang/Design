package com.xanthus.design.bean;

/**
 * Created by liyiheng on 2016/11/18.
 */

public class Wrapper<T> {
    private int resultCode;
    private String message;
    private T result;


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
