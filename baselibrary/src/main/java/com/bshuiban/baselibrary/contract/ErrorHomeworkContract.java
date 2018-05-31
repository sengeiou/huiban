package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：
 */
public interface ErrorHomeworkContract {
    interface View extends ListContract.View{
        /**
         * 进入错题解析
         * @param json
         */
        void toErrorHomeworkAnalysisPage(String json);

        //void toConsolidationWebActivity(String json);
    }
    interface Present extends ListContract.Present{
        void loadErrorHomeworkData();

        /**
         * 错题本 itemt 点击
         * @param index 下标
         */
        void itemClick(int index);
    }
}
