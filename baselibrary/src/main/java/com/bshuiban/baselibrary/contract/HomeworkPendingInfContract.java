package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.HomeworkBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：
 */
public interface HomeworkPendingInfContract {
    interface View extends BaseView{
        void updateHomeworkTitleList(List<HomeworkBean> list, int time);
        void updateAnswerResult(String answer);
    }
    interface Present extends HomeworkReportContract.Present{
        void uploadImage(String imgPath);
    }
}
