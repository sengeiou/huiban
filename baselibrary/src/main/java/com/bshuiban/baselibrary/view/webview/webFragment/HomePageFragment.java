package com.bshuiban.baselibrary.view.webview.webFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bshuiban.baselibrary.contract.HomePageContract;
import com.bshuiban.baselibrary.model.MessageBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.HomePageParent;
import com.bshuiban.baselibrary.utils.DialogUtils;
import com.bshuiban.baselibrary.view.dialog.CommentDialog;
import com.bshuiban.baselibrary.view.dialog.ReplyDialog;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：首页
 * 1.获取今日学生课表  switchs
 * 2.慧辅导两条数据 imag
 * 3.留言列表 message
 */
public class HomePageFragment extends InteractionBaseWebViewFragment<HomePageParent> implements HomePageContract.View {
    private boolean tagToast=true;
    private boolean tagResume;
    private ReplyDialog replyDialog;

    @Override
    public void onResume() {
        if(!tagResume) {
            tagToast = false;
            tPresent.getTodaySchedule(User.getInstance().getUserId());
            tPresent.refresh();
        }
        tagResume=false;
        super.onResume();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, "onHiddenChanged: "+hidden );
        if(hidden){
            tagToast=false;
        }
        super.onHiddenChanged(hidden);
//        if(!hidden){
//            tagToast=false;
//            tPresent.refresh();
//        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tagResume=true;
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
        showLoadingDialog();
    }

    @Override
    public void dismissDialog() {
        dismissLoadingDialog();
    }

    @Override
    public void fail(String error) {
        if(tagToast) {
            if(null!=error&&!error.contains("留言：暂无"))
                toast(error);
        }else {
            tagToast=true;
        }
    }

    @Override
    public void updateTodayClassSchedule(String json) {
        loadJavascriptMethod("switchs",json);
    }

    @Override
    public void updateHuiFuDao(String json) {
        loadJavascriptMethod("imag",json);
    }

    @Override
    public void updateMessageList(String json) {

        loadJavascriptMethod("message",json);
    }
    @Override
    public void startReplyDialog(MessageBean.DataBean dataBean){
        if(null==replyDialog) {
            replyDialog = new ReplyDialog(getActivity());
            replyDialog.setMessageListListener(new ReplyDialog.MessageListListener() {
                @Override
                public void deleteMessageItem(String messageId, String pid) {
                    DialogUtils.showMessageSureCancelDialog(getActivity(), "确认删除此留言？", v -> {
                        tPresent.delete(messageId, pid);
                        replyDialog.dismiss();
                    });
                }

                @Override
                public void showCommitDialog() {
                    CommentDialog commentDialog = new CommentDialog("请输入内容", inputText -> {
                        //recevieId= ;//int,接收人id，给谁留言
                        String sendId= User.getInstance().getUserId();//int,留言人、发送人id
                        //messageId		//int，消息id，评论时传空
                        //String content			//string 回复的内容
                        //"recevieId":,"messageId":,"content":"","sendId":""
                        tPresent.addRecevier("{\"recevieId\":\""+dataBean.getSend()+"\",\"messageId\":\""+dataBean.getId()+"\",\"content\":\""+inputText+"\",\"sendId\":\""+sendId+"\"}");
                        //replyDialog.dismiss();
                    });
                    commentDialog.show(getChildFragmentManager(),"commit");
                }
            });
        }
        replyDialog.setViewData(dataBean);
        replyDialog.show();

    }

    @Override
    public void updateReplyDialog(MessageBean.DataBean dataBean) {
        if(null!=replyDialog) {
            replyDialog.setViewData(dataBean);
        }
    }
}
