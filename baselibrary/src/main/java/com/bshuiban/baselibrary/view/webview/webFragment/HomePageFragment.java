package com.bshuiban.baselibrary.view.webview.webFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bshuiban.baselibrary.contract.HomePageContract;
import com.bshuiban.baselibrary.model.MessageBean;
import com.bshuiban.baselibrary.present.HomePageParent;
import com.bshuiban.baselibrary.view.dialog.ReplyDialog;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：首页
 * 1.获取今日学生课表  classList
 * 2.慧辅导两条数据 train
 * 3.留言列表 message
 */
public class HomePageFragment extends InteractionBaseWebViewFragment<HomePageParent> implements HomePageContract.View {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new HomePageParent(this);
    }

    @Override
    protected void delete(String messageId, String pid) {
        tPresent.delete(messageId,pid);
    }

    protected void addRecevier(String json){
        tPresent.addRecevier(json);
    }

    @Override
    public void update(Bundle bundle) {

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
    public void updateTodayClassSchedule(String json) {
        loadJavascriptMethod("classList",json);
    }

    @Override
    public void updateHuiFuDao(String json) {
        loadJavascriptMethod("train",json);
    }

    @Override
    public void updateMessageList(String json) {
        loadJavascriptMethod("message",json);
    }
    @Override
    public void startReplyDialog(MessageBean.DataBean dataBean){
        ReplyDialog replyDialog=new ReplyDialog(getActivity());
        replyDialog.setViewData(dataBean);
        replyDialog.show();
    }
}
