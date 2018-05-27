package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.contract.ErrorHomeworkAnalysisContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：
 */
public class ErrorHomeworkAnalysisPresent extends BasePresent<ErrorHomeworkAnalysisContract.View> implements ErrorHomeworkAnalysisContract.Present{
    public ErrorHomeworkAnalysisPresent(ErrorHomeworkAnalysisContract.View baseView) {
        super(baseView);
    }

    /**
     * 删除错题
     * @param examId 题目id
     */
    public void deleteErrorHomework(int examId) {//{"userId":"","id":}
        askInternet("deleteWrongQuestion", "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"id\":" + examId + "}", new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                view.deleteErrorHomeworkSuccess();
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
