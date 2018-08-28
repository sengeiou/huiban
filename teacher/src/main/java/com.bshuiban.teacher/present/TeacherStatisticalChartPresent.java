package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.contract.StatisticalChartContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.TeacherStatisticalChartContract;
import com.bshuiban.teacher.model.TeacherStatisticalChartBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinheng on 2018/8/9.<br/>
 * describe：
 */
public class TeacherStatisticalChartPresent extends BasePresent<TeacherStatisticalChartContract.View> implements TeacherStatisticalChartContract.Present{
    public TeacherStatisticalChartPresent(TeacherStatisticalChartContract.View view) {
        super(view);
    }

    @Override
    public void getStatisticalData(String subjectId, String timeSlot, String gradeId, int workType) {
        askInternet("getAccuracyOfClasses",getJsonString(subjectId, timeSlot, gradeId, workType), new RetrofitService.Call<TeacherStatisticalChartBean>(TeacherStatisticalChartBean.class) {
            @Override
            protected void result(TeacherStatisticalChartBean teacherStatisticalChartBean) {
                List<TeacherStatisticalChartBean.DataBean> data = teacherStatisticalChartBean.getData();
                if(isEffective()){
                    view.updateStatisticalChartView(data);
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
    private Map<String,Object> getJsonString(String subjectId, String timeSlot, String gradeId, int workType ){
        HashMap<String, Object> hashMap = new HashMap<>();
        User instance = User.getInstance();
        hashMap.put("schoolId", instance.getSchoolId());
        hashMap.put("subjectId",subjectId);
        hashMap.put("gradeId",gradeId);
        hashMap.put("timeSlot",timeSlot);
        hashMap.put("userId",instance.getUserId());
        hashMap.put("workType",workType);
        return hashMap;
    }
}
