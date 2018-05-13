package com.bshuiban.baselibrary.view.activity;

import android.content.Intent;
import android.os.Bundle;
import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.LoginContract;
import com.bshuiban.baselibrary.present.LoginPresent;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity<LoginPresent> implements LoginContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tPresent = new LoginPresent(this);
    }


    @Override
    public String getUserAccountNumber() {
        return null;
    }

    @Override
    public String getPassWord() {
        return null;
    }

    @Override
    public void loginSuccessToNextActivity(Class<?> cls) {
        if(null==cls){
            toast("账号类型错误");
            return;
        }
        startActivity(new Intent(this,cls));
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
