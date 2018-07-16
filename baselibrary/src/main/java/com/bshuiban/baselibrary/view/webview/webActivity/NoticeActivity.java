package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.contract.NoticeContract;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.NoticePresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;

/**
 * Created by xinheng on 2018/5/23.<br/>
 * describe：
 */
public class NoticeActivity extends BaseWebActivity<NoticePresent> implements NoticeContract.View{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        boolean send = intent.getBooleanExtra("send", false);
        if(!send) {
            loadFileHtml("notic");
        }else{
            loadFileHtml("notic_teacher");
        }
        NoticeHtml messageList=new NoticeHtml();
        messageList.setOnListener(new MessageList.OnMessageListListener(){
            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }

            @Override
            public void refresh() {
                tPresent.refresh();
            }
        });
        registerWebViewH5Interface(messageList);
        tPresent=new NoticePresent(this);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.getInterNetData();
    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("getContent",(json));
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
    class NoticeHtml extends MessageList{
        @JavascriptInterface
        public void send(){
            try {
                Class<?> aClass = Class.forName("com.bshuiban.teacher.view.webView.webActivity.SendNoticeWebActivity");
                toNextActivity(aClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        @JavascriptInterface
        public String getUserId(){
            return User.getInstance().getUserId();
        }
    }
    private void toNextActivity(Class<?> aClass){
        runOnUiThread(()->{
            startActivityForResult(new Intent(getApplicationContext(),aClass),100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                tPresent.refresh();
                break;
        }
    }
}
