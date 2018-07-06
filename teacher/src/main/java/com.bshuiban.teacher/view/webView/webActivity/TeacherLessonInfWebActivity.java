package com.bshuiban.teacher.view.webView.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.TeachClassPresent;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.view.webview.webActivity.LessonInfWebActivity;
import com.bshuiban.teacher.present.TeacherLessonInfPresent;
import com.xinheng.date_dialog.dialog.SeleTwoDialog;

import java.util.List;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：老师课程详情
 */
public class TeacherLessonInfWebActivity extends LessonInfWebActivity<TeacherLessonInfPresent> {
    private TeachClassPresent teachClassPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void webViewLoadFinished() {
        super.webViewLoadFinished();
        if(null==User.getInstance().getTeachClassData()) {
            teachClassPresent = new TeachClassPresent(this);
            teachClassPresent.loadTeachClass();
        }else {
            data=User.getInstance().getTeachClassData();
        }
    }

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
            if(User.getInstance().isTeacher()) {
                //tPresent.loadRecommendParent(courseId, null);
                runOnUiThread(()->startClassDialog());
            }
        }
    }
    private void startClassDialog(){
        if(HomeworkBean.isEffictive(data)) {
            List<String> listString = TextUtils.getListString(data);
            SeleTwoDialog twoDialog = new SeleTwoDialog(this, new SeleTwoDialog.OnClickListener() {
                @Override
                public boolean onSure(String left, int leftIndex, String right, int rightIndex) {
                    tPresent.loadRecommendParent(courseId, data.get(leftIndex).getClassId());
                    return false;
                }

                @Override
                public boolean onCancel() {
                    return false;
                }
            }, listString, null);
            twoDialog.show();
        }else {
            toast("暂无所属班级");
        }
    }
}
