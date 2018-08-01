package com.bshuiban.baselibrary.view.webview.webActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.contract.LiuYanMsgListContract;
import com.bshuiban.baselibrary.model.MessageBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.LiuYanMsgListParent;
import com.bshuiban.baselibrary.utils.ViewUtils;

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
        LiuYanMsgListParent.LiuYanMsgListHtml messageList=tPresent.getLiuYanListHtml(name,isSelf);
        registerWebViewH5Interface(messageList);
        messageList.setOnListener(tPresent.getMessageListListener());
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
        if(null!=null&&!error.contains("暂无数据")) {
            toast(error);
        }
    }

    @Override
    public void startReplyDialog(MessageBean.DataBean dataBean) {
        tPresent.startReplyDialog(getSupportFragmentManager(),this,dataBean,isSelf);
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}

