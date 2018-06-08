package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ParentConfirmContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public abstract class ParentConfirmPresent extends BasePresent<ParentConfirmContract.View> implements ParentConfirmContract.Present {
    public ParentConfirmPresent(ParentConfirmContract.View view) {
        super(view);
    }
    @Override
    public void loadListData() {
        askInternet(getKey(), "{\"userId\":\"" + User.getInstance().getUserId() + "\"}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateList(msg);
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
