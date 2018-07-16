package com.bshuiban.baselibrary.present;

import android.support.annotation.StringDef;

import com.bshuiban.baselibrary.contract.SendNoticeContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.TeachClassBean;
import com.bshuiban.baselibrary.model.User;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：发消息
 */
public class SendNoticePresent extends BasePresent<SendNoticeContract.View> implements SendNoticeContract.Present {
    public static final String SEND_NOTICE="addClassNotices";
    public static final String SEND_ACTIVITY="addClassActivities";
    @Retention(RetentionPolicy.SOURCE)
    //这里指定int的取值只能是以下范围
    @StringDef({SEND_NOTICE, SEND_ACTIVITY})
    @interface SendNoticeKey {
    }
    public SendNoticePresent(SendNoticeContract.View view) {
        super(view);
    }

    @Override
    public void loadClassList() {//{"userId":""}
        List<TeachClassBean.DataBean> teachClassData = User.getInstance().getTeachClassData();
        if(HomeworkBean.isEffictive(teachClassData)){
            if(isEffective()){
                view.updateClassList(teachClassData);
            }
            return;
        }
        askInternet("getClassTeacherNowC","{\"userId\":\""+ User.getInstance().getUserId()+"\"}",new RetrofitService.CallResult<TeachClassBean>(TeachClassBean.class){

            @Override
            protected void success(TeachClassBean teachClassBean) {
                if(isEffective()){
                    view.updateClassList(teachClassBean.getData());
                    User.getInstance().setTeachClassData(teachClassBean.getData());
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

    @Override
    public void sendNotice(@SendNoticeKey String key,String classIds, String content) {//{"userId":"","classIds":"","content":""}
        //"addClassNotices"
        askInternet(SEND_NOTICE, "{\"userId\":\""+User.getInstance().getUserId()+"\",\"classIds\":\""+classIds+"\",\"content\":\""+content+"\"}", new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
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
