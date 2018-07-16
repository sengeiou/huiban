package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.StuLearnReportBean;
import com.bshuiban.baselibrary.model.StudyBottomBean;

import java.util.List; /**
 * Created by xinheng on 2018/5/27.<br/>
 * describe：学科总览
 */
public interface SubjectAllContract {
    interface View extends BaseView{

        void updateStudyBottom(StudyBottomBean.DataBean data);

        void showRankInfo(List<StuLearnReportBean.DataBean.RankBean> listRank);

        void showContrastInfo(List<StuLearnReportBean.DataBean.ContrastBean> listContrast);

        void showExceedInfo(List<StuLearnReportBean.DataBean.ExceedBean> listExceed);
    }
    interface Present{
        void loadStuLearnReportAll(String time);
        void loadStudyBottom(String time);
    }
}
