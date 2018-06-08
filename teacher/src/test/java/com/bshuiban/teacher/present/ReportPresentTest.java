package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xinheng on 2018/6/4.<br/>
 * describe：
 */
public class ReportPresentTest {
    @Test
    public void testIntent() throws InterruptedException {
        RetrofitService.getInstance().getServiceResult("getGradeBySiteId","{\"siteId\":\"45\"}",new RetrofitService.CallTest());
        Thread.sleep(4000);
    }

}