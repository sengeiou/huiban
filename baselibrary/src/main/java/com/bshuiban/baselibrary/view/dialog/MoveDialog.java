package com.bshuiban.baselibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.DensityUtil;
import com.bshuiban.baselibrary.utils.ScreenUtils;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public class MoveDialog extends Dialog{
    float startY;
    float moveY = 0;
    private View view;

    public MoveDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        window.setWindowAnimations(R.style.bottomShow);
        WindowManager.LayoutParams windowparams = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        windowparams.height = (int) DensityUtil.dip2px(getContext(), 300);
        windowparams.width = ScreenUtils.getScreenWidth(getContext());
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(windowparams);
    }

    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
        this.view=view;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = ev.getY() - startY;
                view.scrollBy(0, -(int) moveY);
                startY = ev.getY();
                if (view.getScrollY() > 0) {
                    view.scrollTo(0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (view.getScrollY() < -this.getWindow().getAttributes().height / 4 && moveY > 0) {
                    this.dismiss();

                }
                view.scrollTo(0, 0);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
