package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.present.StudyBottomBean;
import com.bshuiban.baselibrary.present.StudyReportBean;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：
 */
public interface SubjectChildContract {
    interface View extends BaseView{
        /**
         * 更新掌握度
         * @param data
         */
        void updateProgressView(StudyReportBean.DataBean data);
        void updateStudyBottom(StudyBottomBean.DataBean data);
    }
    interface Present{
        void loadStudyReportData(int subjectId,String time);

        /**
         * 底部四个统计
         */
        void loadStudyBottom(int subjectId,String time);
    }
}
