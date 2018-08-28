package com.bshuiban.teacher.view.webView.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.TeachClassBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.TeachClassPresent;
import com.bshuiban.baselibrary.utils.observer.Observed;
import com.bshuiban.baselibrary.view.dialog.TeachClassDialog;
import com.bshuiban.baselibrary.view.webview.webActivity.LessonInfWebActivity;
import com.bshuiban.teacher.present.TeacherLessonInfPresent;

import java.util.List;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：老师课程详情
 */
public class TeacherLessonInfWebActivity extends LessonInfWebActivity<TeacherLessonInfPresent> {
    private TeachClassPresent teachClassPresent;
    private TeachClassDialog teachClassDialog;
    private boolean isSend;
    private Observed observed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSend = getIntent().getBooleanExtra("send", false);
        if(isSend){
            startDialog();
        }
    }

    @Override
    protected void webViewLoadFinished() {
        super.webViewLoadFinished();
        if(null==User.getInstance().getTeachClassData()) {
            teachClassPresent = new TeachClassPresent(this);
            teachClassPresent.loadTeachClass();
        }else {
            data=User.getInstance().getTeachClassData();
            if(isSend){
                startClassDialog();
            }
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
    public void updateData(List<TeachClassBean.DataBean> data) {
        super.updateData(data);
        if(isSend){
            startClassDialog();
        }
    }

    @Override
    public void collectSuccess(String tag) {
        super.collectSuccess(tag);
//        observed = new Observed();
//        ObserveModeGroupList.getInstance().subscribe(observed, SideCollectionListWebActivity.class);
//        observed.sendNotice("0".equals(tag),null);
    }

    @Override
    public void recommendSuccess() {

    }

    @Override
    protected void onDestroy() {
        if(null!=observed)
            observed.clear();
        super.onDestroy();
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
        dismissDialog();
        if(HomeworkBean.isEffictive(data)) {
            if (null == teachClassDialog) {
                teachClassDialog = new TeachClassDialog(this);
                teachClassDialog.setSendClickListener(v1 -> {
                    tPresent.loadRecommendParent(courseId, teachClassDialog.getSelectClass());
                    teachClassDialog.dismiss();
                });
            }
            teachClassDialog.setTexts(data);
            teachClassDialog.show();
        }else {
            toast("暂无所属班级");
        }
    }
}
