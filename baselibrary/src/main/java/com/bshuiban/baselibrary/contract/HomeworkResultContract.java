package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：
 */
public interface HomeworkResultContract  {
    interface View extends BaseView{
        void commitSuccess();
    }
    interface Present{
        void commitService(int prepareId,int workId);
        void saveHomework(int prepareId,int workId);
    }

}
