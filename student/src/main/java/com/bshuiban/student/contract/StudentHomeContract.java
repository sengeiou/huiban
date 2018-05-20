package com.bshuiban.student.contract;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.student.model.StudentUser;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public interface StudentHomeContract {
    interface View extends BaseView{
        void updateSlideView(StudentUser.DataBean data);
    }
    interface Present{
        void getUserDataForInernet();
    }
}
