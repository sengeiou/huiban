package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.contract.ListContract;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public interface MainWeiClassContract {
    interface View extends ListContract.View{
        void toNextPage(Class<?> cls);
    }
    interface Present{
        void loadWeiClassData();
    }
}
