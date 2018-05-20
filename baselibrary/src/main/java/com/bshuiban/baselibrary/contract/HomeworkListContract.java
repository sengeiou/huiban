package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public interface HomeworkListContract {
    interface View extends ListContract.View{

    }
    interface Present{
        void getHomework(boolean tag);
    }
}
