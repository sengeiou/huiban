package com.bshuiban.baselibrary.view.webview.webActivity;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.utils.WebViewFactory;
import com.bshuiban.baselibrary.view.activity.BaseActivity;

public class BaseWebActivity<T extends BasePresent> extends BaseActivity<T> {
    protected WebView mWebView;
    private WebViewFactory mWebViewFactory;
    protected String TAG="TAG"+getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebViewFactory = new WebViewFactory();
        mWebViewFactory.setOnWebViewListener(new WebViewFactory.OnWebViewListener() {
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
            mWebView = mWebViewFactory.createNewWebView(getApplicationContext());
            frameLayout.addView(mWebView);
            setContentView(frameLayout);
        }
    }
    protected boolean initWebView(){
        return true;
    }
    protected void setTAG(String tag){
        mWebViewFactory.setTAG(tag);
    }
    /**
     * 加载本地网页
     * @param name
     */
    protected void loadFileHtml(String name){
        mWebViewFactory.loadFileHtml(name);
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
        mWebViewFactory.registerWebViewH5Interface(object);
    }
    protected void loadJavascriptMethod(String methodName,String... datas){
        mWebViewFactory.loadJavascriptMethod(methodName,datas);
    }

    @Override
    protected void onDestroy() {
        mWebViewFactory.destroy();
        mWebView=null;
        super.onDestroy();
    }
}
