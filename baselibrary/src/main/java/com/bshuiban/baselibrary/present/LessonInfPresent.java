package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.LessonInfContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.JsonArray;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public class LessonInfPresent extends BasePresent<LessonInfContract.View> implements LessonInfContract.Present {

    public LessonInfPresent(LessonInfContract.View view) {
        super(view);
    }

    @Override
    public void getLessonInf(String courseId) {
        RetrofitService.getInstance().getServiceResult("getCourseDetail", "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"courseId\":\"" + courseId + "\"}", new RetrofitService.CallHTML() {

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
