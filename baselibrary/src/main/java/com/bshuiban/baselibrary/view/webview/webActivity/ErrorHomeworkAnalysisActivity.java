package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.contract.ErrorHomeworkAnalysisContract;
import com.bshuiban.baselibrary.present.ErrorHomeworkAnalysisPresent;
import com.bshuiban.baselibrary.view.activity.PlayerVideoActivity;
import com.bshuiban.baselibrary.view.activity.VideoPlayerActivity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class ErrorHomeworkAnalysisActivity extends BaseWebActivity<ErrorHomeworkAnalysisPresent> implements ErrorHomeworkAnalysisContract.View {
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = getIntent().getStringExtra("text");
        tPresent = new ErrorHomeworkAnalysisPresent(this);
        loadFileHtml("wrongMsg");
        registerWebViewH5Interface(new ErrorHomeworkAnalysisHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        loadJavascriptMethod("getContent", text);
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
    public void deleteErrorHomeworkSuccess() {
        finish();
    }

    class ErrorHomeworkAnalysisHtml {
        /**
         * 观看视频
         *
         * @param videoUrl
         */
        @JavascriptInterface
        public void playVideo(String videoUrl) {
            runOnUiThread(() -> {
                PlayerVideoActivity.startPlayerVideoActivity(getApplicationContext(),videoUrl);
            });
        }

        /**
         * 巩固练习
         *
         * @param examId 试题id
         */
        @JavascriptInterface
        public void consolidation(int examId) {
            Log.e(TAG, "consolidation: " + examId);
            runOnUiThread(() -> {
                startActivity(new Intent(getApplicationContext(), ConsolidationWebActivity.class).putExtra("examId", examId));
            });
        }

        /**
         * 删除错题
         *
         * @param examId
         */
        @JavascriptInterface
        public void deleteErrorHomework(int examId) {
            tPresent.deleteErrorHomework(examId);
        }

    }
}
