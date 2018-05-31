package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.StudentAnswerInfContract;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public class StudentAnswerInfPresent extends BasePresent<StudentAnswerInfContract.View>implements StudentAnswerInfContract.Present {
    public StudentAnswerInfPresent(StudentAnswerInfContract.View view) {
        super(view);
    }

    @Override
    public void loadAnswerInf(String preparationId, int process, int workId, int classId) {
        //{"preparationId":"","process":,"workId":,"classId":}
        askInternet("getHBStudentTypeWork", "{\"preparationId\":\""+preparationId+"\",\"process\":"+process+",\"workId\":"+workId+",\"classId\":"+classId+"}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateView(msg);
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
