package com.bshuiban.teacher.present;


import com.bshuiban.baselibrary.contract.ParentConfirmSubjectContract;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：老师端 家长确认学科详情列表
 */
public class ParentConfirmSubjectPresent extends com.bshuiban.baselibrary.present.ParentConfirmSubjectPresent {
    public ParentConfirmSubjectPresent(ParentConfirmSubjectContract.View view) {
        super(view);
    }

    @Override
    protected String getKey() {
        return "getHBParConSubInfo";
    }
}
