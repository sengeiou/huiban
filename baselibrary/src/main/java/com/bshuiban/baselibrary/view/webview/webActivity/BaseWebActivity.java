package com.bshuiban.baselibrary.view.webview.webActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.activity.BaseActivity;

public class BaseWebActivity<T extends BasePresent> extends BaseActivity<T> {
    protected WebView mWebView;
    protected String TAG="HTML5";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(initWebView()) {
            FrameLayout frameLayout = ViewUtils.getFrameLayout(this);
            mWebView = getWebView(getApplicationContext());
            frameLayout.addView(mWebView);
            setContentView(frameLayout);
        }
    }
    protected boolean initWebView(){
        return true;
    }
    protected void setTAG(String tag){
        TAG=tag;
    }
    protected WebView getWebView(Context context){
        WebView webView = new WebView(context);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        return webView;
    }
    /**
     * 加载本地网页
     * @param name
     */
    protected void loadFileHtml(String name){
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webViewLoadFinished();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "shouldOverrideUrlLoading: "+url );
                if(url.lastIndexOf("finish_page.html")!=-1){
                    finish();
                }
                return true;
            }
        });
        setWebViewSetting(mWebView);
        //webView.loadUrl("content://com.ansen.webview/sdcard/test.html");
        mWebView.loadUrl("file:///android_asset/"+name+".html");
    }

    /**
     * 网页加载完之后
     */
    protected void webViewLoadFinished() {

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

    /**
     * 标识 android
     * 类名 BaseWebActivity.WebViewInterface
     * @param object 类
     */
    @SuppressLint("JavascriptInterface")
    protected void registerWebViewH5Interface(Object object){
        mWebView.addJavascriptInterface(object, "android");
    }
    protected void loadJavascriptMethod(String methodName,String... datas){
        StringBuffer stringBuffer=new StringBuffer();
        int i1 = datas.length - 1;
        for (int i = 0; i < datas.length; i++) {
            stringBuffer.append("'");
            stringBuffer.append(replaceJson(datas[i]));
            stringBuffer.append("'");
            if(i< i1){
                stringBuffer.append(",");
            }
        }
        String url = "javascript:" + methodName + "(" + stringBuffer.toString() + ")";
        Log.e(TAG, "loadJavascriptMethod: "+url );
        //mWebView.loadUrl("javascript:setLoginData('2030246','111111')");
        mWebView.loadUrl(url);
    }
    private String replaceJson(String json){
        if(TextUtils.isEmpty(json)){
            return json;
        }
        return json.replace("\\", "\\\\");//没办法
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
        /**
         * 列表获取更多数据
         * @param action true-->下拉刷新，false-->上拉加载
         */
        @JavascriptInterface
        public void getMoreData(boolean action) {
            if (null == loadMoreDataRunnable) {
                loadMoreDataRunnable = new LoadMoreDataRunnable(action);
            } else {
                loadMoreDataRunnable.setTag(action);
            }
            runOnUiThread(loadMoreDataRunnable);
        }

        /**
         * 删除
         * @param messageId 消息id
         * @param pid 上一级留言id
         */
        @JavascriptInterface
        public void delete(final String messageId, final String pid){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    delete(messageId,pid);
                }
            });
        }
        /**
         * H5提供的接口数据
         * @param key
         * @param json
         */
        @JavascriptInterface
        public void dealWithJson(String key, String json){
            if(null!=baseRunnable){
                baseRunnable.reSetParma(key,json);
            }else{
                baseRunnable=new BaseRunnable(key,json);
            }
            runOnUiThread(baseRunnable);
        }
    }
    private LoadMoreDataRunnable loadMoreDataRunnable;
    class LoadMoreDataRunnable implements Runnable{
        private boolean tag;
        public LoadMoreDataRunnable(boolean tag){
            if(tag){
                refresh();
            }else {
                loadMoreData();
            }
        }
        @Override
        public void run() {

        }

        public void setTag(boolean tag) {
            this.tag = tag;
        }
    }

    /**
     * 下拉刷新
     */
    protected void refresh(){

    }
    protected void loadMoreData(){

    }
    protected void delete(String messageId,String pid){

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
            dealWidth(key,json);
        }
    }
    protected void dealWidth(String key, String json){

    }
}
