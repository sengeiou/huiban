package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public interface StudentAnswerInfContract {
    interface View extends BaseView{
        void updateView(String json);
    }
    interface Present{
        /**
         * 获取班级学生答题情况列表
         * @param preparationId 备课标识
         * @param process 课前0，课中1，课后2
         * @param workId 作业id
         * @param classId 班级标识
         */
        void loadAnswerInf(String preparationId,int process,int workId,int classId);
    }
}
