package com.bshuiban.teacher.view.webView.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.teacher.contract.FilterConditionContract;
import com.bshuiban.teacher.present.FilterConditionPresent;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：课程筛选
 */
public class FilterConditionActivity extends BaseWebActivity<FilterConditionPresent> implements FilterConditionContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new FilterConditionPresent(this);
        loadFileHtml(getFileHtmlName());
        registerWebViewH5Interface(new FilterConditionHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadAllSubject();
    }

    protected String getFileHtmlName(){
        return "change";
    }
    @Override
    public void updateView(String json) {
        loadJavascriptMethod(getJavascriptMethodName(),json);
    }

    @Override
    public void loadAllSubject(String json) {
        loadJavascriptMethod("getListNav",json);
    }

    protected String getJavascriptMethodName(){
        return "sxContent";
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
    class FilterConditionHtml{
        @JavascriptInterface
        public void dealWithJson(String key,String json){
            tPresent.loadFilterCondition(key,json);
        }

        /**
         * 确认（提交筛选条件）
         * @param json
         */
        @JavascriptInterface
        public void commitCondition(String json){
            runOnUiThread(()->{
                Intent i = new Intent();
                i.putExtra("text", json);
                setResult(RESULT_OK, i);
                finish();
            });
        }
    }
}
