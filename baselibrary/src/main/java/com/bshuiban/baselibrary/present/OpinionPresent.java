package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.OpinionContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinheng on 2018/5/23.<br/>
 * describe：
 */
public class OpinionPresent extends BasePresent<OpinionContract.View> implements OpinionContract.Present {
    public OpinionPresent(OpinionContract.View view) {
        super(view);
    }

    @Override
    public void sendOpinion(String content) {
        askInternet("commitHbFeedBack", getJsonMap(content, null), new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()){
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
    private Map<String,Object> getJsonMap(String content, String images){
        Map<String,Object> map=new HashMap<>(3);
        map.put("userId", User.getInstance().getUserId());
        map.put("content",content);
        map.put("pics",images);
        return map;
    }
}
