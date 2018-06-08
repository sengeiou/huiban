package com.bshuiban.parents.contract;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.parents.modul.ParentsUser;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public interface ParentsHomeContract {
    interface View extends BaseView{
        void updateSlideView(ParentsUser.DataBean data);
    }
    interface Present{
        void getUserDataForInternet();
    }
}
