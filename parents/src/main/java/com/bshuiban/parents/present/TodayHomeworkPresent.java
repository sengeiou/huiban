package com.bshuiban.parents.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.parents.contract.TodayHomeworkContract;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public class TodayHomeworkPresent extends BasePresent<TodayHomeworkContract.View> implements TodayHomeworkContract.Present {
    public TodayHomeworkPresent(TodayHomeworkContract.View view) {
        super(view);
    }

    @Override
    public void loadTodayHomework() {//{"userId":"","type":}
        askInternet("getHBParTodayWork", "{\"userId\":\""+ User.getInstance().getUserId()+"\"}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateListView(1,msg);
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
}
