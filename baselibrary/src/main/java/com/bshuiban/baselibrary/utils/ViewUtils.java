package com.bshuiban.baselibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.StudyBottomBean;

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
    public static WebView getWebView(Context context){
        WebView webView = new WebView(context);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        return webView;
    }
    /**
     * 设置底部学习页面
     * @param view
     * @param data
     */
    public static void setViewData(View view, StudyBottomBean.DataBean data){
        TextView tv_video=view.findViewById(R.id.textView2);
        TextView tv_time=view.findViewById(R.id.textView12);
        TextView tv_count=view.findViewById(R.id.textView22);
        TextView tv_complete=view.findViewById(R.id.textView32);
        if(null!=data) {
            setText(tv_video, data.getVideoCnt());
            setText(tv_time, data.getLength());
            setText(tv_count, data.getAnswerCnt());
            setText(tv_complete, data.getExamCnt());
        }else {
            setText(tv_video, null);
            setText(tv_time, null);
            setText(tv_count,null);
            setText(tv_complete, null);
        }

    }
    public static void setText(TextView tv,String s){
        tv.setText(TextUtils.cleanNull(s));
    }

}
