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
    private String search;
    public GuanZhuListPresent(GuanZhuListContract.View view) {
        super(view);
    }

    @Override
    public void getSearchData(String search) {//{"schoolId":""}
        this.search=search;
        start=0;
        clearArray();
        getInterNetData();
    }

    @Override
    public void getInterNetData() {
        if(!android.text.TextUtils.isEmpty(search)){
            //{"schoolId":"","start":,"limit":}
            String json = "{\"key\":\""+search+"\",\"schoolId\":\"" + User.getInstance().getSchoolId() + "\",\"start\":" + start + ",\"limit\":" + limit + "}";
            search=null;
            askInternet("getHBTeacherList", json ,callHTMLJsonArray);
        }else {
            //获取已关注列表
            call = RetrofitService.getInstance().getServiceResult("getMyFollows", TextUtils.getUserIdListJson(User.getInstance().getUserId(), start, limit), callHTMLJsonArray);
        }
    }
    @Override
    public void guanZhu(boolean tag, int attUserId) {
        String key=tag?"addUserAttention":"deleteUserAttention";
        //"userId":"","attUserId":
        askInternet(key, "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"attUserId\":" + attUserId + "}", new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()){
                    view.guanZhuResult(tag);
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
    public void updateView(String json) {
        view.updateList(json);
    }

}
