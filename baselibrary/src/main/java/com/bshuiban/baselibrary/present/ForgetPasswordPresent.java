package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ForgetPasswordContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;

/**
 * Created by xinheng on 2018/7/3.<br/>
 * describe：忘记密码
 */
public class ForgetPasswordPresent extends BasePresent<ForgetPasswordContract.View> implements ForgetPasswordContract.Present {
    public ForgetPasswordPresent(ForgetPasswordContract.View view) {
        super(view);
    }

    @Override
    public void loadSendCode(String phone) {//{"mobile":""}
        askInternet("getVcode", "{\"mobile\":\""+phone+"\"}", new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if (isEffective()){
                    view.sendSuccess();
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail("验证码-"+error);
                }
            }
        });
    }

    @Override
    public void loadChangePassword(String phone, String code, String password) {//{"mobile":"","vcode":"","password":""}
        askInternet("resetPwdByTel","{\"mobile\":\""+phone+"\",\"vcode\":\""+code+"\",\"password\":\""+password+"\"}",new RetrofitService.CallResult<ResultBean>(ResultBean.class){
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()){
                    view.changeSuccess();
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
