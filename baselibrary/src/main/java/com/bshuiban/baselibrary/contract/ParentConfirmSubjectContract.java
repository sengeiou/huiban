package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public interface ParentConfirmSubjectContract {
    interface View extends BaseView{
        void updateView(int type,String json);
    }
    interface Present{
        void loadSubjectList(int subjectId,int type);
    }
}
