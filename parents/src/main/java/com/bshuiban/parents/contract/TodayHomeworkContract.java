package com.bshuiban.parents.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public interface TodayHomeworkContract {
    interface View extends BaseView{
        void updateListView(int type, String json);
    }
    interface Present{
        /**
         * 家长端获取今日作业列表
         */
        void loadTodayHomework();
    }
}
