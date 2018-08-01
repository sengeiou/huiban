package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.TeachClassContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.TeachClassBean;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/6/14.<br/>
 * describe：
 */
public class TeachClassPresent extends BasePresent<TeachClassContract.View> implements TeachClassContract.Present {
    public TeachClassPresent(TeachClassContract.View view) {
        super(view);
    }

    @Override
    public void loadTeachClass() {
        askInternet("getClassTeacherNowC","{\"userId\":\""+ User.getInstance().getUserId()+"\"}",new RetrofitService.CallResult<TeachClassBean>(TeachClassBean.class){

            @Override
            protected void success(TeachClassBean teachClassBean) {
                if(isEffective()){
                    view.updateData(teachClassBean.getData());
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    //view.fail(error);
                    view.updateData(null);
                }
            }
        });
    }
}
