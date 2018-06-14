package com.bshuiban.baselibrary.view.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.view.customer.TitleView;

public class AboutSelfActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_self);
        TextView mTvVersionName = findViewById(R.id.tv_versionName);
        TitleView titleView = findViewById(R.id.titleView);
        titleView.setOnClickListener(v -> finish());
        mTvVersionName.setText(getVersionName());
    }
    private String getVersionName() {
        //用来管理手机的APK
        PackageManager pm = getPackageManager();
        //得到知道APK的功能清单文件
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
