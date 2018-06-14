package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/17.<br/>
 * describe：关注列表
 */
public interface GuanZhuListContract extends ListContract{
    interface View extends ListContract.View{
        void goLiuYan(String name,String userId);
        void guanZhuResult(boolean tag);
    }
    interface Present {

        /**
         * 获取筛选的列表数据
         * @param msg
         */
        void getSearchData(String msg);
        /**
         * 关注
         * @param tag true 加关注
         * @param attUserId
         */
        void guanZhu(boolean tag, int attUserId);
    }
}
