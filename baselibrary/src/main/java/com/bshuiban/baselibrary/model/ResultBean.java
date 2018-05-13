package com.bshuiban.baselibrary.model;

public class ResultBean {
    public static final String SUCCESS_CODE1="2002";
    public static final String SUCCESS_CODE2="1000";
    protected String code;
    protected String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess(){
        return isSuccess(code);
    }
    public static boolean isSuccess(String code){
        if(SUCCESS_CODE1.equals(code)||SUCCESS_CODE2.equals(code)){
            return true;
        }
        return false;
    }
}
