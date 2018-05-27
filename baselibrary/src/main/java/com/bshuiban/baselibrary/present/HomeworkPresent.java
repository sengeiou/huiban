package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.HomeworkListData;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：作业详情
 */
public class HomeworkPresent<V extends BaseView> extends BasePresent<V> {
    /**
     * 取统计传1，做作业页面传0
     */
    protected int flag;
    public HomeworkPresent(V v) {
        super(v);
    }
    protected void loadHomeworkInfData(int workId,int wtype) {
        askInternet("getStuWorkInfo",getJsonMap(workId,wtype), new RetrofitService.CallResult<HomeworkInfBean>(HomeworkInfBean.class) {
            @Override
            protected void success(HomeworkInfBean homeworkInfBean) {
                if(!isEffective()){
                    return;
                }
                HomeworkInfBean.DataBean data = homeworkInfBean.getData();
                User.getInstance().setHomeworkInfBean(data);
                updateHomeworkView(data);
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }
    protected void updateHomeworkView(HomeworkInfBean.DataBean dataBean){}
    private Map<String,Object> getJsonMap(int workId, int wtype){
        Map<String,Object>map=new HashMap<>();
        map.put("studentId", User.getInstance().getUserId());
        map.put("workId",workId);
        map.put("wtype",wtype);
        map.put("roleId",User.getInstance().getUserData().getUserType());//用户身份(1学生，4家长)
        map.put("classId",User.getInstance().getClassId());
        map.put("flag",flag);
        return map;
    }
}
