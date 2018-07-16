package com.bshuiban.teacher.view.webView.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bshuiban.baselibrary.contract.SendNoticeContract;
import com.bshuiban.baselibrary.model.TeachClassBean;
import com.bshuiban.baselibrary.present.SendNoticePresent;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：所教班级
 */
public class TeachClassWebActivity extends BaseWebActivity<SendNoticePresent>implements SendNoticeContract.View {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new SendNoticePresent(this);
        loadFileHtml("classmates");
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadClassList();
    }


    @Override
    public void updateClassList(List<TeachClassBean.DataBean> dataBeanList) {
        loadJavascriptMethod("rend",new Gson().toJson(dataBeanList));
    }

    @Override
    public void sendNoticeSuccess() {

    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        toast(error);
    }
}
