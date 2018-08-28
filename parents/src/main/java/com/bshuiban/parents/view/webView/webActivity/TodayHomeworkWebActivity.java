package com.bshuiban.parents.view.webView.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.UserTypeHtml;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.parents.contract.TodayHomeworkContract;
import com.bshuiban.parents.present.TodayHomeworkPresent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：家长端 今日作业
 */
public class TodayHomeworkWebActivity extends BaseWebActivity<TodayHomeworkPresent> implements TodayHomeworkContract.View {
    private int nowWtype=1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new TodayHomeworkPresent(this);
        loadFileHtml("parentsNotice");
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadTodayHomework();
    }

    @Override
    public void updateListView(int type, String json) {
        nowWtype=type;
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
    class TodayHomeworkHtml extends UserTypeHtml{
        /**
         * 提醒
         * @param workId
         */
        @JavascriptInterface
        public void addWorkRemind(int workId){
            //tPresent.addWorkRemind(workId);
        }

    }
}
