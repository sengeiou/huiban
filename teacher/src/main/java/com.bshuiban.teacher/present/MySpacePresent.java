package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.ListPresent;
import com.bshuiban.teacher.contract.MySpaceContract;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：我的空间
 */
public class MySpacePresent extends ListPresent<MySpaceContract.View> implements MySpaceContract.Present {
    /**
     * 访问的用户id
     */
    private String userId;
    public MySpacePresent(MySpaceContract.View view,String usrId) {
        super(view);
        this.userId=usrId;
    }

    @Override
    public void loadSpaceHeadData() {
        askInternet("getHBUserDetail", "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"masterId\":\"" + userId + "\"}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateSpaceHeadView(msg);
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

    @Override
    public void loadMessageListInf() {
        getInterNetData();
    }

    @Override
    public void getInterNetData() {
        askInternet("","",callHTMLJsonArray);
    }
}
