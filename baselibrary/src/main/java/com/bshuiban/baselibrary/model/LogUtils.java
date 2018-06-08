package com.bshuiban.baselibrary.model;

import android.util.Log;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：
 */
public class LogUtils {
    public static final boolean mTag=true;
    public static final String TAG="TAG";

    public static void e(String tag,String msg){
        if(mTag) {
            Log.e(tag, msg);
        }
    }
}
