package com.bshuiban.baselibrary.utils;

import android.content.Context;

import com.bshuiban.baselibrary.view.dialog.HuiBanLoadingDialog;

/**
 * Created by xinheng on 2018/6/12.<br/>
 * describe：
 */
public class DialogFactory {
    /**
     * 慧班通用等待弹窗
     * @param context
     */
    public static void creatHuiBanDialog(Context context) {
        HuiBanLoadingDialog myLoadingDialog = new HuiBanLoadingDialog(context);
        if (!NetUtils.isNetworkAvalible(context.getApplicationContext())) {
            return;
        }
        myLoadingDialog.show();
    }
}
