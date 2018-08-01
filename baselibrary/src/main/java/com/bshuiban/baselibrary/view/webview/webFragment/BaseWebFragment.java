package com.bshuiban.baselibrary.view.webview.webFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.utils.WebViewFactory;
import com.bshuiban.baselibrary.view.fragment.BaseFragment;

/**
 * Created by xinheng on 2018/5/11.<br/>
 * describe：含有webView的fragment
 */
public class BaseWebFragment<T extends BasePresent> extends BaseFragment<T> {
    protected WebView mWebView;
    protected String TAG = "HTML5";
    protected WebViewFactory mWebViewFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebViewFactory = new WebViewFactory();
        mWebViewFactory.setOnWebViewListener(new WebViewFactory.OnWebViewListener() {
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
        mWebViewFactory.setTAG(tag);
    }

    protected WebView getWebView(Context context) {
        return mWebViewFactory.createNewWebView(context);
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
        return mWebViewFactory.getTestLayoutResource();
    }



    protected void loadPathHtml(String name) {
        mWebViewFactory.loadPathHtml(name);
    }

    /**
     * 加载本地网页
     *
     * @param name
     */
    protected void loadFileHtml(String name) {
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
     *
     * @param object 类
     */
    @SuppressLint("JavascriptInterface")
    protected void registerWebViewH5Interface(Object object) {
        mWebViewFactory.registerWebViewH5Interface(object);
    }

    protected void loadJavascriptMethod(String methodName, String... datas) {
        mWebViewFactory.loadJavascriptMethod(methodName, datas);
    }

    @Override
    public void onDetach() {
        mWebViewFactory.destroy();
        mWebView = null;
        super.onDetach();
    }
}
