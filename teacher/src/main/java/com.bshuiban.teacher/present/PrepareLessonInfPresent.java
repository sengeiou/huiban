package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.PrepareLessonInfContract;
import com.bshuiban.teacher.model.PrepareLessonBean;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：备课详情
 */
public class PrepareLessonInfPresent extends BasePresent<PrepareLessonInfContract.View> implements PrepareLessonInfContract.Present {
    public PrepareLessonInfPresent(PrepareLessonInfContract.View view) {
        super(view);
    }

    @Override
    public void loadPrepareLessonInf(int wtype, String preId) {//{"wtype":,"preId":""}
        askInternet("getHBPreReleaseClass","{\"type\":"+wtype+",\"preId\":\""+preId+"\"}", new RetrofitService.CallResult<PrepareLessonBean>(PrepareLessonBean.class) {
            @Override
            protected void success(PrepareLessonBean prepareLessonBean) {
                if(isEffective()){
                    view.updateLessonInf(prepareLessonBean);
                }
            }

            @Override
            protected void error(String error) {
                if (isEffective()){
                    view.fail(error);
                }
            }
        });
    }
}
