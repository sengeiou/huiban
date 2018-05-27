package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.ErrorFilterContract;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.ErrorFilterPresent;

public class ErrorFilterActivity extends BaseWebActivity<ErrorFilterPresent> implements ErrorFilterContract.View {

    private boolean tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new ErrorFilterPresent(this);
        loadFileHtml("moreWrong");
        registerWebViewH5Interface(new ErrorFilterHtml());
    }

    @Override
    protected void webViewLoadFinished() {//首次 章节-科目 {"userId":"","schoolId":""}
        tag = true;
        tPresent.loadFilter("getFilterSubjectL", "{\"schoolId\":\"" + User.getInstance().getSchoolId() + "\"}");
    }

    @Override
    public void updateView(String json) {
        if (tag) {
            loadJavascriptMethod("subject", json);
        } else {
            loadJavascriptMethod("getKnows", json);
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

    }

    class ErrorFilterHtml {
        @JavascriptInterface
        public void dealWithJson(String key, String json) {
            tPresent.loadFilter(key, json);
        }

        /**
         * 当前数据
         *
         * @param tag true 章节；false 知识点
         */
        @JavascriptInterface
        public void setStatue(boolean tag) {
            ErrorFilterActivity.this.tag = tag;
        }

        /**
         * @param mSubjectId
         * @param mVersionId
         * @param mFasId
         * @param mChapBranId
         * @param mSeriBrandId
         */
        @JavascriptInterface
        public void loadFilterData(int mSubjectId,     //int, 科目id
                                   int mVersionId, //int，版本id
                                   int mFasId,        //int，分册id
                                   int mChapBranId,  //int，章节id
                                   int mSeriBrandId   //int，知识点id
        ) {
            runOnUiThread(() -> {
                Intent i = new Intent();
                i.putExtra("mSubjectId", mSubjectId);
                i.putExtra("mVersionId", mVersionId);
                i.putExtra("mFasId", mFasId);
                i.putExtra("mChapBranId", mChapBranId);
                i.putExtra("mSeriBrandId", mSeriBrandId);
                setResult(RESULT_OK, i);
                finish();
            });
        }
    }
}
