package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public class HomePageParentTest {

    @Test
    public void getMessageList() throws InterruptedException {
        LoginResultBean loginResultBean = new Gson().fromJson("{\"data\":{\"userId\":\"2030246\",\"userType\":1,\"realName\":\"戴帅帅\",\"icoPath\":\"\",\"schoolId\":\"45\",\"schoolName\":\"青岛实验初级中学\",\"classId\":[\"3000153\"],\"gradeId\":\"8\",\"otherId\":\"2030246\",\"vipDays\":0},\"code\":\"2002\",\"msg\":\"success\"}", LoginResultBean.class);
        User.getInstance().setData(loginResultBean.getData());
        new HomePageParent(null).getMessageList("2030246");
        Thread.sleep(3000);
    }
}