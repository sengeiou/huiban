package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.HomeworkResultContract;
import com.bshuiban.baselibrary.model.ExamAnswerBean;
import com.bshuiban.baselibrary.model.HomeWorkCommitBean;
import com.bshuiban.baselibrary.model.Homework;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.PrepareVideoAnswerBean;
import com.bshuiban.baselibrary.model.PrepareWorkAnswerBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.model.VideoAnswerBean;
import com.bshuiban.baselibrary.present.HomeworkResultPresent;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_PREPARE;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：作业结果
 */
public class HomeworkResultWebActivity extends BaseWebActivity<HomeworkResultPresent> implements HomeworkResultContract.View {
    private Gson gson = new Gson();
    private int workId, prepareId;
    private boolean complete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        workId = intent.getIntExtra(HomeworkListWebActivity.HOME_Work_Id, -1);
        prepareId = intent.getIntExtra(HOME_PREPARE, -1);
        complete = intent.getBooleanExtra("complete", true);
        tPresent = new HomeworkResultPresent(this);
        boolean complete = intent.getBooleanExtra("complete", true);
        if (complete) {
            loadFileHtml("list");
        } else {
            loadFileHtml("submit");
        }
        registerWebViewH5Interface(new HomeworkResultHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        Homework homework = User.getInstance().getHomework();
        String json = gson.toJson(homework);
        loadJavascriptMethod("answer", json);
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
    public void commitSuccess() {
        loadJavascriptMethod("judge");
    }

    class HomeworkResultHtml {
        /**
         * 提交、保存作业
         *
         * @param tag true 提交，false 保存
         */
        @JavascriptInterface
        public void commitHomework(boolean tag) {
            if (tag) {
                tPresent.commitService(prepareId, workId);
            } else {
                tPresent.saveHomework(prepareId, workId);
            }
        }

        /**
         * 返回作业列表
         */
        @JavascriptInterface
        public void backHomeworkInf() {
            startActivity(new Intent(getApplicationContext(), HomeworkListWebActivity.class).putExtra("refresh", true));
        }
    }

}
