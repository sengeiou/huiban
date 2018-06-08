package com.bshuiban.teacher.view.webView.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.teacher.contract.MiddleReportInfContract;
import com.bshuiban.teacher.present.MiddleReportInfPresent;
import com.bshuiban.teacher.view.activity.HomeworkAnswerInfActivity;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_TYPE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

/**
 * Created by xinheng on 2018/6/4.<br/>
 * describe：课中报告信息
 */
public class MiddleReportInfWebActivity extends BaseWebActivity<MiddleReportInfPresent> implements MiddleReportInfContract.View {

    private int classId;
    private String preId;
    private int wtype;
    private int workId;
    private String className;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        classId = intent.getIntExtra("classId", -1);
        preId = intent.getStringExtra("preparationId");
        wtype = intent.getIntExtra("process", -1);
        workId = intent.getIntExtra("workId", -1);
        className = intent.getStringExtra("className");
        tPresent = new MiddleReportInfPresent(this);
        loadFileHtml("knowsMsg");
        registerWebViewH5Interface(new MiddleReportInfHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadHBTeaPreKnowRate(preId, wtype, workId, classId);
        tPresent.loadHBTeaPreExamRate(preId, wtype, workId, classId);
    }

    @Override
    public void updateView(int type, String json) {
        switch (type) {
            case 1:
                loadJavascriptMethod("getData",json);
                break;
            default:
                loadJavascriptMethod("getMsg",json);
        }
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

    class MiddleReportInfHtml {
//        @JavascriptInterface
//        public void changeItem(int type) {
//            if (type == 1) {
//            }
//        }

        @JavascriptInterface
        public void toNextPage() {
            runOnUiThread(()->{
                Intent intent = new Intent(getApplicationContext(), HomeworkAnswerInfActivity.class);
                intent.putExtra("preId", preId);
                intent.putExtra(HOME_Work_Id, workId);
                intent.putExtra(HOME_TYPE, wtype);
                intent.putExtra("classId",classId);
                startActivity(intent);
            });
        }
    }
}
