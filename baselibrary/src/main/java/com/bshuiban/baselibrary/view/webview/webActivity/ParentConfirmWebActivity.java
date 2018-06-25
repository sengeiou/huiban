package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.ParentConfirmContract;
import com.bshuiban.baselibrary.present.ParentConfirmPresent;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：家长确认
 */
public abstract class ParentConfirmWebActivity<T extends ParentConfirmPresent> extends BaseWebActivity<T>implements ParentConfirmContract.View{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=getPresent();
        loadFileHtml("parentalConfirmation");
        registerWebViewH5Interface(new ParentConfirmHtml());
    }

    protected abstract T getPresent();

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadListData();
    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("pconfirm",json);
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

    protected class ParentConfirmHtml{
        /**
         * 列表点击
         * @param subjectId 学科确认id
         */
        @JavascriptInterface
        public void itemClick(int subjectId){
            runOnUiThread(()-> startActivity(new Intent(getApplicationContext(),getActivityClass()).putExtra("subjectId",subjectId)));
        }
    }

    protected abstract Class<?> getActivityClass();
}
