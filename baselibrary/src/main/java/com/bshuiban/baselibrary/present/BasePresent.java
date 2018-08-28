package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.internet.RetrofitService;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：基本设置
 */
public class BasePresent<T extends BaseView> {
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

    /**
     * 访问网络
     * @param key 方法名
     * @param json json串
     */
    public void askInternet(String key, String json, Callback<ResponseBody> callback){
        call=RetrofitService.getInstance().getServiceResult(key,json,callback);
    }
    public void askInternet(String key, Map<String,Object> json, Callback<ResponseBody> callback){
        call=RetrofitService.getInstance().getServiceMapResult(key,json,callback);
    }
}