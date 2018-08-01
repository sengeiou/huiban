package com.bshuiban.teacher.view.webView.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.activity.PlayerVideoActivity;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.teacher.contract.MainWeiClassContract;
import com.bshuiban.teacher.present.MainWeiClassPresent;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：主讲微课
 */
public class MainWeiClassWebActivity extends BaseWebActivity<MainWeiClassPresent> implements MainWeiClassContract.View{
    private String userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getStringExtra("userId");
        if(android.text.TextUtils.isEmpty(userId)){
            userId = User.getInstance().getUserId();
        }
        tPresent=new MainWeiClassPresent(this,userId);
        loadFileHtml("mainClassList");
        MainWeiClassHtml object = new MainWeiClassHtml();
        object.setOnListener(new MessageList.OnMessageListListener(){
            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }

            @Override
            public void refresh() {
                tPresent.refresh();
            }
        });
        registerWebViewH5Interface(object);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadWeiClassData();
    }

    @Override
    public void toNextPage(Class<?> cls) {

    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("getContent",json);
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
    class MainWeiClassHtml extends MessageList{
        /**
         * 转码中
         * @param s
         */
        @JavascriptInterface
        public void toast(String s){
            runOnUiThread(()->MainWeiClassWebActivity.this.toast(s));
        }

        /**
         * 非转码，视频地址有效
         * @param url
         */
        @JavascriptInterface
        public void playVideo(String url){
            runOnUiThread(()-> PlayerVideoActivity.startPlayerVideoActivity(MainWeiClassWebActivity.this,url));
        }
    }
}
