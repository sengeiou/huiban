package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.teacher.model.PrepareLessonBean;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：备课详情信息
 */
public interface PrepareLessonInfContract {
    interface View extends BaseView{
        void updateLessonInf(PrepareLessonBean prepareLessonBean);
    }
    interface Present{
        /**
         * 获取备课详情信息
         * @param wtype
         * @param preId
         */
        void loadPrepareLessonInf(int wtype,String preId);
    }
}
