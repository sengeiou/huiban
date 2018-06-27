package com.bshuiban.student.present;

import android.util.Log;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.student.contract.StudentHomeContract;
import com.bshuiban.student.model.StudentUser;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public class StudentHomePresent extends BasePresent<StudentHomeContract.View> implements StudentHomeContract.Present{
    public StudentHomePresent(StudentHomeContract.View view) {
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
        call=RetrofitService.getInstance().getServiceResult("getUserInfo","{\"userId\":\""+ userId +"\"}", new RetrofitService.CallResult<StudentUser>(StudentUser.class) {
            @Override
            protected void success(StudentUser studentUser) {
                if(isEffective()) {
                    StudentUser.DataBean data = studentUser.getData();
                    data.setDataContent(User.getInstance().getUserData());
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
