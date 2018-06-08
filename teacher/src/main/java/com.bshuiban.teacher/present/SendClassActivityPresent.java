package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.SendNoticeContract;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：发消息
 */
public class SendClassActivityPresent extends BasePresent<SendNoticeContract.View> implements SendNoticeContract.Present {
    public SendClassActivityPresent(SendNoticeContract.View view) {
        super(view);
    }

    @Override
    public void loadClassList() {//{"userId":""}addClassActivities
        askInternet("getClassTeacherNowC","{\"userId\":\""+ User.getInstance().getUserId()+"\"}",new RetrofitService.CallHTML(){

            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateClassList(msg);
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

    @Override
    public void sendNotice(String classIds, String content) {//{"userId":"","classIds":"","content":""}
        askInternet("addClassNotices", "{\"userId\":\""+User.getInstance().getUserId()+"\",\"classIds\":\""+classIds+"\",\"content\":\""+content+"\"}", new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()){
                    view.sendNoticeSuccess();
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
