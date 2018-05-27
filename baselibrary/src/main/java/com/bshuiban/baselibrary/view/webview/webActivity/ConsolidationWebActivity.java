package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.ConsolidationContract;
import com.bshuiban.baselibrary.present.ConsolidationPresent;

/**
 * 巩固练习
 */
public class ConsolidationWebActivity extends BaseWebActivity<ConsolidationPresent> implements ConsolidationContract.View {

    private int examId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        examId = getIntent().getIntExtra("examId", -1);
        tPresent=new ConsolidationPresent(this);
        loadFileHtml("practive");
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadConsolidationHomeworkData(examId);
    }

    @Override
    public void updateView(String json) {
        loadJavascriptMethod("getContent",json);
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
    class Consolidation{
        @JavascriptInterface
        public void commitData(String json){

        }
    }
}
