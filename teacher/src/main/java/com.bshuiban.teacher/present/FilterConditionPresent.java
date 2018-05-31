package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.FilterConditionContract;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public class FilterConditionPresent extends BasePresent<FilterConditionContract.View> implements FilterConditionContract.Present {
    public FilterConditionPresent(FilterConditionContract.View view) {
        super(view);
    }

    @Override
    public void loadFilterCondition(String key, String json) {
        askInternet(key, json, new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateView(msg);
                }
            }

            @Override
            protected void fail(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }
}
