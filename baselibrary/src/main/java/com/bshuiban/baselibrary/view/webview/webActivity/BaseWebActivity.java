package com.bshuiban.baselibrary.view.webview.webActivity;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.utils.WebViewUtil;
import com.bshuiban.baselibrary.view.activity.BaseActivity;

public class BaseWebActivity<T extends BasePresent> extends BaseActivity<T> {
    protected WebView mWebView;
    private WebViewUtil mWebViewUtil;
    protected String TAG="TAG"+getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebViewUtil = new WebViewUtil();
        mWebViewUtil.setOnWebViewListener(new WebViewUtil.OnWebViewListener() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webViewLoadFinished();
            }

            @Override
            public void finishPage() {
                finish();
            }
        });
        if(initWebView()) {
            FrameLayout frameLayout = ViewUtils.getFrameLayout(this);
            mWebView = mWebViewUtil.createNewWebView(getApplicationContext());
            frameLayout.addView(mWebView);
            setContentView(frameLayout);
        }
    }
    protected boolean initWebView(){
        return true;
    }
    protected void setTAG(String tag){
        mWebViewUtil.setTAG(tag);
    }
    /**
     * 加载本地网页
     * @param name
     */
    protected void loadFileHtml(String name){
        if(null== mWebViewUtil.getWebView()){
            mWebViewUtil.setWebView(mWebView);
        }
        mWebViewUtil.loadFileHtml(name);
    }

    /**
     * 网页加载完之后
     */
    protected void webViewLoadFinished() {

    }


    /**
     * 标识 android
     * 类名 BaseWebActivity.WebViewInterface
     * @param object 类
     */
    @SuppressLint("JavascriptInterface")
    protected void registerWebViewH5Interface(Object object){
        mWebViewUtil.registerWebViewH5Interface(object);
    }
    protected void loadJavascriptMethod(String methodName,String... datas){
        mWebViewUtil.loadJavascriptMethod(methodName,datas);
    }

    @Override
    protected void onDestroy() {
        mWebViewUtil.destroy();
        mWebView=null;
        super.onDestroy();
    }
}
