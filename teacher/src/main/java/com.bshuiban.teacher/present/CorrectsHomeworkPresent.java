package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.present.HomeworkPresent;
import com.bshuiban.teacher.contract.CorrectsHomeworkContract;

import java.util.List;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：批阅作业
 */
public class CorrectsHomeworkPresent extends HomeworkPresent<CorrectsHomeworkContract.View> implements CorrectsHomeworkContract.Present {
    public CorrectsHomeworkPresent(CorrectsHomeworkContract.View view) {
        super(view);
    }

    @Override
    public void loadHomeworkInf(int workId, int wtype) {
        loadHomeworkInfData(workId,wtype);
    }

    @Override
    protected void updateHomeworkView(HomeworkInfBean.DataBean dataBean) {
        List<HomeworkBean> homeworkBean = HomeworkBean.getHomeworkBean(dataBean);
        view.updateListCountView(homeworkBean);
    }
}
