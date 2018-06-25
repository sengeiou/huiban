package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.contract.ParentConfirmContract;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public class ParentConfirmPresent extends com.bshuiban.baselibrary.present.ParentConfirmPresent {
    public ParentConfirmPresent(ParentConfirmContract.View view) {
        super(view);
    }

    @Override
    protected String getKey() {
        return "getHBParConSubStatus";
    }
}
