package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：
 */
public interface CorrectsHomeworkContract {
    interface View extends BaseView{
        void updateListCountView();
    }
    interface Present{
        void loadHomeworkInf(int workId, int wtype);
    }
}
