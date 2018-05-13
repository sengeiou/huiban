package com.bshuiban.baselibrary.view.webview.webActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.present.LoginPresent;

public class WebLoginActivity extends AppCompatActivity {
    WebView webView;
    private final String TAG="HTML5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);
        webView=findViewById(R.id.webView);
        init();
    }

    private void init() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new Test(),"android");
        webView.loadUrl("file:///android_asset/login.html");
        Log.e(TAG, "log: msg = loadUrl" );
    }
    class Test{
        @JavascriptInterface
        public void log(String msg){
            Log.e(TAG, "log: msg = "+msg );
        }
        @JavascriptInterface
        public void login(String userId,String passWord){
            new LoginPresent(null).login(userId,passWord);
        }
    }
}
