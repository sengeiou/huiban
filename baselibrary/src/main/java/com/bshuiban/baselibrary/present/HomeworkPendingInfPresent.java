package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.HomeworkPendingInfContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.HomeworkListData;
import com.bshuiban.baselibrary.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：未完成（待）作业详情
 */
public class HomeworkPendingInfPresent extends HomeworkPresent<HomeworkPendingInfContract.View> implements HomeworkPendingInfContract.Present {
    public HomeworkPendingInfPresent(HomeworkPendingInfContract.View view) {
        super(view);
        flag=0;
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

    @Override
    public void uploadImage(String imgPath) {

    }
}
