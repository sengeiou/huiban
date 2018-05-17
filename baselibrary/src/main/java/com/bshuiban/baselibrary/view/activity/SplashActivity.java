package com.bshuiban.baselibrary.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.view.webview.webActivity.WebLoginActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.util.List;


public class SplashActivity extends BaseActivity {
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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //if(SharedPreferencesUtils.instance(getApplication()).getBooleanKey(SharedPreferencesUtils.SP_IS_FIRST_STUDENT_LOGIN,true)){
                    //startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                //}else{
                try {
                    //startActivity(new Intent(SplashActivity.this,Class.forName("com.bshuiban.student.view.activity.StudentHomeActivity")));
                    startActivity(new Intent(SplashActivity.this,Class.forName("com.bshuiban.baselibrary.view.webview.webActivity.WebLoginActivity")));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                finish();
                //}
            }
        },2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
