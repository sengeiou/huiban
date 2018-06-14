package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;

public class TestWebViewActivity extends BaseWebActivity {

    private EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);
        mWebView=findViewById(R.id.webview);
        setWebViewSetting(mWebView);
        TextView tv_sure=findViewById(R.id.tv_sure);
        et_content=findViewById(R.id.et_content);
        et_content.setText("http://192.168.0.3:90/");

        mWebView.setWebChromeClient(new WebChromeClient(){
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        tv_sure.setOnClickListener(v-> mWebView.loadUrl(et_content.getText().toString().trim()));
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
