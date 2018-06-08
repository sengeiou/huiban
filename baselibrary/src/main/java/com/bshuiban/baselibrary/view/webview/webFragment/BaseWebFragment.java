package com.bshuiban.baselibrary.view.webview.webFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.fragment.BaseFragment;
/**
 * Created by xinheng on 2018/5/11.<br/>
 * describe：含有webView的fragment
 */
public class BaseWebFragment<T extends BasePresent> extends BaseFragment<T> {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWebView = getWebView(getActivity().getApplicationContext());
        FrameLayout frameLayout = ViewUtils.getFrameLayout(getActivity());
        frameLayout.addView(mWebView);
        return frameLayout;
    }

    /**
     * 加载本地网页
     * @param name
     */
    protected void loadFileHtml(String name){
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webViewLoadFinished();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "shouldOverrideUrlLoading: "+url );
                return true;
            }
        });
        setWebViewSetting(mWebView);
        //webView.loadUrl("content://com.ansen.webview/sdcard/test.html");
        mWebView.loadUrl("file:///android_asset/"+name+".html");
    }

    protected void webViewLoadFinished() {

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
        //TODO 勿删，‘\\’js自动转‘\’
        return json.replace("\\", "\\\\");//没办法
    }
    @Override
    public void onDetach() {
        if(null!=mWebView){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebView.clearHistory();
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView=null;
        }
        super.onDetach();
    }
    protected void delete(String messageId, String pid){

    }
    class WebViewInterface {
        /**
         * 删除
         * @param messageId 消息id
         * @param pid 上一级留言id
         */
        @JavascriptInterface
        public void delete(final String messageId, final String pid){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    delete(messageId,pid);
                }
            });
        }
        @JavascriptInterface
        public void log(String msg){
            Log.e(TAG, "log: "+msg );
        }
        @JavascriptInterface
        public void logTag(String tag,String msg){
            Log.e(TAG, "logTag: tag="+tag+", smg="+msg );
        }
        @JavascriptInterface
        public void dealWithJson(String key, String json){
            if(null==json){
                json=getJsonString();
            }
            if(null!=baseRunnable){
                baseRunnable.reSetParma(key,json);
            }else{
                baseRunnable=new BaseRunnable(key,json);
            }
            getActivity().runOnUiThread(baseRunnable);
        }
    }
    private BaseWebFragment.BaseRunnable baseRunnable;
    protected String getJsonString(){
        return null;
    }
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
            if(TextUtils.isEmpty(key)){
                toast("key 错误");
                return;
            }
            if(TextUtils.isEmpty(json)){
                toast("json数据错误");
                return;
            }
            //tPresent.askInternet(key,json);
        }
    }
}
