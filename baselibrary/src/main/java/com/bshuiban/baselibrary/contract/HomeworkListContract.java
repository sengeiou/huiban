package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public interface HomeworkListContract {
    interface View extends ListContract.View{
        void updateSubjects(String json);
    }
    interface Present{
        void loadMoreData();
        void refresh();

        /**
         * 设置数据类型
         * @param subjectId  0-->false 待完成;true 已完成
         */
        void setTag(int subjectId);
        void setTag(boolean tag);
        void loadSubjectList();
    }
}
