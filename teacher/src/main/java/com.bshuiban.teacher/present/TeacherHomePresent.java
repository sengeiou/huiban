package com.bshuiban.teacher.present;

import android.util.Log;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.TeacherHomeContract;
import com.bshuiban.teacher.model.TeacherUser;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：老师首页
 */
public class TeacherHomePresent extends BasePresent<TeacherHomeContract.View> implements TeacherHomeContract.Present{
    public TeacherHomePresent(TeacherHomeContract.View view) {
        super(view);
    }

    @Override
    public void getUserDataForInternet() {
        User instance = User.getInstance();
        if(null==instance){
            Log.e("TAG", "getUserDataForInternet: 错误");
            return;
        }
        String userId = instance.getUserId();
        call=RetrofitService.getInstance().getServiceResult("getUserInfo","{\"userId\":\""+ userId +"\"}", new RetrofitService.CallResult<TeacherUser>(TeacherUser.class) {
            @Override
            protected void success(TeacherUser studentUser) {
                if(isEffective()) {
                    TeacherUser.DataBean data = studentUser.getData();
                    view.updateSlideView(data);
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
