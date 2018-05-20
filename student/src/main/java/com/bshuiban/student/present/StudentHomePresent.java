package com.bshuiban.student.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
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
    public void getUserDataForInernet() {
        //String userId = User.getInstance().getUserId();
        String userId = "2030246";
        call=RetrofitService.getInstance().getServiceResult("getUserInfo","{\"userId\":\""+ userId +"\"}", new RetrofitService.CallResult<StudentUser>(StudentUser.class) {
            @Override
            protected void success(StudentUser studentUser) {
                if(isEffective()) {
                    StudentUser.DataBean data = studentUser.getData();
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
