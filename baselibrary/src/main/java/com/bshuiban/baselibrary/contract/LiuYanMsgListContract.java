package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public interface LiuYanMsgListContract {
    interface View extends ListContract.View{
    }
    interface Parent{
        void deleteMessageItem(String messageId, String pid);

        void replayMessage(String json);
    }
}
