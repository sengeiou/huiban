package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：
 */
public interface ErrorHomeworkAnalysisContract {
    interface View extends BaseView{
        void deleteErrorHomeworkSuccess();
    }
    interface Present{
        /**
         * 删除错题
         * @param examId 题目id
         */
        void deleteErrorHomework(int examId);
    }
}
