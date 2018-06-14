package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.UploadAppBean;

/**
 * Created by xinheng on 2018/6/14.<br/>
 * describe：
 */
public interface UpdateAppContract {
    interface View extends BaseView{
        /**
         *
         * @param dataBean
         */
        void updateView(UploadAppBean.DataBean dataBean);
    }
    interface Present{
        void loadUpdate();
    }
}
