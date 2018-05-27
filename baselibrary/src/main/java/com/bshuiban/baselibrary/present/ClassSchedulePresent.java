package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ClassScheduleContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ClassScheduleBean;
import com.bshuiban.baselibrary.view.customer.ClassSchedule;
import com.bshuiban.baselibrary.view.fragment.ClassScheduleFragment;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：班级课表P层
 */
public class ClassSchedulePresent extends BasePresent<ClassScheduleContract.View> implements ClassScheduleContract.Present {

    public ClassSchedulePresent(ClassScheduleContract.View view) {
        super(view);
    }

    @Override
    public void askInternetForScheduleData(String classId) {
        call = RetrofitService.getInstance().getServiceResult("getCourseListByCid","{\"classId\":\""+classId+"\"}", new RetrofitService.CallResult<ClassScheduleBean>(ClassScheduleBean.class) {
            @Override
            protected void success(ClassScheduleBean classScheduleBean) {
                if(isEffective()){
                    view.updateSchedule(classScheduleBean.getData());
                }
            }

            @Override
            protected void error(String error) {
                view.fail(error);
            }
        });

    }
}
