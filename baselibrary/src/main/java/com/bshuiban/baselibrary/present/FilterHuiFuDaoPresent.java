package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.FilterHuiFuDaoContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Created by xinheng on 2018/7/3.<br/>
 * describe：慧辅导筛选
 */
public class FilterHuiFuDaoPresent extends BasePresent<FilterHuiFuDaoContract.View> implements FilterHuiFuDaoContract.Present {
    private Gson gson=new Gson();
    public FilterHuiFuDaoPresent(FilterHuiFuDaoContract.View view) {
        super(view);
    }
    @Override
    public void getScreeningData(String json) {
        dealWithJson("getVipRelationEduList",json);
    }
    private void dealWithJson(String key,String json) {
        askInternet(key, json, new RetrofitService.CallHTMLJsonArray() {
            @Override
            protected void success(JsonArray msg) {
                if (isEffective()) {
                    view.loadScreeningData(gson.toJson(msg));
                }
            }

            @Override
            protected void fail(String error) {
                if (isEffective()) {
                    view.fail(error);
                }
            }
        });
    }

    @Override
    public void getClassTypeUse(String json) {
        dealWithJson("getClassTypeUse",json);
    }
}
