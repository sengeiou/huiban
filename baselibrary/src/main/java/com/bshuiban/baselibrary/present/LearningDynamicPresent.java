package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.LearningDynamicContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.LearningDynamicBean;

/**
 * Created by xinheng on 2018/5/8.<br/>
 * describe：
 */
public class LearningDynamicPresent extends BasePresent<LearningDynamicContract.View> implements LearningDynamicContract.Present {

    public LearningDynamicPresent(LearningDynamicContract.View view) {
        super(view);
    }

    private String getJsonString(){
        return "";
    }

    @Override
    public void askInternetForLearningDynamicData(String userId, int start, int limit) {
        //call= RetrofitService.getInstance().getServiceResult("getUserStuDyMsg",getJsonString(userId,start,limit),new RetrofitService.CallTest());
        call= RetrofitService.getInstance().getServiceResult("getUserStuDyMsg",getJsonString(userId,start,limit), new RetrofitService.CallResult<LearningDynamicBean>(LearningDynamicBean.class) {
            @Override
            protected void success(LearningDynamicBean learningDynamicBean) {
                if(isEffective()){
                    view.updateViewForData(learningDynamicBean.getData());
                }
            }

            @Override
            protected void error(String error) {

            }
        });

    }

    @Override
    public String getJsonString(String userId,int start,int limit) {
        return "{\"userId\":\""+userId+"\",\"start\":"+start+",\"limit\":"+limit+"}";
    }
}
