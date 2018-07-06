package com.bshuiban.baselibrary.view.customer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xinheng on 2018/7/4.<br/>
 * describe：缩放的TextView
 */
@SuppressLint("AppCompatCustomView")
public class ScaleTextView extends TextView {
//    private static final int STATUE_NO_WORK=0;//未做答
//    private static final int STATUE_HAD_WORK=1;//已答
    private boolean scallTag;//是否放大，是否选中
//    @Retention(RetentionPolicy.SOURCE)
//    @IntDef({STATUE_NO_WORK, STATUE_HAD_WORK})
//    @interface ScaleTextViewStatue {
//    }
    public ScaleTextView(Context context) {
        super(context);
    }

    public ScaleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScallTag(boolean scallTag) {
        Log.e("TAG", "setScallTag: "+this.scallTag+", "+scallTag +", "+getTag());
        if(scallTag!=this.scallTag) {
            if(scallTag){
                scaleUp();
            }else {
                scaleDown();
            }
        }
        //this.scallTag=scallTag;
    }
    //1.08表示放大倍数,可以随便改
    private void scaleUp() {
        scallTag=true;
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1, 1.3f).setDuration(200);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1, 1.3f).setDuration(200);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.start();
    }

    private void scaleDown() {
        scallTag=false;
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.3f, 1).setDuration(200);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.3f, 1).setDuration(200);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.start();
    }
}
