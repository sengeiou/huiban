package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ClassActivityContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ClassActivityBean;
import com.bshuiban.baselibrary.model.ClassScheduleBean;
import com.bshuiban.baselibrary.model.ResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：
 */
public class ClassActivityPresent extends BasePresent<ClassActivityContract.View>implements ClassActivityContract.Present {
    private boolean deleteTag;

    public ClassActivityPresent(ClassActivityContract.View view) {
        super(view);
    }

    @Override
    public void askInternetForClassActivityData(String classId, int start, int limit) {
        RetrofitService.getInstance().getServiceResult("getClassMessageList",getJsonString(classId,start,limit), new RetrofitService.CallResult<ClassActivityBean>(ClassActivityBean.class) {
            @Override
            protected void success(ClassActivityBean classActivityBean) {
                if(isEffective()) {
                    List<ClassActivityBean.DataBean> data = classActivityBean.getData();
                    view.updateViewForData(data);
                    if(deleteTag){
                        deleteTag=false;
                    }
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                    if(deleteTag){
                        view.updateViewForData(new ArrayList<ClassActivityBean.DataBean>());
                        deleteTag=false;
                    }
                }
            }
        });
    }

    @Override
    public String getJsonString(String classId, int start, int limit) {
        return "{\"classId\":\""+classId+"\",\"start\":"+start+",\"limit\":"+limit+"}";
    }

    @Override
    public void deleteActivity(String classId, String activityId) {//"":"","":""
        askInternet("delClassActivities", "{\"classId\":\""+classId+"\",\"id\":\""+activityId+"\"}", new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()) {
                    view.refresh();
                    deleteTag=true;
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
