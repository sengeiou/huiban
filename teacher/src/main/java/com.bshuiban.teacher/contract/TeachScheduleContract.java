package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.model.ClassScheduleBean;

import java.util.List;

/**
 * Created by xinheng on 2018/6/8.<br/>
 * describe：
 */
public interface TeachScheduleContract {
    interface View extends BaseView{
        void updateView(List<List<ClassScheduleBean.DataBean>> data);
    }
    interface Present{
        void loadSchedule();
    }
}
