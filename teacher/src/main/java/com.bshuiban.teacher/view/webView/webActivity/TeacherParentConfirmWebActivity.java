package com.bshuiban.teacher.view.webView.webActivity;

import com.bshuiban.baselibrary.view.webview.webActivity.ParentConfirmWebActivity;
import com.bshuiban.teacher.present.ParentConfirmPresent;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：老师端 家长确认列表
 */
public class TeacherParentConfirmWebActivity extends ParentConfirmWebActivity<ParentConfirmPresent> {
    @Override
    protected ParentConfirmPresent getPresent() {
        return new ParentConfirmPresent(this);
    }

    @Override
    protected Class<?> getActivityClass() {
        return TeacherParentConfirmSubjectWebActivity.class;
    }
}
