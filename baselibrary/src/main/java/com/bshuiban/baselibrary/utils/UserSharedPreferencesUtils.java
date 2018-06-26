package com.bshuiban.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bshuiban.baselibrary.utils.aes.AESUtils;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：
 */
public class UserSharedPreferencesUtils {
    private static final String NAME="userData";
    private static final String keyName="resUserJson";
    public static boolean saveUserData(Context context,String dataRes){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        dataRes=AESUtils.encrypt(dataRes);
        edit.putString(keyName,dataRes);
        return edit.commit();
    }
    public static String getUserResJson(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(keyName, null);
        if(null!=string){
            string= AESUtils.desEncrypt(string);
        }
        return string;
    }
    public static boolean cleanUserData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit().clear().commit();
    }
}
