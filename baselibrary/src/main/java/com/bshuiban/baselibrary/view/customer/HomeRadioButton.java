package com.bshuiban.baselibrary.view.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.bshuiban.baselibrary.R;

/**
 * Created by xinheng on 2018/4/26.<br/>
 * describe：首页底部按钮
 */
@SuppressLint("AppCompatCustomView")
public class HomeRadioButton extends RadioButton {
    private Drawable drawable;
    public HomeRadioButton(Context context) {
        super(context);
    }

    public HomeRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
