package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：
 */
public interface ReportContract {
    interface View extends BaseView{
        /**
         * 更新ui
         * @param type 1 各班报告，2 学生实施掌握情
         * @param json
         */
        void updateView(int type,String json);

        void loadListComplete();
    }
    interface Present{
        /**
         * 获取年级各班报告
         * @param subjectId
         * @param gradeId
         * @param timeSlot
         */
        void loadReportsOfClasses(int subjectId,int gradeId,String timeSlot);

        /**
         * 获取班级各学生实施掌握情
         * @param subjectId
         * @param gradeId
         * @param timeSlot
         */
        void loadGraspingOfStudents(int subjectId,int gradeId,String timeSlot,int classId);

        /**
         * 学校的科目列表
         *  siteId ，学校id
         */
        void loadSubjectList();
        void loadGradeList();
        void loadGradeClassList();
    }
}
