package com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass;

import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/6/6.<br/>
 * describe：
 */
public class UserTypeHtml {
    @JavascriptInterface
    public int getUserType(){
        return User.getInstance().getUserType();
    }
}
