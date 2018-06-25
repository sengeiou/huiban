package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.SubjectBean;
import com.bshuiban.baselibrary.present.AllSubjectPresent;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.FilterConditionContract;
import com.google.gson.Gson;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public class FilterConditionPresent extends BasePresent<FilterConditionContract.View> implements FilterConditionContract.Present {
    private final AllSubjectPresent allSubjectPresent;
    protected Gson gson=new Gson();
    public FilterConditionPresent(FilterConditionContract.View view) {
        super(view);
        allSubjectPresent = new AllSubjectPresent<FilterConditionContract.View>(view) {
            @Override
            protected void loadAllSubject(SubjectBean dataBean) {
                FilterConditionPresent.this.loadAllSubject(dataBean);
            }
        };
    }

    private void loadAllSubject(SubjectBean dataBean) {
        if (isEffective() && null != dataBean.getData()) {
            view.loadAllSubject(gson.toJson(dataBean.getData()));
        }
    }
    @Override
    public void loadAllSubject() {
        allSubjectPresent.getAllSubject();
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
