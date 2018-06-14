package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.TeachClassBean;

import java.util.List;

/**
 * Created by xinheng on 2018/6/14.<br/>
 * describe：
 */
public interface TeachClassContract {
    interface View extends BaseView{
        void updateData(List<TeachClassBean.DataBean> data);
    }
    interface Present{
        void loadTeachClass();
    }
}
