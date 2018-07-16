package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/7/3.<br/>
 * describe：
 */
public interface ForgetPasswordContract {
    interface View extends BaseView{
        void changeSuccess();
        void sendSuccess();
    }
    interface Present{
        /**
         * 发送验证码
         * @param phone
         */
        void loadSendCode(String phone);

        /**
         * 更改密码
         * @param phone 手机号
         * @param code 验证码
         * @param password 密码
         */
        void loadChangePassword(String phone,String code,String password);
    }
}
