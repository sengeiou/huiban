package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.LessonInfContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.JsonArray;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：课程详情
 */
public class LessonInfPresent extends BasePresent<LessonInfContract.View> implements LessonInfContract.Present {

    public LessonInfPresent(LessonInfContract.View view) {
        super(view);
    }

    @Override
    public void getLessonInf(String courseId) {
       call = RetrofitService.getInstance().getServiceResult("getCourseDetail", "{\"userId\":\"" + User.getInstance().getReallyUserId() + "\",\"courseId\":\"" + courseId + "\"}", new RetrofitService.CallHTML() {

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

    RetrofitService.CallResult<ResultBean> callback = new RetrofitService.CallResult<ResultBean>(ResultBean.class) {

        @Override
        protected void success(ResultBean resultBean) {
            if (isEffective()) {
                view.collectSuccess(String.valueOf(tag));
            }
        }

        @Override
        protected void error(String error) {
            if (isEffective()) {
                view.fail(error);
            }
        }
    };
    private int tag;
    private String getUserId(){
        if(User.getInstance().getUserType()==4){
            return User.getInstance().getUserData().getParentsId();
        }else {
            return User.getInstance().getUserId();
        }
    }
    @Override
    public void addCollect(String courseId) {//{"courseId":,"userId",""}
        tag=1;
        askInternet("addUserCollect", "{\"courseId\":" + courseId + ",\"userId\":\"" + User.getInstance().getReallyUserId() + "\"}", callback);
    }

    @Override
    public void deleteCollect(String courseId) {
        tag=0;
        askInternet("deleteUserCollect", "{\"courseId\":" + courseId + ",\"userId\":\"" + User.getInstance().getReallyUserId() + "\"}", callback);
    }
}
