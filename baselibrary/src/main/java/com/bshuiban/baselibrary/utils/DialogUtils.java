package com.bshuiban.baselibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StringDef;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bshuiban.baselibrary.view.dialog.MessageDialog;
import com.bumptech.glide.Glide;

/**
 * Created by xinheng on 2018/5/7.<br/>
 * describe：弹窗设置工具
 */
public class DialogUtils {
    /**
     * 设置弹窗宽充满屏幕
     *
     * @param dialog
     */
    public static void setDialogWithMatcherScreen(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
//        attributes.width=WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = ScreenUtils.getScreenWidth(dialog.getContext());
        attributes.height = ScreenUtils.getScreenHeight(dialog.getContext()) / 3;
        //attributes.height=200;
        window.setAttributes(attributes);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 设置弹窗宽充满屏幕
     * 设置弹出位置
     *
     * @param dialog
     * @param gravity 弹出位置
     */
    public static void setDialogWithMatcherScreen(Dialog dialog, int gravity) {
        setDialogWithMatcherScreen(dialog);
        dialog.getWindow().setGravity(gravity);
    }

    /**
     * 提示消息
     *
     * @param context  上下文
     * @param s        弹窗内容
     * @param listener 确认点击回调
     * @return dialog对象
     */
    public static MessageDialog showMessageSureCancelDialog(Context context, String s, View.OnClickListener listener) {
        MessageDialog messageDialog = new MessageDialog(context);
        messageDialog.setTypeMessage(s, MessageDialog.TYPE_MESSAGE_SURE_CANCEL);
        messageDialog.setCancelable(true);
        messageDialog.setOnClickListenerSure(listener);
        messageDialog.show();
        return messageDialog;
    }

    /**
     * 提示消息
     *
     * @param context  上下文
     * @param s        弹窗内容
     * @param sure     确认按钮内容
     * @param cancel   取消按钮内容
     * @param listener 确认点击回调
     * @return dialog对象
     */
    public static MessageDialog showMessageSureCancelDialog(Context context, String s, String sure, String cancel, View.OnClickListener listener) {
        MessageDialog messageDialog = new MessageDialog(context);
        messageDialog.setTypeMessage(s, MessageDialog.TYPE_MESSAGE_SURE_CANCEL, sure, cancel);
        messageDialog.setCancelable(true);
        messageDialog.setOnClickListenerSure(listener);
        messageDialog.show();
        return messageDialog;
    }

    public static Dialog showImageDialog(Context context, String src) {
        Dialog dialog = new Dialog(context);
        ImageView view = new ImageView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Glide.with(context).load(src).into(view);
        dialog.show();
        dialog.setCancelable(true);
        setDialogWithMatcherScreen(dialog);
        return dialog;
    }
    //public static Dialog show
}
