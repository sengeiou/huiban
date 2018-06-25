package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.bshuiban.baselibrary.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by xinheng on 2018/6/20.<br/>
 * describe：修改一些点击事件
 */
public class FullJCVideoPlayer extends JCVideoPlayerStandard {
    public FullJCVideoPlayer(Context context) {
        super(context);
    }

    public FullJCVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        fullscreenButton.setVisibility(GONE);
        titleTextView.setPadding(10,10,10,10);
        titleTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.back) {
             if(null!=onBackListener){
                 onBackListener.back();
             }
        }else if(i==R.id.title){
            backPress();
            if(null!=onBackListener){
                onBackListener.back();
            }
        }
    }
    private OnBackListener onBackListener;

    public void setOnBackListener(OnBackListener onBackListener) {
        this.onBackListener = onBackListener;
    }

    public interface OnBackListener{
        void back();
    }
}
