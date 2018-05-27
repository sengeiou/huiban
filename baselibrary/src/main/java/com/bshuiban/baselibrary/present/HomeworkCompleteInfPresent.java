package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.HomeworkCompleteInfContract;
import com.bshuiban.baselibrary.contract.HomeworkPendingInfContract;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.HomeworkListData;

import java.util.List;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：已完成作业详情
 */
public class HomeworkCompleteInfPresent extends HomeworkPresent<HomeworkCompleteInfContract.View> implements HomeworkCompleteInfContract.Present {
    public HomeworkCompleteInfPresent(HomeworkCompleteInfContract.View view) {
        super(view);
        flag=1;
    }

    @Override
    public void loadHomeworkInf(int workId, int wtype) {
        loadHomeworkInfData(workId,wtype);
    }

    @Override
    protected void updateHomeworkView(HomeworkInfBean.DataBean dataBean) {
        if(isEffective()){
            List<HomeworkListData> homeworkInfList = HomeworkListData.getHomeworkInfList(dataBean);
            view.updateHomeworkTitleList(homeworkInfList);
        }
    }
}
