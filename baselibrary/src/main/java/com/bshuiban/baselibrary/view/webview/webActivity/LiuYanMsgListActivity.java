package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.contract.LiuYanMsgListContract;
import com.bshuiban.baselibrary.present.LiuYanMsgListParent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：留言
 */
public class LiuYanMsgListActivity extends BaseWebActivity<LiuYanMsgListParent> implements LiuYanMsgListContract.View{

    private String messageUserId;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = ViewUtils.getFrameLayout(this);
        mWebView=getWebView(getApplicationContext());
        frameLayout.addView(mWebView);
        setContentView(frameLayout);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        messageUserId = intent.getStringExtra("userId");
        tPresent=new LiuYanMsgListParent(this,messageUserId);
        loadFileHtml("leave");
        MessageList messageList=new MessageList();
        registerWebViewH5Interface(messageList);
        messageList.setOnListener(new MessageList.MessageListListener() {
            @Override
            public void deleteMessageItem(String messageId, String pid) {
                tPresent.deleteMessageItem(messageId,pid);
            }

            @Override
            public void replayMessage(String json) {
                tPresent.replayMessage(json);
            }

            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }

            @Override
            public void refresh() {
                tPresent.refresh();
            }
        });
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.getInterNetData();
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
    class LiuYanMsgListHtml extends MessageList{
        @JavascriptInterface
        public void reply(int index){
            runOnUiThread(()->{
                tPresent.getReplyMessage(index);
            });
        }
    }
}

