package com.bshuiban.teacher.view.webView.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.teacher.contract.MySpaceContract;
import com.bshuiban.teacher.present.MySpacePresent;

/**
 * 我的空间
 */
public class MySpaceWebActivity extends BaseWebActivity<MySpacePresent>implements MySpaceContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFileHtml("mySpace");
        tPresent=new MySpacePresent(this);
        registerWebViewH5Interface(new MySpaceHtml());

    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadSpaceHeadData();
        tPresent.loadMessageListInf();
    }

    @Override
    public void updateSpaceHeadView(String json) {
        loadJavascriptMethod("getMsg",json);
    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("",json);
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
    class MySpaceHtml extends MessageList{
        /**
         * 跳转页面
         * @param tag 0 主讲微课-查看全部；1 留言查看全部
         */
        @JavascriptInterface
        public void toNextPage(int tag){
            switch (tag){
                case 0:
                    toNextActivity(MainWeiClassWebActivity.class);
                    break;
                default:
            }
        }
    }
    private void toNextActivity(Class<?> cls){
        runOnUiThread(()->{
            startActivity(new Intent(getApplicationContext(),cls));
        });
    }
}
