package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/17.<br/>
 * describe：关注列表
 */
public interface GuanZhuListContract extends ListContract{
    interface View extends ListContract.View{
        void goLiuYan(String name,String userId);
        void guanZhuResult();
    }
    interface Present {

        /**
         * 获取筛选的列表数据
         * @param msg
         */
        void getSearchData(String msg);
        void guanZhu(String key,String json);
    }
}
