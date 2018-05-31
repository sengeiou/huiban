package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public interface LessonInfContract {
    interface View extends BaseView{
        void updateView(String json);

        /**
         * 收藏成功
         * @param tag 1 添加成功，0取消成功
         */
        void collectSuccess(String tag);

        /**
         * 推荐结果
         */
        void recommendSuccess();
    }
    interface Present{
        /**
         * 获取课程详情
         * @param courseId 教学资源标识
         */
        void getLessonInf(String courseId);

        /**
         * 添加收藏
         * @param courseId 教学资源id
         */
        void addCollect(String courseId);
        void deleteCollect(String courseId);
    }
}
