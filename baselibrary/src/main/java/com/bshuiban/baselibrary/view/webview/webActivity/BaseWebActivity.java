package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bshuiban.baselibrary.view.activity.BaseActivity;

public class BaseWebActivity extends BaseActivity {
    protected WebView mWebView;
    protected String TAG="HTML5";
    protected void setTAG(String tag){
        TAG=tag;
    }
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

    /**
     * 标识 android
     * 类名 BaseWebActivity.WebViewInterface
     */
    protected void registerWebViewH5Interface(){
        mWebView.addJavascriptInterface(new WebViewInterface(), "android");
    }
    protected void loadJavascriptMethod(String methodName,String json){
        mWebView.loadUrl("javascript:" + methodName + "('" + json + "' ");
    }
    protected void initWebClinet(WebView webView){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
                return super.onRenderProcessGone(view, detail);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        if(null!=mWebView){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebView.clearHistory();
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView=null;
        }
        super.onDestroy();
    }
    class WebViewInterface {
        @JavascriptInterface
        public void log(String msg){
            Log.e(TAG, "log: "+msg );
        }
        @JavascriptInterface
        public void logTag(String tag,String msg){
            Log.e(TAG, "logTag: tag="+tag+", smg="+msg );
        }
        @JavascriptInterface
        public void dealWithJson(final String key, final String json){
            if(null!=baseRunnable){
                baseRunnable.reSetParma(key,json);
            }else{
                baseRunnable=new BaseRunnable(key,json);
            }
            runOnUiThread(baseRunnable);
        }
    }
    private BaseRunnable baseRunnable;
    class BaseRunnable implements Runnable {
        private String json;
        private String key;

        public BaseRunnable(String key, String json){
            this.key=key;
            this.json=json;
        }
        public void reSetParma(String key, String json){
            this.key=key;
            this.json=json;
        }
        @Override
        public void run() {
            tPresent.askInternet(key,json);
        }
    }
}
