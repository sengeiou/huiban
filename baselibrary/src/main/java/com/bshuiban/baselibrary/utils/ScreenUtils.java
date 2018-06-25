package com.bshuiban.baselibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public class ScreenUtils {
    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
    public static int getScreenHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
