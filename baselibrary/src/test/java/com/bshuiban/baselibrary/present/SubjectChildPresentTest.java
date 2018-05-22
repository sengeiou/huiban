package com.bshuiban.baselibrary.present;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xinheng on 2018/5/22.<br/>
 * describe：
 */
public class SubjectChildPresentTest {

    @Test
    public void loadStudyBottom() throws InterruptedException {
        new SubjectChildPresent(null).loadStudyBottom(2);
        Thread.sleep(2000);
    }

    @Test
    public void loadStudyReportData() throws InterruptedException {
        new SubjectChildPresent(null).loadStudyReportData();
        Thread.sleep(2000);

    }
}