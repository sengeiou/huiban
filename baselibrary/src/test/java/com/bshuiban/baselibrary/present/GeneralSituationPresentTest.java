package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.view.fragment.GeneralSituationFragment;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xinheng on 2018/5/8.<br/>
 * describe：
 */
public class GeneralSituationPresentTest {

    @Test
    public void askInterNetForData() {
        GeneralSituationFragment loginActivity=new GeneralSituationFragment(){
            @Override
            public String getClassId() {
                return "3000153";
            }

            @Override
            public String getUserId() {
                return "2030246";
            }
        };
        GeneralSituationPresent loginPresent=new GeneralSituationPresent(loginActivity);
        loginPresent.askInterNetForData();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}