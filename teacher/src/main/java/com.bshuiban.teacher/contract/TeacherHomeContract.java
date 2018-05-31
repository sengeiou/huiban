package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.teacher.model.TeacherUser;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public interface TeacherHomeContract {
    interface View extends BaseView{
        void updateSlideView(TeacherUser.DataBean data);
    }
    interface Present{
        void getUserDataForInternet();
    }
}
