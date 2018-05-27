package com.bshuiban.baselibrary.present;

import android.util.Log;

import com.bshuiban.baselibrary.contract.SubjectAllContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.StuLearnReportBean;
import com.bshuiban.baselibrary.model.User;

import java.util.List;

/**
 * Created by xinheng on 2018/5/27.<br/>
 * describe：
 */
public class SubjectAllPresent extends BasePresent<SubjectAllContract.View>implements SubjectAllContract.Present {
    public SubjectAllPresent(SubjectAllContract.View view) {
        super(view);
    }

    @Override
    public void loadStuLearnReportAll(String time) {//{"userId":"2030246","time":""}
        askInternet("getStuLearnReportAll", "{\"userId\":\""+ User.getInstance().getUserId()+"\",\"time\":\"" + time + "\"}", new RetrofitService.CallResult<StuLearnReportBean>(StuLearnReportBean.class) {
            @Override
            protected void success(StuLearnReportBean stuLearnReportBean) {
                List<StuLearnReportBean.DataBean.RankBean> listRank = null;
                List<StuLearnReportBean.DataBean.ContrastBean> listContrast = null;
                List<StuLearnReportBean.DataBean.ExceedBean> listExceed = null;
                if (stuLearnReportBean == null) {
                    view.fail("暂无数据");
                } else {
                    StuLearnReportBean.DataBean data = stuLearnReportBean.getData();
                    if (data != null) {
                        listRank = data.getRank();
                        listContrast = data.getContrast();
                        listExceed = data.getExceed();
                    }
                }
                view.showRankInfo(listRank);
                view.showContrastInfo(listContrast);
                view.showExceedInfo(listExceed);
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }

    @Override
    public void loadStudyBottom(String time) {
        askInternet("getStuLearnBottom","{\"time\":\""+time+"\",\"userId\":\""+ User.getInstance().getUserId()+"\"}", new RetrofitService.CallResult<StudyBottomBean>(StudyBottomBean.class) {
            @Override
            protected void success(StudyBottomBean studyBottomBean) {
                if(isEffective()) {
                    StudyBottomBean.DataBean data = studyBottomBean.getData();
                    view.updateStudyBottom(data);
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }
}
