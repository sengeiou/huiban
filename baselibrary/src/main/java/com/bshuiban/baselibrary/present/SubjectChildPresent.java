package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.SubjectChildContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/5/22.<br/>
 * describe：子学科
 */
public class SubjectChildPresent extends BasePresent<SubjectChildContract.View> implements SubjectChildContract.Present {
    public SubjectChildPresent(SubjectChildContract.View view) {
        super(view);
    }

    @Override
    public void loadStudyReportData(int subjectId,String time) {//{"subjectId":,"time":"201805","userId":"2030246","classId":""}
        askInternet("getStuLearnReportSubMineKnow","{\"subjectId\":"+subjectId+",\"time\":\""+time+"\",\"userId\":\""+User.getInstance().getUserId()+"\",\"classId\":\""+User.getInstance().getClassId()+"\"}", new RetrofitService.CallResult<StudyReportBean>(StudyReportBean.class) {
            @Override
            protected void success(StudyReportBean studyReportBean) {
                if(isEffective()){
                    StudyReportBean.DataBean data = studyReportBean.getData();
                    view.updateProgressView(data);
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

    @Override
    public void loadStudyBottom(int subjectId,String time) {//{"subjectId":,"time":"","userId":"2030246"}
        RetrofitService.getInstance().getServiceResult("getStuLearnBottom","{\"subjectId\":"+subjectId+",\"time\":\""+time+"\",\"userId\":\""+ User.getInstance().getUserId()+"\"}", new RetrofitService.CallResult<StudyBottomBean>(StudyBottomBean.class) {
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
