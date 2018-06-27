package com.bshuiban.parents.present;

import android.util.Log;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.parents.contract.ParentsHomeContract;
import com.bshuiban.parents.modul.ParentsUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinheng on 2018/6/6.<br/>
 * describe：
 */
public class ParentsHomePresent extends BasePresent<ParentsHomeContract.View> implements ParentsHomeContract.Present {
    public ParentsHomePresent(ParentsHomeContract.View view) {
        super(view);
        User instance = User.getInstance();
        LoginResultBean.Data userData = instance.getUserData();
        String userId = userData.getUserId();
        userData.setParentsId(userId);
        List<LoginResultBean.Data.StuArrBean> stuArr = userData.getStuArr();
        if(HomeworkBean.isEffictive(stuArr)){
            LoginResultBean.Data.StuArrBean stuArrBean = stuArr.get(0);
            if(null!=stuArrBean){
                userData.setUserId(stuArrBean.getStuId());
                userData.setClassId1(stuArrBean.getClassId()+"");
                ArrayList<String> className = new ArrayList<>(1);
                className.add(stuArrBean.getClassName());
                userData.setClassName(className);
            }
        }
    }

    @Override
    public void getUserDataForInternet() {
        User instance = User.getInstance();
        if(null==instance){
            Log.e("TAG", "getUserDataForInternet: 错误");
            return;
        }
        //String userId = instance.getUserId();
        String userId = instance.getUserData().getParentsId();
        askInternet("getUserInfo","{\"userId\":\""+ userId +"\"}", new RetrofitService.CallResult<ParentsUser>(ParentsUser.class) {
            @Override
            protected void success(ParentsUser studentUser) {
                if(isEffective()) {
                    ParentsUser.DataBean data = studentUser.getData();
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
