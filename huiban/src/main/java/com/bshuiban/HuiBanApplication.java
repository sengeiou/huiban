package com.bshuiban;

import android.app.Application;

import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.utils.CrashHandler;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：application
 */
public class HuiBanApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(!BuildConfig.LOG_DEBUG) {
            CrashHandler.getInstance().init(this);
        }
    }
}
