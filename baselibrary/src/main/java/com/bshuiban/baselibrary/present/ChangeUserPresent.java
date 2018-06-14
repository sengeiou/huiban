package com.bshuiban.baselibrary.present;

import android.text.TextUtils;

import com.bshuiban.baselibrary.contract.ChangeUserContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.model.UserMoreBean;

import java.util.List;

/**
 * Created by xinheng on 2018/6/12.<br/>
 * describe：
 */
public class ChangeUserPresent extends BasePresent<ChangeUserContract.View> implements ChangeUserContract.Present {
    public ChangeUserPresent(ChangeUserContract.View view) {
        super(view);
    }

    @Override
    public void loadMoreUserData() {
        String phone= User.getInstance().getUserData().getMobile();
        if(TextUtils.isEmpty(phone)){
            if (isEffective()){
                view.fail("暂无其他身份");
            }
            return;
        }
        //phone="13426480529";
        askInternet("getUserRoleByMobile","{\"mobile\":\""+phone+"\"}", new RetrofitService.CallResult<UserMoreBean>(UserMoreBean.class) {
            @Override
            protected void success(UserMoreBean userMoreBean) {
                if(isEffective()){
                    List<UserMoreBean.DataBean> data = userMoreBean.getData();
                    view.updateView(data);
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }
}
