package com.bshuiban.teacher.view.webView.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.teacher.contract.SendNoticeContract;
import com.bshuiban.teacher.present.SendClassActivityPresent;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public class SendClassActivityWebActivity extends BaseWebActivity<SendClassActivityPresent> implements SendNoticeContract.View {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new SendClassActivityPresent(this);
        loadFileHtml("send");
        registerWebViewH5Interface(new SendNoticeHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadClassList();
    }

    @Override
    public void updateClassList(String msg) {
        loadJavascriptMethod("getClass",msg);
    }

    @Override
    public void sendNoticeSuccess() {
        toast("发送成功");
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
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
    class SendNoticeHtml{
        /**
         * 发送通知
         * @param classIds 班级id ，多个逗号隔开
         * @param content 通知内容
         */
        @JavascriptInterface
        public void sendNotice(String classIds,String content){
            tPresent.sendNotice(classIds,content);
        }
    }
}
