package com.bshuiban.baselibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.utils.DialogUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xinheng on 2018/5/7.<br/>
 * describe：消息弹窗
 */
public class MessageDialog extends Dialog {
    /**
     * 仅仅显示消息内容
     */
    public static final int TYPE_ONLY_MESSAGE = 0;
    /**
     * 显示消息与确认按钮
     */
    public static final int TYPE_MESSAGE_SURE = 1;
    /**
     * 显示消息、确认和取消按钮
     */
    public static final int TYPE_MESSAGE_SURE_CANCEL = 2;

    @Retention(RetentionPolicy.SOURCE)
    //这里指定int的取值只能是以下范围
    @IntDef({TYPE_MESSAGE_SURE, TYPE_MESSAGE_SURE_CANCEL, TYPE_ONLY_MESSAGE})
    @interface MessageDialogType {
    }

    private int dp1;
    private View.OnClickListener onClickListenerSure;

    public MessageDialog(@NonNull Context context) {
        super(context);
        init();
    }

    protected MessageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }

    private void init() {
        dp1 = (int) TypedValue.applyDimension(1, 1, getContext().getResources().getDisplayMetrics());
    }

    public MessageDialog setOnClickListenerSure(View.OnClickListener onClickListenerSure) {
        this.onClickListenerSure = onClickListenerSure;
        return this;
    }

    public MessageDialog setTypeMessage(String text, @MessageDialogType int type,int gravity) {
        getWindow().setContentView(getContentView(text, type));
        DialogUtils.setDialogWithMatcherScreen(this,gravity);
        return this;
    }

    private View getContentView(String text, int type) {
        LinearLayout llParent = new LinearLayout(getContext());
        llParent.setOrientation(LinearLayout.VERTICAL);
        llParent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setBackgroundColor(Color.WHITE);
        llParent.addView(textView);
        switch (type) {
            case TYPE_ONLY_MESSAGE:
                break;
            case TYPE_MESSAGE_SURE: {
                View lineH = getViewH();
                llParent.addView(lineH);
                TextView textView1 = getTextView("确认");
                textView1.setOnClickListener(getClickListener());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llParent.addView(textView1);
            }
            break;
            default: {
                View lineH = getViewH();
                llParent.addView(lineH);
                LinearLayout ll = new LinearLayout(getContext());
                ll.setOrientation(LinearLayout.HORIZONTAL);
                TextView textView1 = getTextView("确认");
                textView1.setOnClickListener(getSureClickListener());
                textView1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                TextView textView2 = getTextView("取消");
                textView2.setOnClickListener(getClickListener());
                textView2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                ll.addView(textView1);
                ll.addView(getViewV());
                ll.addView(textView2);
                llParent.addView(ll);
            }
        }
        return llParent;
    }
    private View.OnClickListener getSureClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=onClickListenerSure){
                    onClickListenerSure.onClick(v);
                }
                dismiss();
            }
        };
    }
    private View.OnClickListener getClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        };
    }

    private View getViewH() {
        View lineH = new View(getContext());
        lineH.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp1));
        return lineH;
    }

    private View getViewV() {
        View lineH = new View(getContext());
        lineH.setLayoutParams(new LinearLayout.LayoutParams(dp1, 30));
        return lineH;
    }

    private TextView getTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.WHITE);
        return textView;
    }
}
