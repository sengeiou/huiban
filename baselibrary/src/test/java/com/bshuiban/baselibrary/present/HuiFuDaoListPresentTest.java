package com.bshuiban.baselibrary.present;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：
 */
public class HuiFuDaoListPresentTest {

    @Test
    public void guessWhatYouThink() throws InterruptedException {
        HuiFuDaoListPresent huiFuDaoListPresent = new HuiFuDaoListPresent(null);
        //huiFuDaoListPresent.guessWhatYouThink("1");//1 语文
        huiFuDaoListPresent.getAllSubject();//{"subjectId":"1"}
        //huiFuDaoListPresent.getScreeningData("{\"subjectId\":\"1\"}");
        huiFuDaoListPresent.getScreeningData("{\"subjectId\":\"1\",\"organId\":\"27\"}");
        Thread.sleep(2000);
    }
}