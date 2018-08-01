package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.contract.ListContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.LogUtils;
import com.bshuiban.baselibrary.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public abstract class ListPresent<T extends BaseView> extends BasePresent<T> implements ListContract.Present {
    protected int limit=10;
    protected int start;
    protected JsonArray jsonArray;
    protected Gson gson=new Gson();

    public void setLimit(int limit) {
        this.limit = limit;
    }

    protected RetrofitService.CallHTMLJsonArray callHTMLJsonArray= new RetrofitService.CallHTMLJsonArray() {
        @Override
        protected void success(JsonArray msg) {
            LogUtils.e("TAG", "success: "+msg+", "+(null==jsonArray?0:jsonArray.size()));
            if(isEffective()){
                if(null==jsonArray){
                    jsonArray=msg;
                    String json;
                    if(null==jsonArray){
                        json=null;
                    }else {
                        json = gson.toJson(jsonArray);
                    }
                    updateView(json);
                }else{
                    if(null!=msg&&msg.size()>0) {
                        jsonArray.addAll(msg);
                        updateView(gson.toJson(jsonArray));
                    }
                }
                if(null==jsonArray||jsonArray.size()==0){
                    fail("暂无数据");
                }
            }
        }

        @Override
        protected void fail(String error) {
            ListPresent.this.fail(error);
        }
    };
    public ListPresent(T t) {
        super(t);
    }

    @Override
    public void loadMoreData() {
        start+=limit;
        getInterNetData();
    }

    @Override
    public void refresh() {
        start=0;
        clearArray();
        getInterNetData();
    }

    @Override
    public void getInterNetData() {

    }

    @Override
    public void updateView(String json) {

    }
    public void fail(String error){
        if(isEffective()){
            view.fail(error);
        }
    }
    @Override
    public void clearArray() {
        if(null!=jsonArray){
            jsonArray=null;
        }
    }
    protected JsonObject pareJsonObj(String json){
        return JsonUtils.parseJsonObjetNotNull(json);
    }
}
