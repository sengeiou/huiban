package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.UserMoreBean;

import java.util.List;

/**
 * Created by xinheng on 2018/6/12.<br/>
 * describe：
 */
public interface ChangeUserContract {
    interface View extends BaseView{
        void updateView(List<UserMoreBean.DataBean> data);
    }
    interface Present{
        void loadMoreUserData();
    }
}
