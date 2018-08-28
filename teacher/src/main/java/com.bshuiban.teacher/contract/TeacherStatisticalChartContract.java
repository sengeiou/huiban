package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.model.StatisticalChartBean;
import com.bshuiban.teacher.model.TeacherStatisticalChartBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：
 */
public interface TeacherStatisticalChartContract {
    interface View extends BaseView {

        void updateStatisticalChartView(List<TeacherStatisticalChartBean.DataBean> listBeans);
    }

    interface Present {
        /**
         * 获取统计数据
         * @param subjectId
         * @param timeSlot 所选月份   201801
         * @param gradeId
         * @param workType
         */
        void getStatisticalData(String subjectId, String timeSlot,String gradeId,int workType);
    }
}
