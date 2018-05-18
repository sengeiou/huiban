package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.contract.ListContract;
import com.bshuiban.baselibrary.present.CollectionPresent;
import com.bshuiban.baselibrary.utils.ViewUtils;

public class CollectionListActivity extends BaseWebActivity<CollectionPresent> implements ListContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = ViewUtils.getFrameLayout(this);
        mWebView = getWebView(getApplicationContext());
        frameLayout.addView(mWebView);
        setContentView(frameLayout);
        tPresent=new CollectionPresent(this);
        setWebViewSetting(mWebView);
        registerWebViewH5Interface();
        loadFileHtml("follow");
        tPresent.getInterNetData();
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(tag){
                    loadJavascriptMethod("getContent",json);
                }else{
                    tag=true;
                }
            }
        });
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
