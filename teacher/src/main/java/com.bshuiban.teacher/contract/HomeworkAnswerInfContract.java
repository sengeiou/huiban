package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：
 */
public interface HomeworkAnswerInfContract {
    interface View extends BaseView{
        void updateWebView(String json);

        /**
         * 数据加载完毕
         * @param size
         */
        void loadWebView(int size);
    }
    interface Present{
        /**
         * 获取班级学生各题测试情况列表
         * @param preId 备课标识
         * @param workId 作业id
         * @param type 课前1，课中2，课后3
         * @param classId 班级标识
         */
        void loadHomeworkAnswerData(String preId,int workId,int type,String classId);

        void loadProblemContent(int position);
    }

}
