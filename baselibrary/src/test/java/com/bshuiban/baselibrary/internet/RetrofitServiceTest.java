package com.bshuiban.baselibrary.internet;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：
 */
public class RetrofitServiceTest {

    @Test
    public void getServiceResultForResString() {
        RetrofitService.getInstance().getServiceResultForResString("getCourseListByCid","{\"classId\":\"3000153\"}",new RetrofitService.CallResTest(){
            @Override
            protected void result(String s) {
                System.out.print(s);
            }
        });
    }
}