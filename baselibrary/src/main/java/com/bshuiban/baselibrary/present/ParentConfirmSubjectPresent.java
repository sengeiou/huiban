package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ParentConfirmSubjectContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public abstract class ParentConfirmSubjectPresent extends BasePresent<ParentConfirmSubjectContract.View> implements ParentConfirmSubjectContract.Present{
    public ParentConfirmSubjectPresent(ParentConfirmSubjectContract.View view) {
        super(view);
    }

    @Override
    public void loadSubjectList(int subjectId, int type) {//{"userId":"","":,"":}
        askInternet(getKey(), "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"subjectId\":" + subjectId + ",\"type\":" + type + "}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateView(type,msg);
                }
            }

            @Override
            protected void fail(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }

    protected abstract String getKey();
}
