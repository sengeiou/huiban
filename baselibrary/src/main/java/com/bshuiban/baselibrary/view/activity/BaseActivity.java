package com.bshuiban.baselibrary.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.NetUtils;
import com.bshuiban.baselibrary.utils.SystemStatusManager;
import com.bshuiban.baselibrary.view.dialog.HuiBanLoadingDialog;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：Activity 基本配置：状态栏一体化
 */
public class BaseActivity<T extends BasePresent> extends AppCompatActivity {
    protected T tPresent;
    private Toast toast;
    private HuiBanLoadingDialog myLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SystemStatusManager(this).setTranslucentStatus(R.color.guide_start_btn);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setFitsSystemWindowsTrue();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setFitsSystemWindowsTrue();
    }
    private void setFitsSystemWindowsTrue(){
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
    }
    /**
     * 吐司
     * @param s
     */
    protected void toast(String s){
        if(null==toast){
            toast=Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
        }
        toast.setText(s);
        toast.show();
    }
    @Override
    protected void onDestroy() {
        if(null!=tPresent) {
            tPresent.cancel();
            tPresent=null;
        }
        if(null!=toast){
            toast.cancel();
            toast=null;
        }
        super.onDestroy();
    }
    protected void showLoadingDialog() {
        if (myLoadingDialog == null) {
            myLoadingDialog = new HuiBanLoadingDialog(this);
        } else {
            myLoadingDialog.setContext(this);
        }
        if (myLoadingDialog != null && myLoadingDialog.isShowing()) {
            myLoadingDialog.dismiss();
        }
        if (!NetUtils.isNetworkAvalible(getApplication())) {
            return;
        }
        myLoadingDialog.show();
    }
    protected void dismissLoadingDialog() {
        if (myLoadingDialog != null && myLoadingDialog.isShowing()) {
            myLoadingDialog.dismiss();
        }
    }
}
