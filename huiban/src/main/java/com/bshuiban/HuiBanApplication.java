package com.bshuiban;

import android.app.Application;

import com.bshuiban.baselibrary.model.LoginResultBean;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：
 */
public class HuiBanApplication extends Application {
    private LoginResultBean.Data data;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public String getUserId(){
        return data.getUserId();
    }
    public String getClassId(){
        return data.getClassId1();
    }
    public LoginResultBean.Data getUserData(){
        return data;
    }
}
