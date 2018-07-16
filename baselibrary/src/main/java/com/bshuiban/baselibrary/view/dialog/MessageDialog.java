package com.bshuiban.baselibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.DialogUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xinheng on 2018/5/7.<br/>
 * describe：消息弹窗
 */
public class MessageDialog {
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
    private final Context mContext;
    private Dialog dialog;

    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    @Retention(RetentionPolicy.SOURCE)
    //这里指定int的取值只能是以下范围
    @IntDef({TYPE_MESSAGE_SURE, TYPE_MESSAGE_SURE_CANCEL, TYPE_ONLY_MESSAGE})
    @interface MessageDialogType {
    }

    private int dp1;
    private View.OnClickListener onClickListenerSure;

    public MessageDialog(@NonNull Context context) {
        mContext=context;
        init();
    }

    private void init() {
        dp1 = (int) TypedValue.applyDimension(1, 1, mContext.getResources().getDisplayMetrics());
        dialog = new Dialog(mContext);
    }
    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
        dialog.setOnDismissListener(listener);
    }
    public MessageDialog setOnClickListenerSure(View.OnClickListener onClickListenerSure) {
        this.onClickListenerSure = onClickListenerSure;
        return this;
    }
    public void dismiss() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    public void show(){
        if(null!=dialog&&!dialog.isShowing()){
            dialog.show();
//            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//            layoutParams.gravity = Gravity.CENTER;
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
//            dialog.getWindow().setAttributes(layoutParams);
            //DialogUtils.setDialogWithMatcherScreen(dialog);
//            dialog.getWindow().getDecorView().setPadding(0,0,0,0);
        }
    }
    public MessageDialog setTypeMessage(String text, @MessageDialogType int type,String sure,String cancel) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(getContentViewId(text, type));
        TextView tv_sure = dialog.findViewById(R.id.tv_sure);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setText(cancel);
        tv_sure.setText(sure);
        return this;
    }
    public MessageDialog setTypeMessage(String text, @MessageDialogType int type) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(getContentViewId(text, type));
        //TextView tv_text = dialog.findViewById(R.id.tv_text);
        //tv_text.setMinHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mContext.getResources().getDimension(R.dimen.dp_40),mContext.getResources().getDisplayMetrics()));
        return this;
    }
    public MessageDialog setTypeMessage(String text, @MessageDialogType int type,int gravity) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(getContentViewId(text, type));
        return this;
    }
    private View getContentViewId(String text, int type) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_message_dialog, null);
        TextView tv_text = view.findViewById(R.id.tv_text);
        View line = view.findViewById(R.id.line);
        TextView tv_sure = view.findViewById(R.id.tv_sure);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_text.setText(text);
        switch (type) {
            case TYPE_ONLY_MESSAGE:
                tv_cancel.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                tv_sure.setVisibility(View.GONE);
                break;
            case TYPE_MESSAGE_SURE: {
                tv_sure.setOnClickListener(getSureClickListener());
                tv_cancel.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
            }
            break;
            default: {
                tv_sure.setOnClickListener(getSureClickListener());
                tv_cancel.setOnClickListener(getClickListener());
            }
        }
        return view;
    }
    private View getContentView(String text, int type) {
        LinearLayout llParent = new LinearLayout(mContext);
        llParent.setOrientation(LinearLayout.VERTICAL);
        llParent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView textView = new TextView(mContext);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setGravity(Gravity.CENTER);
        textView.setMinHeight((int) mContext.getResources().getDimension(R.dimen.dp_60));
        textView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.guide_start_btn));
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //textView.setBackgroundColor(Color.WHITE);
        llParent.addView(textView);
        switch (type) {
            case TYPE_ONLY_MESSAGE:
                break;
            case TYPE_MESSAGE_SURE: {
                View lineH = getViewH();
                llParent.addView(lineH);
                TextView textView1 = getTextView("确认");
                textView1.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red));
                textView1.setOnClickListener(getClickListener());
                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llParent.addView(textView1);
            }
            break;
            default: {
                View lineH = getViewH();
                llParent.addView(lineH);
                LinearLayout ll = new LinearLayout(mContext);
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
        return v -> {
            if(null!=onClickListenerSure){
                onClickListenerSure.onClick(v);
            }
            dialog.dismiss();
        };
    }
    private View.OnClickListener getClickListener() {
        return v -> dialog.cancel();
    }

    private View getViewH() {
        View lineH = new View(mContext);
        lineH.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp1));
        return lineH;
    }

    private View getViewV() {
        View lineH = new View(mContext);
        lineH.setLayoutParams(new LinearLayout.LayoutParams(dp1, 30));
        return lineH;
    }

    private TextView getTextView(String text) {
        TextView textView = new TextView(mContext);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(0,10,0,10);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.WHITE);
        return textView;
    }
}
