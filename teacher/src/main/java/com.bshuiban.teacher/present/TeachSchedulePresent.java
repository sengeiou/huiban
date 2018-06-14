package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ClassScheduleBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.TeachScheduleContract;

/**
 * Created by xinheng on 2018/6/8.<br/>
 * describe：老师课表
 */
public class TeachSchedulePresent extends BasePresent<TeachScheduleContract.View>implements TeachScheduleContract.Present {
    public TeachSchedulePresent(TeachScheduleContract.View baseView) {
        super(baseView);
    }

    @Override
    public void loadSchedule() {
        askInternet("getCourseListByTid","{\"userId\":\""+ User.getInstance().getUserId()+"\"}", new RetrofitService.CallResult<ClassScheduleBean>(ClassScheduleBean.class) {
            @Override
            protected void success(ClassScheduleBean classScheduleBean) {
                if(isEffective()){
                    view.updateView(classScheduleBean.getData());
                }
            }

            @Override
            protected void error(String error) {
                view.fail(error);
            }
        });
    }
}
