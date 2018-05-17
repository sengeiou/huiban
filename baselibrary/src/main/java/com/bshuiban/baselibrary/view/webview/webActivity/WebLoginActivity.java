package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.LoginContract;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.LoginPresent;
import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.utils.aes.AESUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class WebLoginActivity extends BaseWebActivity<LoginPresent> implements LoginContract.View {
    private final String TAG = "HTML5";
    private Gson gson = new Gson();
    private final static String HTML_NAME="login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = ViewUtils.getFrameLayout(this);
        mWebView = getWebView(getApplicationContext());
        frameLayout.addView(mWebView);
        setContentView(frameLayout);
        //mWebView.loadData(null, "text/html", "utf-8");
        tPresent = new LoginPresent(this);
        init();
    }
    class LoginHtml{
        @JavascriptInterface
        public void log(String msg){
            Log.e(TAG, "log: "+msg );
        }
        @JavascriptInterface
        public void login(final String userId,final String passwrod){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tPresent.login(userId,passwrod);
                }
            });
        }
    }
    private void init() {
        setWebViewSetting(mWebView);
        loadFileHtml(HTML_NAME);
        //registerWebViewH5Interface();
        mWebView.addJavascriptInterface(new LoginHtml(),"android");
        String resUserJson = UserSharedPreferencesUtils.getUserResJson(getApplicationContext());
        if (!TextUtils.isEmpty(resUserJson)) {
            //String userJson = AESUtils.desEncrypt(resUserJson);
            String userJson = resUserJson;
            LoginResultBean.Data data = null;
            try {
                data = gson.fromJson(userJson, LoginResultBean.Data.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            if (null != data) {
                String userId = data.getUserId();
                String passWord = data.getPassWord();
                tPresent.login(userId, passWord);
                loadJavascriptMethod("setLoginData", userId, passWord);
            }
        }
    }

    @Override
    protected void dealWidth(String key, String json) {
        tPresent.askInternet(key,json);
    }

    @Override
    public void loginSuccessToNextActivity(Class<?> cls, LoginResultBean.Data loginData) {
        saveUserData(loginData);
        dismissDialog();
        //startActivity(new Intent(getApplicationContext(), cls));
        try {
            startActivity(new Intent(getApplicationContext(), Class.forName("com.bshuiban.baselibrary.view.webview.webActivity.CollectionListActivity")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finish();
    }

    private void saveUserData(LoginResultBean.Data loginData) {
        User.getInstance().setData(loginData);
        //UserSharedPreferencesUtils.saveUserData(getApplicationContext(),AESUtils.encrypt(new Gson().toJson(loginData)));
        UserSharedPreferencesUtils.saveUserData(getApplicationContext(),new Gson().toJson(loginData));
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {

    }
}
