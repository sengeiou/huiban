package com.bshuiban.baselibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.LogUtils;

/**
 * Created by xinheng on 2018/6/27.<br/>
 * describe：webView 工具
 */
public class WebViewUtil {
    private String TAG;
    private WebView mWebView;
    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    /**
     * 创建一个webView
     * @param context Application
     * @return webView对象
     */
    public WebView createNewWebView(Context context){
        mWebView = ViewUtils.getWebView(context);
        return mWebView;
    }

    public void setWebView(WebView webView) {
        this.mWebView = webView;
    }

    public WebView getWebView() {
        return mWebView;
    }

    /**
     * 加载本地网页
     * @param name
     */
    public void loadFileHtml(String name){
        loadPathHtml("file:///android_asset/"+name+".html");
    }

    /**
     * 加载网页
     * @param name
     */
    public void loadPathHtml(String name){
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(null!=onWebViewListener) {
                    onWebViewListener.onPageFinished(view,url);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.lastIndexOf("finish_page.html")!=-1){
                    if(null!=onWebViewListener) {
                        onWebViewListener.finishPage();
                        return true;
                    }
                }
                Log.e(TAG, "shouldOverrideUrlLoading: "+url );
                if(url.contains("huiban:80")){
//                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
//                    getWebView().getContext().startActivity(intent);
//                    return true;
                    return super.shouldOverrideUrlLoading(view,url);
                }
                return true;
            }
        });
        setWebViewSetting(mWebView);
        //webView.loadUrl("content://com.ansen.webview/sdcard/test.html");
        mWebView.loadUrl(name);
    }
    private WebSettings setWebViewSetting(WebView webView){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        return settings;
    }
    /**
     * 标识 android
     * 类名 BaseWebActivity.WebViewInterface
     * @param object 类
     */
    @SuppressLint("JavascriptInterface")
    public void registerWebViewH5Interface(Object object){
        mWebView.addJavascriptInterface(object, "android");
    }

    /**
     * 调用html5 js 方法
     * @param methodName
     * @param datas
     */
    public void loadJavascriptMethod(String methodName,String... datas){
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
        LogUtils.e(TAG, "loadJavascriptMethod: "+url );
        mWebView.loadUrl(url);
    }
    private String replaceJson(String json){
        if(TextUtils.isEmpty(json)){
            return json;
        }
        return com.bshuiban.baselibrary.utils.aes.Base64.encode(json.getBytes());
//        return json.replace("\\", "\\\\");//没办法
    }
    public void destroy(){
        if(null!=mWebView){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebView.clearHistory();
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView=null;
        }
    }
    private OnWebViewListener onWebViewListener;

    public void setOnWebViewListener(OnWebViewListener onWebViewListener) {
        this.onWebViewListener = onWebViewListener;
    }

    /**
     * 网页调试页面
     * @return 布局路径
     */
    public int getTestLayoutResource(){
        return R.layout.activity_test_web_view;
    }

    /**
     * 初始化调试页面
     * @param view
     */
    public void initTestWebView(View view) {
        mWebView = view.findViewById(R.id.webview);
        if(null!=mWebView) {
            setWebViewSetting(mWebView);
            TextView tv_sure = view.findViewById(R.id.tv_sure);
            EditText et_content = view.findViewById(R.id.et_content);
            et_content.setText("http://192.168.0.3:90/");
            tv_sure.setOnClickListener(v -> loadPathHtml(et_content.getText().toString().trim()));
        }
    }

    /**
     * webView
     */
    public interface OnWebViewListener{
        void onPageFinished(WebView view, String url);
        void finishPage();
    }
}
