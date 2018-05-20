package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.HomeworkListContract;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public class HomeworkListPresent extends ListPresent<HomeworkListContract.View> implements HomeworkListContract.Present {
    public HomeworkListPresent(HomeworkListContract.View view) {
        super(view);
    }

    @Override
    public void getInterNetData() {

    }

    public void reSetStart() {
        start=0;
    }

    @Override
    public void getHomework(boolean tag) {
        
        if(tag){

        }else{

        }
    }
}
