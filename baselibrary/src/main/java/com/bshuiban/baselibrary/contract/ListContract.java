package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/16.<br/>
 * describe：
 */
public interface ListContract {
    interface View extends BaseView{
        /**
         * 更新数据
         * @param json
         */
        void updateList(String json);
    }
    interface Present{
        void loadMoreData();
        void refresh();
        void getInterNetData();
        void updateView(String json);
        void clearArray();
    }
}
