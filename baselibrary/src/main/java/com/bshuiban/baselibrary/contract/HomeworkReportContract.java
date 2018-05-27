package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：
 */
public interface HomeworkReportContract {
    interface View extends BaseView{
        void updateView(String json);
    }
    interface Present{
        void loadHomeworkInf(int workId,int wtype);
    }
}
