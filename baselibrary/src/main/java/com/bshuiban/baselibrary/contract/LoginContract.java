package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.LoginResultBean;

import java.util.Map;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：登录契约
 */
public interface LoginContract {
    interface View extends BaseView{
        /**
         * 获取用户输入的账号
         * @return
         */
        //String getUserAccountNumber();

        /**
         * 获取用户输入的密码
         * @return
         */
        //String getPassWord();
        void loginSuccessToNextActivity(Class<?> cls, LoginResultBean.Data loginData);
    }
    interface Present{
        /**
         * 登录
         * @param accountNumber 账号
         * @param password 密码
         */
        void login(String accountNumber, String password);
        Map<String,Object> getMapJson(String accountNumber, String password);

        /**
         * 获取下一个启动的Activity
         * @param userType 类型 1学生、2老师、3管理者、4家长，
         * @return
         */
        Class<?> getNextActivity(int userType);
    }
}
