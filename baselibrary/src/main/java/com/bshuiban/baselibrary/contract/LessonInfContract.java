package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public interface LessonInfContract {
    interface View extends BaseView{
        void updateView(String json);
    }
    interface Present{
        /**
         * 获取课程详情
         * @param courseId 教学资源标识
         */
        void getLessonInf(String courseId);
    }
}
