package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.MessageBean;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：
 */
public interface HomePageContract {
    interface View extends BaseView{
        /**
         * 更新今日课表
         * @param json
         */
        void updateTodayClassSchedule(String json);

        /**
         * 慧辅导
         * @param json
         */
        void updateHuiFuDao(String json);

        /**
         * 留言列表
         * @param json
         */
        void updateMessageList(String json);
        void startReplyDialog(MessageBean.DataBean dataBean);

        void updateReplyDialog(MessageBean.DataBean dataBean);
    }
    interface Parent{
        /**
         * 获取今日课表
         */
        void getTodaySchedule(String userId);
        void getHuiFuDaoTwoData();
        void getMessageList(String userId);
        /**删除*/
        void delete(String messageId,String pid);
        /**回复留言*/
        void addRecevier(String json);

        /**
         * 获取回复内容数据
         * @param index
         */
        void getReplyMessage(int index);
    }
}
