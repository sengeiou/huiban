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
        /**
         *
         * @param subjectId 科目id
         * @param type 课前、课后、课中 类型
         */
        void loadSubjectList(int subjectId,int type);
    }
}
