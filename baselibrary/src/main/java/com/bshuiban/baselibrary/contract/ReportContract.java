package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.SubjectBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：
 */
public interface ReportContract {
    interface View extends BaseView{
        /**
         * 标题栏
         * @param data
         */
        void updateTitleBar(List<SubjectBean.DataBean> data);
        /**
         * 日期
         */
        void updateDateView();
    }
    interface Present{
        void getTitleBarData();
        //void getDate();
    }
}
