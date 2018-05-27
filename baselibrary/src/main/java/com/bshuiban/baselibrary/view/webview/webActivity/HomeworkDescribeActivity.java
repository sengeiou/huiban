package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;

import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.User;

/**
 * 作业描述
 *
 * HTML
 * 作业面熟文本-describe
 */
public class HomeworkDescribeActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFileHtml("descripe");
    }

    @Override
    protected void webViewLoadFinished() {
        HomeworkInfBean.DataBean homeworkInfBean = User.getInstance().getHomeworkInfBean();
        String desc = homeworkInfBean.getDesc();
        loadJavascriptMethod("describe",desc);
    }
}
