package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.MiddleReportInfContract;

/**
 * Created by xinheng on 2018/6/4.<br/>
 * describe：课中报告信息
 */
public class MiddleReportInfPresent extends BasePresent<MiddleReportInfContract.View>implements MiddleReportInfContract.Present {
    public MiddleReportInfPresent(MiddleReportInfContract.View view) {
        super(view);
    }

    @Override
    public void loadHBTeaPreKnowRate(String preId, int type, int workId, int classId) {//{"preId":"","type":,"workId":,"classId":}
        askInternet("getHBTeaPreKnowRate", "{\"preId\":\"" + preId + "\",\"type\":" + type + ",\"workId\":" + workId + ",\"classId\":" + classId + "}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateView(1,msg);
                }
            }

            @Override
            protected void fail(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }

    @Override
    public void loadHBTeaPreExamRate(String preId, int type, int workId, int classId) {
        askInternet("getHBTeaPreExamRate", "{\"preId\":\"" + preId + "\",\"type\":" + type + ",\"workId\":" + workId + ",\"classId\":" + classId + "}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateView(2,msg);
                }
            }

            @Override
            protected void fail(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }
}
