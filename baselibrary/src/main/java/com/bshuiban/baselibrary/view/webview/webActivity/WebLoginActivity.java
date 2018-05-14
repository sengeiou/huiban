package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.LoginContract;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.present.LoginPresent;
import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;
import com.bshuiban.baselibrary.utils.aes.AESUtils;
import com.google.gson.Gson;

public abstract class WebLoginActivity extends BaseWebActivity<LoginPresent> implements LoginContract.View{
    private final String TAG="HTML5";
    private Gson gson= new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);
        tPresent = new LoginPresent(this);
        mWebView=findViewById(R.id.webView);
        init();
    }

    private void init() {
       setWebViewSetting(mWebView);
       registerWebViewH5Interface();
       String resUserJson = UserSharedPreferencesUtils.getUserResJson(getApplicationContext());
       if(!TextUtils.isEmpty(resUserJson)){
           String userJson = AESUtils.desEncrypt(resUserJson);
           LoginResultBean.Data data = gson.fromJson(userJson, LoginResultBean.Data.class);
           String userId = data.getUserId();
           String passWord="******";
           tPresent.login(userId,data.getPassWord());
           loadJavascriptMethod("setLoginData",userId,passWord);
       }
    }
    protected abstract void saveUserData(LoginResultBean.Data data);
    @Override
    public String getUserAccountNumber() {
        return null;
    }

    @Override
    public String getPassWord() {
        return null;
    }

    @Override
    public void loginSuccessToNextActivity(Class<?> cls, LoginResultBean.Data loginData) {
        saveUserData(loginData);
        dismissDialog();
        startActivity(new Intent(getApplicationContext(),cls));
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
