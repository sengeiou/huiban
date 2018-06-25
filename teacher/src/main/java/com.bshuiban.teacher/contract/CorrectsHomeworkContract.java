package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.model.HomeworkBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：
 */
public interface CorrectsHomeworkContract {
    interface View extends BaseView{
        void updateListCountView(List<HomeworkBean> homeworkBean, String title);
    }
    interface Present{
        void loadHomeworkInf(int workId, int wtype, String s);
    }
}
