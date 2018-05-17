package com.bshuiban.baselibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by xinheng on 2018/4/26.<br/>
 * describe：
 */
public class ViewUtils {
    public static StateListDrawable getSeleDrawable(Context context,int selectedId, int unSelectedId) {
        Drawable selected = context.getResources().getDrawable(selectedId);
        Drawable unSelected = context.getResources().getDrawable(unSelectedId);
        return getSeleDrawable(selected,unSelected);
    }
    public static StateListDrawable getSeleDrawable(Drawable selected, Drawable unSelected) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed},selected);
        drawable.addState(new int[]{-android.R.attr.state_pressed},unSelected);
        return drawable;
    }
    public static FrameLayout getFrameLayout(Context context){
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return frameLayout;
    }
}
