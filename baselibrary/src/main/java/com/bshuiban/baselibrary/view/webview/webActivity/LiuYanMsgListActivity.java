package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.contract.LiuYanMsgListContract;
import com.bshuiban.baselibrary.model.MessageBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.LiuYanMsgListParent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.dialog.CommentDialog;
import com.bshuiban.baselibrary.view.dialog.ReplyDialog;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：留言
 */
public class LiuYanMsgListActivity extends BaseWebActivity<LiuYanMsgListParent> implements LiuYanMsgListContract.View{

    private String messageUserId;
    private String name;
    private boolean isSelf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        messageUserId = intent.getStringExtra("userId");
        if(TextUtils.isEmpty(messageUserId)){
            messageUserId= User.getInstance().getUserId();
        }
        if(TextUtils.isEmpty(name)){
            name=User.getInstance().getUserName();
        }
        isSelf=messageUserId==User.getInstance().getUserId();
        tPresent=new LiuYanMsgListParent(this,messageUserId);
        loadFileHtml("leave");
        LiuYanMsgListHtml messageList=new LiuYanMsgListHtml();
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

    @Override
    public void startReplyDialog(MessageBean.DataBean dataBean) {
        ReplyDialog replyDialog=new ReplyDialog(this,isSelf);
        replyDialog.setViewData(dataBean);
        replyDialog.show();
        replyDialog.setMessageListListener(new ReplyDialog.MessageListListener() {
            @Override
            public void deleteMessageItem(String messageId, String pid) {
                tPresent.deleteMessageItem(messageId,pid);
            }

            @Override
            public void showCommitDialog() {
                CommentDialog commentDialog = new CommentDialog("请输入内容", inputText -> {
                    //recevieId= ;//int,接收人id，给谁留言
                    //String sendId= User.getInstance().getUserId();//int,留言人、发送人id
                    //messageId		//int，消息id，评论时传空
                    //String content			//string 回复的内容
                    //"recevieId":,"messageId":,"content":"","sendId":""
                    tPresent.replayMessage("{\"recevieId\":\""+dataBean.getSend()+"\",\"messageId\":\""+dataBean.getId()+"\",\"content\":\""+inputText+"\"}");
                    replyDialog.dismiss();
                });
                commentDialog.show(getSupportFragmentManager(),"commit");
            }
        });
    }

    class LiuYanMsgListHtml extends MessageList{
        @JavascriptInterface
        public void reply(int index){
            runOnUiThread(()->{
                tPresent.getReplyMessage(index);
            });
        }
        @JavascriptInterface
        public String getRecevieId(){
            return messageUserId;
        }
        @JavascriptInterface
        public String getName(){
            return name;
        }
        @JavascriptInterface
        public String getUserId(){
            return User.getInstance().getUserId();
        }
        @JavascriptInterface
        public boolean isSelf(){
            return isSelf;
        }
    }
}

