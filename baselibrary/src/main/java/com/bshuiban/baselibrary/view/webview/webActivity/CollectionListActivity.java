package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bshuiban.baselibrary.contract.ListContract;
import com.bshuiban.baselibrary.present.CollectionPresent;

public class CollectionListActivity extends BaseWebActivity<CollectionPresent> implements ListContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tPresent=new CollectionPresent(this);
        setWebViewSetting(mWebView);
        registerWebViewH5Interface();
        loadFileHtml("follow");
        tPresent.getInterNetData();
    }

    @Override
    protected void webViewLoadFinished() {
        if(tag){
            loadJavascriptMethod("getContent",json);
        }else{
            tag=true;
        }
    }

    private boolean tag=false;
    private String json;
    @Override
    public void updateList(String json) {
        if(tag) {
            loadJavascriptMethod("getContent", json);
        }else {
            this.json=json;
            tag=true;
        }
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {

    }

    @Override
    protected void loadMoreData() {
        tPresent.loadMoreData();
    }

    @Override
    protected void refresh() {
        tPresent.refresh();
    }
}
