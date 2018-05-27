package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/26.<br/>
 * describe：
 */
public interface ErrorFilterContract {
    interface View extends BaseView{
        void updateView(String json);
    }
    interface Present{
         void loadFilter(String key,String json);
    }

}
