package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.HomeworkReportContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：
 */
public class HomeworkReportPresent extends HomeworkPresent<HomeworkReportContract.View> implements HomeworkReportContract.Present {
    public HomeworkReportPresent(HomeworkReportContract.View view) {
        super(view);
        flag=1;
    }

    @Override
    public void loadHomeworkInf(int workId,int wtype) {
        loadHomeworkInfData(workId,wtype);
    }

    @Override
    protected void updateHomeworkView(HomeworkInfBean.DataBean data) {
        HomeworkInfBean.DataBean bean = new HomeworkInfBean.DataBean();
        bean.setAddtime(data.getAddtime());
        bean.setTitle(data.getTitle());
        bean.setTimes(data.getTimes());
        bean.setEndtime(data.getEndtime());
        bean.setExamCnt(data.getExamCnt());
        bean.setRank(data.getRank());
        bean.setUserRate(data.getUserRate());
        bean.setClassRate(data.getClassRate());
        bean.setRw(data.getRw());
        bean.setKnowArr(data.getKnowArr());
        String s = new Gson().toJson(bean);
        view.updateView(s);
    }

}
