package com.bshuiban.teacher.view.webView.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.TeachClassContract;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.SubjectBean;
import com.bshuiban.baselibrary.model.TeachClassBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.TeachClassPresent;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.view.dialog.TeachClassDialog;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.baselibrary.view.webview.webActivity.LessonInfWebActivity;
import com.bshuiban.teacher.contract.LessonListContract;
import com.bshuiban.teacher.present.LessonListPresent;
import com.google.gson.Gson;
import com.xinheng.date_dialog.dialog.SeleTwoDialog;

import java.util.List;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：课程列表
 */
public class LessonListActivity extends BaseWebActivity<LessonListPresent> implements LessonListContract.View, TeachClassContract.View {
    private List<TeachClassBean.DataBean> data;
    private String mJsonSubject;
    private TeachClassDialog teachClassDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new LessonListPresent(this);
        if (User.getInstance().getTeachClassData() == null)
            new TeachClassPresent(this).loadTeachClass();
        else
            data = User.getInstance().getTeachClassData();
        loadFileHtml("stuResource");
        LessonListHtml object = new LessonListHtml();
        object.setOnListener(new MessageList.OnMessageListListener() {
            @Override
            public void refresh() {
                tPresent.refresh();
            }

            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }
        });
        registerWebViewH5Interface(object);
    }

    @Override
    protected void webViewLoadFinished() {
        //tPresent.loadLessonListData(null);
        tPresent.loadAllSubject();
    }

    @Override
    public void recommendParent() {
        //to next page
    }

    @Override
    public void loadAllSubject(SubjectBean dataBean) {
        if (null != dataBean && null != dataBean.getData())
            mJsonSubject = new Gson().toJson(dataBean.getData());
    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("getContent", json);
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        toast(error);
    }

    @Override
    public void updateData(List<TeachClassBean.DataBean> data) {
        this.data = data;
        User.getInstance().setTeachClassData(data);
    }

    private void startClassDialog(String courseId) {
        if (HomeworkBean.isEffictive(data)) {
            if (null == teachClassDialog) {
                teachClassDialog = new TeachClassDialog(this);
                teachClassDialog.setSendClickListener(v1 -> {
                    tPresent.loadRecommendParent(courseId, teachClassDialog.getSelectClass());
                    teachClassDialog.dismiss();
                });
            }
            teachClassDialog.setTexts(data);
            teachClassDialog.show();
        } else {
            toast("暂无所属班级");
        }
    }

    class LessonListHtml extends MessageList {
        /**
         * 推荐给家长
         *
         * @param courseId 教学资源标识
         */
        @JavascriptInterface
        public void recommendParent(String courseId) {
            //tPresent.loadRecommendParent(courseId);
            runOnUiThread(() -> {
                /*if (null != data) {
                    startClassDialog(courseId);
                } else {
                    toast("暂无班级");
                }*/
                startActivity(new Intent(getApplicationContext(), TeacherLessonInfWebActivity.class).putExtra("courseId", courseId).putExtra("send",true));

            });
        }

        /**
         * 列表每个小单元 点击
         *
         * @param courseId
         */
        @JavascriptInterface
        public void itemClick(String courseId) {
            runOnUiThread(() -> {
                startActivity(new Intent(getApplicationContext(), TeacherLessonInfWebActivity.class).putExtra("courseId", courseId));
            });
        }

        @JavascriptInterface
        public void dealWithJson(String search) {
            tPresent.loadSearchLessonListData(search);
        }

        @JavascriptInterface
        public void toSearchPage() {
            runOnUiThread(() -> {
                Intent intent = new Intent(getApplicationContext(), FilterConditionActivity.class);
                intent.putExtra("json", mJsonSubject);
                startActivityForResult(intent, 100);
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100:
                if (data != null) {
                    String json = data.getStringExtra("text");
                    tPresent.loadLessonListData(json);
                }
                break;
        }
    }
}
