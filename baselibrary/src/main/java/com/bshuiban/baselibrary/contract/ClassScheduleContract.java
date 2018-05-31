package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.ClassScheduleBean;
import com.bshuiban.baselibrary.model.StudentClassClassScheduleBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：班级课表
 */
public interface ClassScheduleContract {
    interface View extends BaseView{
        /**
         * 更新课表
         * @param data
         */
        void updateSchedule(List<List<ClassScheduleBean.DataBean>> data);

        void updateSchedule1(StudentClassClassScheduleBean bean);
    }
    interface Present{
        /**
         * 通过网络获取课表数据
         */
        void askInternetForScheduleData(String classId);
    }
}
