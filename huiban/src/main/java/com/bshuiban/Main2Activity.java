package com.bshuiban;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.LoginResultBean;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {
    WebView webView;
    private final String TAG="HTML5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        webView=findViewById(R.id.webView);
        init();
    }

    private void init() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new Test(),"test");
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

            RetrofitService.getInstance().getServiceResult("logInByUidAndPwd",getMapJson(userId,passWord),new RetrofitService.CallTest(){
                @Override
                protected void result(String s) {
                    webView.loadUrl("javascript:fn('"+s+"')");
                }

                @Override
                protected void error(String s) {
                    webView.loadUrl("javascript:fn('"+s+"')");
                }
            });
        }
    }
        private Map<String, Object> getMapJson(String accountNumber, String password) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", accountNumber);
            map.put("userPwd", password);
            map.put("terminal", "7");//登录终端7慧班学生,8慧班老师,9慧班家长
            return map;
        }
}
