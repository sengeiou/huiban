package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.HomePageContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：
 */
public class HomePageParent extends BasePresent<HomePageContract.View> implements HomePageContract.Parent {
    public HomePageParent(HomePageContract.View view) {
        super(view);
    }

    private String getJsonString(String userId) {
        return "{\"userId\":\""+userId+"\"}";
    }

    /**
     * 今日课表
     */
    public void getTodaySchedule(String userId){
        askInternet("getTodayCourseListBySid", getJsonString(userId), new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective())
                view.updateTodayClassSchedule(msg);
            }

            @Override
            protected void fail(String error) {
                if(isEffective())
                    view.fail(error);
            }
        });
    }

    /**
     * 获取慧辅导数据
     */
    public void getHuiFuDaoTwoData(){//{"index":0,"limit":2}
        askInternet("getHBCourseList", "{\"index\":0,\"limit\":2}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()) {
                    view.updateHuiFuDao(msg);
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
    public void getMessageList(String userId){//{"userId":""."start":,"limit":10}
        askInternet("getUserLeaveList", "{\"userId\":\"" + userId + "\",\"start\":0,\"limit\":10}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateMessageList(msg);
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
    public void delete(String messageId, String pid) {
        String json;
        if(null==pid){
            String userId = User.getInstance().getUserId();
            json="{\"userId\":\""+userId+"\",\"messageId\":\""+messageId+"\"}";
        }else{
            json="{\"messageId\":\""+messageId+"\",\"pid\":\""+pid+"\"}";
        }
        askInternet("delComment", json, new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    getMessageList(User.getInstance().getUserId());
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
    public void addRecevier(String json) {
        askInternet("addLeaveMessage", json, new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    getMessageList(User.getInstance().getUserId());
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

    public void test(){
        RetrofitService.getInstance().getServiceResult("getTodayCourseListBySid",getJsonString("2030246"),new RetrofitService.CallTest());
    }
}
