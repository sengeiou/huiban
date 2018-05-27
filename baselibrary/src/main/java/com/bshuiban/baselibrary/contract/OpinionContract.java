package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/23.<br/>
 * describe：
 */
public interface OpinionContract {
    interface View extends BaseView{
        void sendSuccess();
    }
    interface Present{
        void sendOpinion(String content);
    }
}
