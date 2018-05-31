package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：
 */
public interface SendNoticeContract {
    interface View extends BaseView{
        void updateClassList(String msg);
        void sendNoticeSuccess();
    }
    interface Present{
        void loadClassList();
        void sendNotice(String classIds,String content);
    }
}
