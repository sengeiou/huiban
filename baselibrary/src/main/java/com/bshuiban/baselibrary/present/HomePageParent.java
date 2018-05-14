package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.HomePageContract;
import com.bshuiban.baselibrary.internet.RetrofitService;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：
 */
public class HomePageParent extends BasePresent<HomePageContract.View> implements HomePageContract.Parent {
    public HomePageParent(HomePageContract.View view) {
        super(view);
    }

    @Override
    protected void initCallHtml() {
        callHTML=new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateView();
                }
            }

            @Override
            protected void fail(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        };
    }

    private String getJsonString(String userId) {
        return "{\"userId\":\""+userId+"\"}";
    }
    public void getTodaySchedule(){
        askInternet("getTodayCourseListBySid",getJsonString("2030246"));
    }
    public void test(){
        RetrofitService.getInstance().getServiceResult("getTodayCourseListBySid",getJsonString("2030246"),new RetrofitService.CallTest());
    }
}
