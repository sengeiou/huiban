package com.bshuiban.baselibrary.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.SystemStatusManager;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：Activity 基本配置：状态栏一体化
 */
public class BaseActivity<T extends BasePresent> extends AppCompatActivity {
    protected T tPresent;
    private Toast toast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SystemStatusManager(this).setTranslucentStatus(R.color.guide_start_btn);
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
}
