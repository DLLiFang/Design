package com.xanthus.design.bean;

import java.util.List;

/**
 * Created by liyiheng on 2016/11/18.
 */

public class Wrapper2<T> {
    private int resultCode;
    private String message;
    private List<T> result;


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

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
