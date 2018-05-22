package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：
 */
public interface AllSubjectContract {
    interface View extends BaseView{

        /**
         * 学科排名
         */
        void updateSubjectRanking();

        /**
         * 掌握度 表格
         */
        void updateSubjectGridView();

        /**
         * 柱状图
         */
        void updateSubjectBarView();
        /**
         * 学习情况，底部
         */
        void updateStudyBottom();
    }
}
