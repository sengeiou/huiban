package com.bshuiban.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bshuiban.baselibrary.utils.aes.AESUtils;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：用户信息密文存储（aes）
 */
public class UserSharedPreferencesUtils {
    /**
     * xml文件名
     */
    private static final String NAME="userData";
    /**
     * xml存储中的key值
     */
    private static final String keyName="resUserJson";

    /**
     * 保存
     * @param context 上下文
     * @param dataRes 密文
     * @return 成功与否
     */
    public static boolean saveUserData(Context context,String dataRes){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        dataRes=AESUtils.encrypt(dataRes);
        edit.putString(keyName,dataRes);
        return edit.commit();
    }

    /**
     * 获取
     * @param context 上下文
     * @return 内容
     */
    public static String getUserResJson(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(keyName, null);
        if(null!=string){
            string= AESUtils.desEncrypt(string);
        }
        return string;
    }

    /**
     * 清除
     * @param context 上下文
     * @return 成功与否
     */
    public static boolean cleanUserData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit().clear().commit();
    }
}
