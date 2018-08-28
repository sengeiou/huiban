package com.bshuiban.baselibrary.view.webview.webFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.utils.WebViewUtil;
import com.bshuiban.baselibrary.view.fragment.BaseFragment;

/**
 * Created by xinheng on 2018/5/11.<br/>
 * describe：含有webView的fragment
 */
public class BaseWebFragment<T extends BasePresent> extends BaseFragment<T> {
    protected WebView mWebView;
    protected String TAG = "HTML5";
    protected WebViewUtil mWebViewUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebViewUtil = new WebViewUtil();
        mWebViewUtil.setOnWebViewListener(new WebViewUtil.OnWebViewListener() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webViewLoadFinished();
            }

            @Override
            public void finishPage() {

            }
        });
    }

    protected void setTAG(String tag) {
        mWebViewUtil.setTAG(tag);
    }

    protected WebView getWebView(Context context) {
        return mWebViewUtil.createNewWebView(context);
    }

    protected WebSettings setWebViewSetting(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        return settings;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWebView = getWebView(getActivity().getApplicationContext());
        FrameLayout frameLayout = ViewUtils.getFrameLayout(getActivity());
        frameLayout.addView(mWebView);
        return frameLayout;
    }

    protected int getLayoutResource() {
        return mWebViewUtil.getTestLayoutResource();
    }



    protected void loadPathHtml(String name) {
        mWebViewUtil.loadPathHtml(name);
    }

    /**
     * 加载本地网页
     *
     * @param name
     */
    protected void loadFileHtml(String name) {
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
     *
     * @param object 类
     */
    @SuppressLint("JavascriptInterface")
    protected void registerWebViewH5Interface(Object object) {
        mWebViewUtil.registerWebViewH5Interface(object);
    }

    protected void loadJavascriptMethod(String methodName, String... datas) {
        mWebViewUtil.loadJavascriptMethod(methodName, datas);
    }

    @Override
    public void onDetach() {
        mWebViewUtil.destroy();
        mWebView = null;
        super.onDetach();
    }
}
