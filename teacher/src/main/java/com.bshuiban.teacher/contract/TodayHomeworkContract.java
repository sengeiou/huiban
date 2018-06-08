package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public interface TodayHomeworkContract {
    interface View extends BaseView{
        void updateListView(int type,String json);
    }
    interface Present{
        /**
         * 老师端获取今日作业列表
         * @param type 课前1、课后3
         */
        void loadTodayHomework(int type);
        void addWorkRemind(int workId);
    }
}
