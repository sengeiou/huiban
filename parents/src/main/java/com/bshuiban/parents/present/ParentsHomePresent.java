package com.bshuiban.parents.present;

import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.parents.contract.ParentsHomeContract;

/**
 * Created by xinheng on 2018/6/6.<br/>
 * describe：
 */
public class ParentsHomePresent extends BasePresent<ParentsHomeContract.View> implements ParentsHomeContract.Present {
    public ParentsHomePresent(ParentsHomeContract.View view) {
        super(view);
    }

    @Override
    public void getUserDataForInternet() {

    }
}
