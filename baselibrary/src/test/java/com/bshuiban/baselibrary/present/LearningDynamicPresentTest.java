package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.view.fragment.LearningDynamicFragment;

import org.junit.Test;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：
 */
public class LearningDynamicPresentTest {

    @Test
    public void sakInternetForData() throws InterruptedException {
        LearningDynamicPresent l=new LearningDynamicPresent(new LearningDynamicFragment());
        l.askInternetForLearningDynamicData("2030246",0,1000);
        Thread.sleep(2000);
    }
}