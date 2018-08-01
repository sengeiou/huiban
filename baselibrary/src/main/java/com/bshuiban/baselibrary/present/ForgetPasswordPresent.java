package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ForgetPasswordContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
        /*askInternet("getVcode", "{\"mobile\":\"" + phone + "\"}", new RetrofitService.CallTest() {
            @Override
            protected void result(String s) {
                if(null!=s){
                    JsonElement parse = new JsonParser().parse(s);
                    if(null!=parse){
                        JsonObject asJsonObject = parse.getAsJsonObject();
                        String code = asJsonObject.get("code").getAsString();
                        if(ResultBean.isSuccess(code)){
                            if (isEffective()){
                                view.sendSuccess();
                            }
                        }else{

                        }
                    }
                }
            }
        });*/
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
                    view.fail(error);
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
