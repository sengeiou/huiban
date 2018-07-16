package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.TeachClassBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：
 */
public interface SendNoticeContract {
    interface View extends BaseView{
        void updateClassList(List<TeachClassBean.DataBean> dataBeanList);
        void sendNoticeSuccess();
    }
    interface Present{
        /**
         * 获取班级信息
         */
        void loadClassList();
        void sendNotice(String key, String classIds, String content);
    }
}
