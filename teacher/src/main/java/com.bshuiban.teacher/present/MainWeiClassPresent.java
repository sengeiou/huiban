package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.present.ListPresent;
import com.bshuiban.teacher.contract.MainWeiClassContract;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public class MainWeiClassPresent extends ListPresent<MainWeiClassContract.View> implements  MainWeiClassContract.Present{
    public MainWeiClassPresent(MainWeiClassContract.View view) {
        super(view);
    }

    @Override
    public void loadWeiClassData() {

    }
}
