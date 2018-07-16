package com.bshuiban.baselibrary.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.LiuYanMsgListContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.MessageBean;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.utils.DialogUtils;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.view.dialog.CommentDialog;
import com.bshuiban.baselibrary.view.dialog.ReplyDialog;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：留言功能模块
 */
public class LiuYanMsgListParent extends ListPresent<LiuYanMsgListContract.View> implements LiuYanMsgListContract.Parent {
    /**
     * 人的userId
     */
    private final String messageUserId;
    private List<MessageBean.DataBean> dataBeans;

    public LiuYanMsgListParent(LiuYanMsgListContract.View view, String messageUserId) {
        super(view);
        this.messageUserId = messageUserId;
    }

    @Override
    public void getInterNetData() {
        call = RetrofitService.getInstance().getServiceResult("getUserLeaveList", TextUtils.getUserIdListJson(messageUserId, start, limit), callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        view.updateList(json);
        if (!android.text.TextUtils.isEmpty(json)) {
            dataBeans = gson.fromJson(json, new TypeToken<List<MessageBean.DataBean>>() {
            }.getType());
        }
    }

    @Override
    public void fail(String error) {
        if (start > 0 && error.contains("暂无数据")) {
            error = "没有更多数据了";
        } else {
            view.updateList("[]");
        }
        view.fail("留言：" + error);
    }

    @Override
    public void deleteMessageItem(String messageId, String pid) {
        if (null == pid) {
            if (!isEffective()) {
                return;
            }
            DialogUtils.showMessageSureCancelDialog(view.getActivity(), "确认删除此留言？", v -> {
                String key1 = "delComment";
                String json1 = "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"messageId\":\"" + messageId + "\"}";
                deleteMessage(key1,json1);
            });
        } else {
            //key = "delCommentReply";
            //json = "{\"pid\":\"" + pid + "\",\"messageId\":\"" + messageId + "\"}";
            deleteMessage("delCommentReply","{\"pid\":\"" + pid + "\",\"messageId\":\"" + messageId + "\"}");
        }

    }

    private void deleteMessage(String key, String json) {
        call = RetrofitService.getInstance().getServiceResult(key, json, new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                refresh();
            }

            @Override
            protected void error(String error) {
                if (isEffective()) {
                    view.fail(error);
                }
            }
        });
    }

    @Override
    public void replayMessage(String json) {
        JsonElement parse = new JsonParser().parse(json);
        if (parse.isJsonObject()) {
            JsonObject jsonObject = parse.getAsJsonObject();
            jsonObject.addProperty("sendId", User.getInstance().getUserId());
            json = gson.toJson(jsonObject);
            RetrofitService.getInstance().getServiceResult("addLeaveMessage", json, new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
                @Override
                protected void success(ResultBean resultBean) {
                    refresh();
                    view.fail("成功");
                }

                @Override
                protected void error(String error) {
                    if (isEffective()) {
                        view.fail(error);
                    }
                }
            });
        }
    }

    @Override
    public void getReplyMessage(int index) {
        if (isEffective() && null != dataBeans && dataBeans.size() > index) {
            view.startReplyDialog(dataBeans.get(index));
        }
    }

    public void startReplyDialog(FragmentManager fragmentManager, Context activity, MessageBean.DataBean dataBean, boolean isSelf) {
        ReplyDialog replyDialog = new ReplyDialog(activity, isSelf);
        replyDialog.setViewData(dataBean);
        replyDialog.show();
        replyDialog.setMessageListListener(new ReplyDialog.MessageListListener() {
            @Override
            public void deleteMessageItem(String messageId, String pid) {
                DialogUtils.showMessageSureCancelDialog(view.getActivity(), "确认删除此留言？", v -> {
                    LiuYanMsgListParent.this.deleteMessageItem(messageId, pid);
                });
            }
            @Override
            public void showCommitDialog() {
                CommentDialog commentDialog = new CommentDialog("请输入内容", inputText -> {
                    //recevieId= ;//int,接收人id，给谁留言
                    //String sendId= User.getInstance().getUserId();//int,留言人、发送人id
                    //messageId		//int，消息id，评论时传空
                    //String content			//string 回复的内容
                    //"recevieId":,"messageId":,"content":"","sendId":""
                    LiuYanMsgListParent.this.replayMessage("{\"recevieId\":\"" + dataBean.getSend() + "\",\"messageId\":\"" + dataBean.getId() + "\",\"content\":\"" + inputText + "\"}");
                    replyDialog.dismiss();
                });
                commentDialog.show(fragmentManager, "commit");
            }
        });
    }

    public MessageList.MessageListListener getMessageListListener() {
        return messageListListener;
    }

    private MessageList.MessageListListener messageListListener = new MessageList.OnMessageListListener() {
        @Override
        public void deleteMessageItem(String messageId, String pid) {
            LiuYanMsgListParent.this.deleteMessageItem(messageId, pid);
        }

        @Override
        public void replayMessage(String json) {
            LiuYanMsgListParent.this.replayMessage(json);
        }

        @Override
        public void loadMore() {
            LiuYanMsgListParent.this.loadMoreData();
        }

        @Override
        public void refresh() {
            LiuYanMsgListParent.this.refresh();
        }
    };

    public LiuYanMsgListHtml getLiuYanListHtml(String name, boolean isSelf) {
        return new LiuYanMsgListHtml(name, isSelf, this);
    }

    public static class LiuYanMsgListHtml extends MessageList {
        private Handler handler;
        private String name;
        private boolean isSelf;
        private WeakReference<LiuYanMsgListParent> reference;

        public LiuYanMsgListHtml(String name, boolean isSelf, LiuYanMsgListParent liuYanMsgListParent) {
            reference = new WeakReference<>(liuYanMsgListParent);
            this.name=name;
            this.isSelf=isSelf;
            //TODO 必须主线程创建，此处没做限制
            handler = new Handler(Looper.getMainLooper());
        }

        @JavascriptInterface
        public void reply(int index) {
            if (null != reference.get()) {
                handler.post(() -> reference.get().getReplyMessage(index));
            }
        }

        @JavascriptInterface
        public String getRecevieId() {
            if (null != reference.get()) {
                return reference.get().messageUserId;
            }
            return null;
        }

        @JavascriptInterface
        public String getName() {
            return name;
        }

        @JavascriptInterface
        public String getUserId() {
            return User.getInstance().getUserId();
        }

        @JavascriptInterface
        public boolean isSelf() {
            return isSelf;
        }
    }
}
