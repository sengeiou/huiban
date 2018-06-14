package com.bshuiban.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.LoginContract;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;
import com.bshuiban.baselibrary.view.activity.BaseActivity;
import com.bshuiban.present.LoginPresent;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.util.List;


public class SplashActivity extends BaseActivity<LoginPresent> implements LoginContract.View {
    Handler mHandler = new Handler(Looper.getMainLooper());
    ImageView ivSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();

    }

    @PermissionYes(100)
    private void getPermissionYes(List<String> grantedPermissions) {
        // TODO ����Ȩ�޳ɹ���
    }

    @PermissionNo(100)
    private void getPermissionNo(List<String> deniedPermissions) {
        // TODO ����Ȩ��ʧ�ܡ�
    }

    private void initData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .callback(this)
                    .start();
        }else{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                AndPermission.with(this)
                        .requestCode(2000)
                        .permission(Manifest.permission.CAMERA)
                        .callback(this)
                        .start();
            }
        }

        ivSplash = findViewById(R.id.iv_splash);
        String resUserJson = UserSharedPreferencesUtils.getUserResJson(getApplicationContext());
        if (!TextUtils.isEmpty(resUserJson)) {
            tPresent=new LoginPresent(this);
            //String userJson = AESUtils.desEncrypt(resUserJson);
            String userJson = resUserJson;
            LoginResultBean.Data data = null;
            try {
                Gson gson = new Gson();
                data = gson.fromJson(userJson, LoginResultBean.Data.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            if (null != data) {
                String userId = data.getUserId();
                String passWord = data.getPassWord();
                tPresent.login(userId, passWord);
            } else {
                toNext();
            }
        }else {
            toNext();
        }
    }

    private void toNext() {
        mHandler.postDelayed(() -> {
            try {
                startActivity(new Intent(SplashActivity.this,Class.forName("com.bshuiban.view.webActivity.WebLoginActivity")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            finish();
        },2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void loginSuccessToNextActivity(Class<?> cls, LoginResultBean.Data loginData) {
        User.getInstance().setData(loginData);
        startActivity(new Intent(getApplicationContext(), cls));
        finish();
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
