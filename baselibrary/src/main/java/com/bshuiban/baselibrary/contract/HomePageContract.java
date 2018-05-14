package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：
 */
public interface HomePageContract {
    interface View extends BaseView{
        void updateView();
    }
    interface Parent{
        /**
         * 获取今日课表
         */
        void getTodaySchedule();
    }
}
