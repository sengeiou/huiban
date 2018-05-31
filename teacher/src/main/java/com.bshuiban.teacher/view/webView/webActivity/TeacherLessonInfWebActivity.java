package com.bshuiban.teacher.view.webView.webActivity;

import android.webkit.JavascriptInterface;
import com.bshuiban.baselibrary.view.webview.webActivity.LessonInfWebActivity;
import com.bshuiban.teacher.present.TeacherLessonInfPresent;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：老师课程详情
 */
public class TeacherLessonInfWebActivity extends LessonInfWebActivity<TeacherLessonInfPresent> {
    @Override
    protected String getHtmlFileName() {
        return "details";
    }

    @Override
    protected Object getRegisterWebViewH5Class() {
        return new TeacherLessonInfHtml();
    }

    @Override
    protected TeacherLessonInfPresent getPresent() {
        return new TeacherLessonInfPresent(this);
    }

    @Override
    public void recommendSuccess() {

    }

    class TeacherLessonInfHtml extends LessonInfHtml{
        /**
         * 推荐给家长
         */
        @JavascriptInterface
        public void recommendParent(){//弹窗 courseId
            //tPresent.loadRecommendParent(courseId,null);
        }
    }
}
