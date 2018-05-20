package com.bshuiban.baselibrary.present;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public class HomePageParentTest {

    @Test
    public void getMessageList() throws InterruptedException {
        new HomePageParent(null).getMessageList("2030246");
        Thread.sleep(3000);
    }
}