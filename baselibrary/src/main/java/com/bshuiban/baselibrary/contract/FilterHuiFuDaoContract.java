package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/7/3.<br/>
 * describe：慧辅导过滤
 */
public interface FilterHuiFuDaoContract {
    interface View extends BaseView{

        void loadScreeningData(String json);
    }
    interface Present{
        void getScreeningData(String json);

        void getClassTypeUse(String json);
    }
}
