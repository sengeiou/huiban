package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.contract.HomeworkListContract;
import com.bshuiban.baselibrary.model.LogUtils;
import com.bshuiban.baselibrary.present.HomeworkListPresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：作业列表
 * <p>
 * HTML
 * 学科列表-complatelist
 * 待完成列表-nocomplate
 * 已完成列表-complately
 */
public class HomeworkListWebActivity extends BaseWebActivity<HomeworkListPresent> implements HomeworkListContract.View {
    //private static final String HTML_NAME="workIndex";
    public static final String HOME_TYPE = "home_type";
    public static final String HOME_PREPARE = "home_prepareId";
    public static final String HOME_Work_Id = "workId";
    public static final int Front_Class = 1;//1课前
    public static final int Middle_Class = 2;//2课中
    public static final int After_Class = 3;//3课后
    private int home_type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //1课前 2课中 3课后
        home_type = intent.getIntExtra(HOME_TYPE, Front_Class);
        boolean refresh = intent.getBooleanExtra("refresh", false);
        tPresent = new HomeworkListPresent(this);
        tPresent.setWtype(home_type);
        String HTML_NAME;
        switch (home_type) {
            case Front_Class:
                HTML_NAME = "workIndex";
                break;
            case Middle_Class:
                HTML_NAME = "classrecords";
                break;
            default:
                HTML_NAME = "workIndex";
        }
        loadFileHtml(HTML_NAME);
        HomeworkListHtml messageList = new HomeworkListHtml();
        messageList.setOnListener(new MessageList.OnMessageListListener() {
            @Override
            public void refresh() {
                tPresent.refresh();
            }

            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }
        });
        registerWebViewH5Interface(messageList);
        if (refresh) {
            if (!tPresent.getTAg()) {
                tPresent.setTag(0);
            } else {
                tPresent.refresh();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean refresh = intent.getBooleanExtra("refresh", false);
        LogUtils.e(LogUtils.TAG,"refresh="+refresh);
        if (refresh) {
            if (!tPresent.getTAg()) {
                tPresent.setTag(0);
            } else {
                tPresent.refresh();
            }
        }
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadSubjectList();
    }

    @Override
    public void updateList(String json) {
        if (tPresent.getTAg()||home_type==Middle_Class) {
            loadJavascriptMethod("complately", json);
        } else {
            loadJavascriptMethod("nocomplate", json);
        }
    }

    @Override
    public void updateSubjects(String json) {
        tPresent.setTag(0);
        loadJavascriptMethod("complatelist", json);
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

    class HomeworkListHtml extends MessageList {
        /**
         * 更改学科
         *
         * @param subjectId 0-->待完成
         */
        @JavascriptInterface
        public void changeSubjectId(int subjectId) {
            tPresent.setTag(subjectId);
        }

        /**
         * 当前列表属性
         *
         * @param tag false 待完成;true 已完成
         */
        @JavascriptInterface
        public void changeDataType(boolean tag) {
            Log.e(TAG, "changeDataType: " + tag);
            tPresent.setTag(tag);
        }

        @JavascriptInterface
        public void toNextPage(int workId, int prepareId) {
            //workId prepareId
            runOnUiThread(() -> {
                if (tPresent.getTAg()||home_type==Middle_Class) {//已完成
                    startActivity(new Intent(getApplicationContext(), HomeworkReportActivity.class).putExtra(HOME_Work_Id, workId).putExtra(HOME_PREPARE, prepareId));
                } else {//未完成
                    startActivity(new Intent(getApplicationContext(), HomeworkPendingInfActivity.class).putExtra(HOME_Work_Id, workId).putExtra(HOME_PREPARE, prepareId));
                }

            });
        }
    }
}
