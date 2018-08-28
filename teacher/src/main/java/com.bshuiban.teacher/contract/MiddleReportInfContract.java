package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/6/4.<br/>
 * describe：
 */
public interface MiddleReportInfContract {
    interface View extends BaseView{
        void updateView(int type,String json);
    }
    interface Present{
        /**
         * 获取班级学生知识点掌握情况列表
         * @param preId
         * @param type
         * @param workId
         * @param classId
         */
        void loadHBTeaPreKnowRate(String preId,int type,int workId,int classId);

        /**
         * 获取班级学生各题测试情况列表
         * @param preId
         * @param type
         * @param workId
         * @param classId
         */
        void loadHBTeaPreExamRate(String preId,int type,int workId,int classId);
    }
}
