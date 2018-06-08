package com.bshuiban.present;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xinheng on 2018/6/4.<br/>
 * describe：
 */
public class LoginPresentTest {

    @Test
    public void login() {
        LoginPresent loginPresent=new LoginPresent(null);
        loginPresent.askInternet("","{\"userId\":\"2030246\",\"userPwd\":\"111111\"}");

    }
}