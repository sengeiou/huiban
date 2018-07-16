package com.bshuiban.baselibrary.contract;

import android.app.Activity;

import com.bshuiban.baselibrary.model.MessageBean; /**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public interface LiuYanMsgListContract {
    interface View extends ListContract.View{
        void startReplyDialog(MessageBean.DataBean dataBean);
        Activity getActivity();
    }
    interface Parent{
        void deleteMessageItem(String messageId, String pid);
        void getReplyMessage(int index);
        void replayMessage(String json);
    }
}
