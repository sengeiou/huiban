package com.bshuiban.teacher.view.webView.webActivity;

import com.bshuiban.baselibrary.view.webview.webActivity.ParentConfirmSubjectWebActivity;
import com.bshuiban.teacher.present.ParentConfirmSubjectPresent;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public class TeacherParentConfirmSubjectWebActivity extends ParentConfirmSubjectWebActivity<ParentConfirmSubjectPresent> {
    @Override
    protected ParentConfirmSubjectPresent getPresent() {
        return new ParentConfirmSubjectPresent(this);
    }
}
