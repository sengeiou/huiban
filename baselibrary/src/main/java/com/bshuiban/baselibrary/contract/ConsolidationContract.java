package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：巩固练习
 */
public interface ConsolidationContract {
    interface View extends BaseView{
        void updateView(String json);
    }
    interface Present{
        void loadConsolidationHomeworkData(int examId);
        void commitData(String json);
    }
}
