package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.LearningDynamicBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：学习动态
 */
public interface LearningDynamicContract {
    interface View extends BaseView{
        /**
         * 根据获得的数据更新view
         * @param data
         */
        void updateViewForData(List<LearningDynamicBean.DataBean> data);
    }
    interface Present{
        void askInternetForLearningDynamicData(String userId, int start, int limit);
        String getJsonString(String userId, int start, int limit);
    }
}
