package com.bshuiban.baselibrary.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.UpdateAppContract;
import com.bshuiban.baselibrary.internet.ProgressResponseBody;
import com.bshuiban.baselibrary.internet.RetrofitDownload;
import com.bshuiban.baselibrary.model.UploadAppBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.UpdateAppPresent;
import com.bshuiban.baselibrary.utils.AppUpdate;
import com.bshuiban.baselibrary.utils.DialogUtils;
import com.bshuiban.baselibrary.utils.ScreenUtils;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.view.customer.TitleView;

import java.io.File;
import java.util.Calendar;

public class AboutSelfActivity extends BaseActivity<UpdateAppPresent> implements UpdateAppContract.View {

    private int versionCode;
    private RetrofitDownload retrofitDownload;
    private Dialog dialog;
    private String downUrl;
    private String version;
    private final int REQUEST_CODE_UNKNOWN_APP=100;
    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_self);
        TextView mTvVersionName = findViewById(R.id.tv_versionName);
        TextView tv_update = findViewById(R.id.tv_update);
        TitleView titleView = findViewById(R.id.titleView);
        titleView.setOnClickListener(this::onClick);
        mTvVersionName.setText(getVersionName());
        tv_update.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        int id = v.getId();
        if (id == R.id.titleView) {
            finish();
        } else if (id == R.id.tv_update) {
            if (null == tPresent) {
                tPresent = new UpdateAppPresent(AboutSelfActivity.this);
            }
            startDialog();
            tPresent.loadUpdate();
        } else if (id == R.id.tv_cancel) {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else if (id == R.id.tv_sure) {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
            detectionUpdate(downUrl, version);
        }
    }

    private String getVersionName() {
        //用来管理手机的APK
        PackageManager pm = getPackageManager();
        //得到知道APK的功能清单文件
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            versionCode = info.versionCode;
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    private void detectionUpdate(String url, String versionName) {
        url = "http://dz.80txt.com/71356/%E7%8E%84%E7%95%8C%E6%97%85%E8%A1%8C%E7%A4%BE.zip";
        String name = "huiban" + versionName + ".apk";
        String path = User.path + "app/" + name;
        if(new File(path).exists()){
            //AppUpdate.installApk(getApplicationContext(),path);
            //installApp(path);
            //return;
        }
        ProgressResponseBody.ProgressListener progressListener = (progress, total, done) -> {
            //Log.e(TAG, "onProgress: "+progress +", "+(int) (progress*100f/total));
            runOnUiThread(() -> {
                float value = progress * 1f / total;
                Dialog dialog = retrofitDownload.showDialog(AboutSelfActivity.this,value);
                if (value >= 1) {
                    dialog.dismiss();
                }
            });
        };
        retrofitDownload = new RetrofitDownload(progressListener);
        retrofitDownload.downloadFile(url, path);
        retrofitDownload.setDownloadListener(new RetrofitDownload.DownloadListener() {
            @Override
            public void loadFinish(String downLoadPath) {
                AppUpdate.installApk(getApplicationContext(),downLoadPath);
            }

            @Override
            public void loadFail(String error) {
                toast(error);
            }
        });
    }

    @Override
    public void updateView(UploadAppBean.DataBean dataBean) {
        downUrl = dataBean.getDownUrl();
        String describe = dataBean.getDescribe();
        int verNum = dataBean.getVerNum();
        version = dataBean.getVersion();
        dismissDialog();
        if (verNum <= versionCode) {
            //detectionUpdate(downUrl,version);
            updateDialogPrompt(version, describe);
        } else {
            toast("已经是最新版本了");
        }
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
        dismissDialog();
        toast(error);
    }

    private void updateDialogPrompt(String version_name, String descrip) {
        if (null == dialog) {
            dialog = new Dialog(this);
            Window window = dialog.getWindow();
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.layout_update_item);

            TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
            TextView tv_sure = dialog.findViewById(R.id.tv_sure);
            TextView tv_update_version_name = dialog.findViewById(R.id.tv_update_version_name);
            TextView tv_update_content = dialog.findViewById(R.id.tv_update_content);
            tv_update_version_name.setText("检测到最新版本："+TextUtils.cleanNull(version_name));
            if(android.text.TextUtils.isEmpty(descrip)){
                descrip="暂无描述";
            }
            tv_update_content.setText(descrip);
            tv_cancel.setOnClickListener(this::onClick);
            tv_sure.setOnClickListener(this::onClick);
        }
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width= (int) getResources().getDimension(R.dimen.dp_258);
        window.setAttributes(attributes);
        window.setGravity(Gravity.CENTER);
    }
    private void installApp(String path){
        Boolean aBoolean = AppUpdate.installApk(getApplicationContext(), path);
        if(aBoolean==null){

        }else if(!aBoolean){//8.0
            this.mPath=path;
            //跳转至“安装未知应用”权限界面，引导用户开启权限，可以在onActivityResult中接收权限的开启结果
            Uri packageURI = Uri.parse("package:"+getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
            startActivityForResult(intent, REQUEST_CODE_UNKNOWN_APP);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode==REQUEST_CODE_UNKNOWN_APP){
            installApp(mPath);
            //AppUpdate.install26(getApplicationContext(),new File(mPath));
        }
    }
}
