package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：筛选
 */
public interface FilterConditionContract {
    interface View extends BaseView{
        void updateView(String json);

        void loadAllSubject(String json);
    }
    interface Present{
        void loadFilterCondition(String key,String json);

        void loadAllSubject();
    }
}
