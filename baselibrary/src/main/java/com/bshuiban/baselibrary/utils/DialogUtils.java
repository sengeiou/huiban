package com.bshuiban.baselibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bshuiban.baselibrary.view.dialog.MessageDialog;

/**
 * Created by xinheng on 2018/5/7.<br/>
 * describe：弹窗设置工具
 */
public class DialogUtils {
    /**
     * 设置弹窗宽充满屏幕
     * @param dialog
     */
    public static void setDialogWithMatcherScreen(Dialog dialog){
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
//        attributes.width=WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width=ScreenUtils.getScreenWidth(dialog.getContext());
        //attributes.height=ScreenUtils.getScreenHeight(dialog.getContext())/3;
        //attributes.height=200;
        window.setAttributes(attributes);
        window.getDecorView().setPadding(0,0,0,0);
    }
    /**
     * 设置弹窗宽充满屏幕
     * 设置弹出位置
     * @param dialog
     * @param gravity 弹出位置
     */
    public static void setDialogWithMatcherScreen(Dialog dialog,int gravity){
        setDialogWithMatcherScreen(dialog);
        dialog.getWindow().setGravity(gravity);
    }
    public static MessageDialog showMessageSureCancelDialog(Context context,String s, View.OnClickListener listener){
        MessageDialog messageDialog=new MessageDialog(context);
        messageDialog.setTypeMessage(s,MessageDialog.TYPE_MESSAGE_SURE_CANCEL);
        messageDialog.setCancelable(true);
        messageDialog.setOnClickListenerSure(listener);
        messageDialog.show();
        return messageDialog;
    }
}
