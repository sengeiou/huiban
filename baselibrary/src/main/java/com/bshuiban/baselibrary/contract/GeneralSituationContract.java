package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.GeneralBean;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：概况
 */
public interface GeneralSituationContract {
    interface View extends BaseView{
        void updateView(GeneralBean.DataBean data);
        String getUserId();
        String getClassId();
    }
    interface Present{
        /**
         * 获取网络数据
         */
        void askInterNetForData();
    }
}
