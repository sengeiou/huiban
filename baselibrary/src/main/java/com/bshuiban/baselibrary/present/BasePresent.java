package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.internet.RetrofitService;

import okhttp3.ResponseBody;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：基本设置
 */
public class BasePresent<T> {
    protected T view;
    protected RetrofitService.CallHTML callHTML;
    protected retrofit2.Call<ResponseBody> call;

    public BasePresent(T t) {
        this.view = t;
        initCallHtml();
    }

    protected void initCallHtml() {

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

    /**
     * 访问网络
     * @param key 方法名
     * @param json json串
     */
    public void askInternet(String key,String json){
        call=RetrofitService.getInstance().getServiceResult(key,json,callHTML);
    }
    public void askInternet(String key,String json,RetrofitService.CallHTML callHTML){
        call=RetrofitService.getInstance().getServiceResult(key,json,callHTML);
    }

}