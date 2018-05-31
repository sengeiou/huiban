package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ConsolidationContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;

/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：
 */
public class ConsolidationPresent extends BasePresent<ConsolidationContract.View>implements ConsolidationContract.Present {

    public ConsolidationPresent(ConsolidationContract.View view) {
        super(view);
    }

    @Override
    public void loadConsolidationHomeworkData(int examId) {//{"examId":}
        askInternet("getPraticeQuestionList","{\"examId\":"+examId+"}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateView(msg);
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
    public void commitData(String json) {
        askInternet("addWrongQuestion", json, new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()){
                    view.fail("提交成功");
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
