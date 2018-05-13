package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：
 */
public interface ClassActivityContract {
    interface View extends BaseView{
        void updateViewForData();
    }
    interface Present{
        /**
         * 获取班级活动数据
         * @param classId 班级id
         * @param start 开始点
         * @param limit 加载数量
         */
        void askInternetForClassActivityData(String classId, int start, int limit);

        /**
         * 构建json串
         * @param classId
         * @param start
         * @param limit
         * @return
         */
        String getJsonString(String classId, int start, int limit);
    }
}
