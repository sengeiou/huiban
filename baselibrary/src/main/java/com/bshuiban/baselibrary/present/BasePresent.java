package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.internet.RetrofitService;

import okhttp3.ResponseBody;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：基本设置
 */
public class BasePresent<T> {
    protected T view;
    protected retrofit2.Call<ResponseBody> call;

    public BasePresent(T t) {
        this.view = t;
    }

    public void cancel() {
        if (null != call && call.isCanceled()) {
            view = null;
            call.cancel();
        }
    }

    public boolean isEffective() {
        return null != view;
    }
    public void askInternet(String key,String json){
        RetrofitService.getInstance().getServiceResult(key,json,new RetrofitService.CallTest(){
            @Override
            protected void result(String s) {
                super.result(s);
            }
        });
    }
/*
    protected Map<String, Object> getMapinf(Object... objects) {
        return null;
    }*/
}