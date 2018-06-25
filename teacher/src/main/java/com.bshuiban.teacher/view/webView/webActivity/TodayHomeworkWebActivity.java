package com.bshuiban.teacher.view.webView.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.teacher.contract.TodayHomeworkContract;
import com.bshuiban.teacher.present.TodayHomeworkPresent;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：今日作业
 */
public class TodayHomeworkWebActivity extends BaseWebActivity<TodayHomeworkPresent> implements TodayHomeworkContract.View {
    private int nowWtype=1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new TodayHomeworkPresent(this);
        loadFileHtml("workNotice");
        registerWebViewH5Interface(new TodayHomeworkHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadTodayHomework(nowWtype);
    }

    @Override
    public void updateListView(int type, String json) {
        String name="worknotice";
        loadJavascriptMethod(name,json);
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
    class TodayHomeworkHtml{
        /**
         * 提醒
         * @param workId
         */
        @JavascriptInterface
        public void addWorkRemind(int workId){
            tPresent.addWorkRemind(workId);
        }

        /**
         *
         * @param type 1,3
         */
        @JavascriptInterface
        public void changeItem(int type){
            if(type!=nowWtype) {
                tPresent.loadTodayHomework(type);
                nowWtype=type;
            }
        }

    }
}
