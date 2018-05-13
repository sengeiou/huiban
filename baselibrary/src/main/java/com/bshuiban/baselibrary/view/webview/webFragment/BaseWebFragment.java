package com.bshuiban.baselibrary.view.webview.webFragment;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.view.fragment.BaseFragment;

/**
 * Created by xinheng on 2018/5/11.<br/>
 * describe：含有webView的fragment
 */
public class BaseWebFragment<T extends BasePresent> extends BaseFragment<T> {
    protected WebView mWebView;
    protected WebView getWebView(Context context){
        WebView webView = new WebView(context);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        return webView;
    }
    protected WebSettings setWebViewSetting(WebView webView){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        return settings;
    }

    @Override
    public void onDestroyView() {
        if(null!=mWebView){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebView.clearHistory();
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView=null;
        }
        super.onDestroyView();
    }
}
