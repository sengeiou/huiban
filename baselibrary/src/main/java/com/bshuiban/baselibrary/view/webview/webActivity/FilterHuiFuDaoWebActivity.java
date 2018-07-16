package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.FilterHuiFuDaoContract;
import com.bshuiban.baselibrary.present.FilterHuiFuDaoPresent;

/**
 * Created by xinheng on 2018/7/3.<br/>
 * describe：
 */
public class FilterHuiFuDaoWebActivity extends BaseWebActivity<FilterHuiFuDaoPresent> implements FilterHuiFuDaoContract.View{

    private String subjectId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new FilterHuiFuDaoPresent(this);
        subjectId = getIntent().getStringExtra("subjectId");
        loadFileHtml("kcsx");
        registerWebViewH5Interface(new FilterHuiFuDaoHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.getScreeningData("{\"subjectId\":"+subjectId+"}");
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
    @Override
    public void loadScreeningData(String json) {
        loadJavascriptMethod("sxContent", (json));
    }
    class FilterHuiFuDaoHtml{
        @JavascriptInterface
        public void loadScreeningData(String json){
            tPresent.getScreeningData(json);
        }
        @JavascriptInterface
        public void loadClassTypeUse(String json){
            tPresent.getClassTypeUse(null);
        }
        /**
         * 确认筛选条件
         * @param json
         */
        @JavascriptInterface
        public void sureFilter(String json){
            runOnUiThread(()->{
                Intent i=new Intent();
                i.putExtra("json",json);
                setResult(RESULT_OK, i);
                finish();
            });
        }
    }
}
