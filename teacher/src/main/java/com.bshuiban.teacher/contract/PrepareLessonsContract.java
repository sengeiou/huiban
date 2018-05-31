package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.ListContract;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：我的备课
 */
public interface PrepareLessonsContract {
    interface View extends ListContract.View{
    }
    interface Present{
        void loadLessons(String json);
    }
}
