package com.bshuiban.baselibrary.view.webview.webActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;

public class TestWebViewActivity extends BaseWebActivity {

    private TextView et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);
        mWebView=findViewById(R.id.webview);
        setWebViewSetting(mWebView);
        TextView tv_sure=findViewById(R.id.tv_sure);
        et_content=findViewById(R.id.et_content);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        et_content.setText("宽x高 ："+displayMetrics.widthPixels+"x"+displayMetrics.heightPixels+", "+displayMetrics.density+", "+displayMetrics.densityDpi+", "+displayMetrics.scaledDensity+", \n"+displayMetrics.toString());
        et_content.setTextColor(Color.BLACK);
        et_content.setBackgroundColor(Color.WHITE);
        et_content.setPadding(10,10,10,10);
        et_content.setTextSize(TypedValue.COMPLEX_UNIT_PX,44);
        mWebView.setWebChromeClient(new WebChromeClient(){
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        tv_sure.setOnClickListener(v-> mWebView.loadUrl(et_content.getText().toString().trim()));
        loadFileHtml("testTextSize");
    }

    @Override
    protected boolean initWebView() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
