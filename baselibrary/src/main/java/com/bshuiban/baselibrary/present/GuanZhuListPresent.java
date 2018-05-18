package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.GuanZhuListContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.utils.TextUtils;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：列表
 */
public class GuanZhuListPresent extends ListPresent<GuanZhuListContract.View> implements GuanZhuListContract.Present {
    public GuanZhuListPresent(GuanZhuListContract.View view) {
        super(view);
    }

    @Override
    public void getSearchData(String msg) {

    }

    @Override
    public void guanZhu(String key,String json) {
        //deleteUserAttention
        //addUserAttention
        RetrofitService.getInstance().getServiceResult(key, json, new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective())
                    view.guanZhuResult();
            }

            @Override
            protected void error(String error) {
                if(isEffective())
                    view.fail(error);
            }
        });
    }

    @Override
    public void getInterNetData() {
        //获取已关注列表
        call = RetrofitService.getInstance().getServiceResult("getMyFollows",TextUtils.getUserIdListJson(User.getInstance().getUserId(),start,limit), callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        view.updateList(json);
    }
}
