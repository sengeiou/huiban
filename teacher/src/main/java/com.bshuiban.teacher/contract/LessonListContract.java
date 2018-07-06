package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.ListContract;
import com.bshuiban.baselibrary.model.SubjectBean;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：课程列表
 */
public interface LessonListContract {
    interface View extends ListContract.View{
        /**
         * 点击推荐给家长
         */
        void recommendParent();

        void loadAllSubject(SubjectBean dataBean);
    }
    interface Present{
        void loadLessonListData(String json);
        void loadSearchLessonListData(String search);
        /**
         * 推荐给家长
         */
        void loadRecommendParent(String courseId, String classId);
    }
}
