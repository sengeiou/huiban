package com.bshuiban.view.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.LoginContract;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.present.LoginPresent;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class WebLoginActivity extends BaseWebActivity<LoginPresent> implements LoginContract.View {
    private final String TAG = "HTML5";
    private Gson gson = new Gson();
    private final static String HTML_NAME="login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mWebView.loadData(null, "text/html", "utf-8");
        tPresent = new LoginPresent(this);
        init();
    }
    class LoginHtml{
        @JavascriptInterface
        public void log(String msg){
            if(msg!=null){
                toast(msg);
            }
        }
        @JavascriptInterface
        public void login(final String userId,final String passwrod){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startDialog();
                    tPresent.login(userId,passwrod);
                }
            });
        }
    }
    private void init() {
        loadFileHtml(HTML_NAME);
        registerWebViewH5Interface(new LoginHtml());
        String resUserJson = UserSharedPreferencesUtils.getUserResJson(getApplicationContext());
        if (!TextUtils.isEmpty(resUserJson)) {
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
                //tPresent.login("2030076", "111111");
                tPresent.login(userId, passWord);
                loadJavascriptMethod("setLoginData", userId, passWord);
            }
        }
    }

    @Override
    public void loginSuccessToNextActivity(Class<?> cls, LoginResultBean.Data loginData) {
        saveUserData(loginData);
        dismissDialog();
        startActivity(new Intent(getApplicationContext(), cls));
        finish();
    }

    private void saveUserData(LoginResultBean.Data loginData) {
        User.getInstance().setData(loginData);
        UserSharedPreferencesUtils.saveUserData(getApplicationContext(),new Gson().toJson(loginData));
    }

    @Override
    public void startDialog() {
        showLoadingDialog();
    }

    @Override
    public void dismissDialog() {
        dismissLoadingDialog();
    }

    @Override
    public void fail(String error) {
        toast(error);
    }
}
