package com.bshuiban.baselibrary.utils;

import android.app.Dialog;
import android.view.Window;
import android.view.WindowManager;

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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width=WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
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
}
