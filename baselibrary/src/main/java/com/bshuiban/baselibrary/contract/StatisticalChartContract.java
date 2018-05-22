package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.StatisticalChartBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：
 */
public interface StatisticalChartContract {
    interface View extends BaseView {

        void updateStatisticalChartView(List<StatisticalChartBean.DataBean.ListBean> listBeans);
    }

    interface Present {
        /**
         * 获取统计数据
         * @param subjectId
         * @param timeSlot 所选月份   201801
         */
        void getStatisticalData(String subjectId,String timeSlot);
    }
}
