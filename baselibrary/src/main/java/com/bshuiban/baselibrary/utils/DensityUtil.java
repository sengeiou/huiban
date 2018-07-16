package com.bshuiban.baselibrary.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：dp，px,sp 转换
 */
public class DensityUtil {
    public static float dip2px(Context context,int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
