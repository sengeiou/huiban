package com.bshuiban.baselibrary.present;

import android.util.Log;

import com.bshuiban.baselibrary.contract.LoginContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：登录P层
 */
public class LoginPresent extends BasePresent<LoginContract.View> implements LoginContract.Present {

    public LoginPresent(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String accountNumber, String password) {
        //call = RetrofitService.getInstance().getServiceResult("logInByUidAndPwd", getMapJson(accountNumber, password), new RetrofitService.CallTest());
        call = RetrofitService.getInstance().getServiceResult("logInByUidAndPwd", getMapJson(accountNumber, password), new RetrofitService.CallResult<LoginResultBean>(LoginResultBean.class) {
            @Override
            protected void success(LoginResultBean loginResultBean) {
                Log.e(TAG, "success: "+(new Gson().toJson(loginResultBean)));
                LoginResultBean.Data data = loginResultBean.getData();
                List<Integer> classId = data.getClassId();
                List<String> className = data.getClassName();
                int gradeId = data.getGradeId();
                int userId = data.getUserId();
                int userType = data.getUserType();
                String otherId = data.getOtherId();
                String schoolName = data.getSchoolName();
                int schoolId = data.getSchoolId();
//                if (isEffective()) {
//                    view.dismissDialog();
//                    view.loginSuccessToNextActivity(getNextActivity(userType));
//                }
            }

            @Override
            protected void error(String t) {
                if (isEffective()) {
                    view.dismissDialog();
                    view.fail(t);
                }
            }
        });
    }

    @Override
    public Map<String, Object> getMapJson(String accountNumber, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", accountNumber);
        map.put("userPwd", password);
        map.put("terminal", "7");//登录终端7慧班学生,8慧班老师,9慧班家长
        return map;
    }

    @Override
    public Class<?> getNextActivity(int userType) {
        Class<?> classes=null;
        try {
            switch (userType) {//1学生、2老师、3管理者、4家长，
                case 1:
                    classes = Class.forName("com.bshuiban.student.view.activity.StudentHomeActivity");
                    break;
                case 2:
                    classes = Class.forName("com.bshuiban.student.view.activity.TeacherHomeActivity");
                    break;
                case 4:
                    classes = Class.forName("com.bshuiban.student.view.activity.ParentHomeActivity");
                    break;
                default:
                    //classes = null;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
