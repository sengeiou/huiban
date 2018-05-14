package com.bshuiban.baselibrary.view.webview.webFragment;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
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
            tPresent.askInternet(key,json);
        }
    }
}
