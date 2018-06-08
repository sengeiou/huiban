package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/6/4.<br/>
 * describe：
 */
public interface MiddleReportInfContract {
    interface View extends BaseView{
        void updateView(int type,String json);
    }
    interface Present{
        void loadHBTeaPreKnowRate(String preId,int type,int workId,int classId);
        void loadHBTeaPreExamRate(String preId,int type,int workId,int classId);
    }
}
