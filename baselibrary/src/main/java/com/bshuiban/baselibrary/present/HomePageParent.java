package com.bshuiban.baselibrary.present;

import android.text.TextUtils;

import com.bshuiban.baselibrary.contract.HomePageContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.MessageBean;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：
 */
public class HomePageParent extends ListPresent<HomePageContract.View> implements HomePageContract.Parent {
    private static final String userId = User.getInstance().getUserId();
    private List<MessageBean.DataBean> dataBeans;

    public HomePageParent(HomePageContract.View view) {
        super(view);
    }

    private String getJsonString(String userId) {
        return "{\"userId\":\"" + userId + "\"}";
    }

    @Override
    public void getInterNetData() {
        getMessageList(userId);
    }

    /**
     * 今日课表
     */
    public void getTodaySchedule(String userId) {
        askInternet("getTodayCourseListBySid", getJsonString(userId), new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if (isEffective())
                    view.updateTodayClassSchedule(msg);
            }

            @Override
            protected void fail(String error) {
                if (isEffective())
                    view.fail("今日课表结果："+error);
            }
        });
    }

    /**
     * 获取慧辅导数据
     */
    public void getHuiFuDaoTwoData() {//{"index":0,"limit":2}
        askInternet("getHBCourseList", "{\"index\":0,\"limit\":2}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if (isEffective()) {
                    view.updateHuiFuDao(msg);
                }
            }

            @Override
            protected void fail(String error) {
                if (isEffective()) {
                    view.fail("慧辅导："+error);
                }
            }
        });
    }

    @Override
    public void getMessageList(String userId) {//{"userId":""."start":,"limit":10}
        askInternet("getUserLeaveList", "{\"userId\":\"" + userId + "\",\"start\":" + start + ",\"limit\":" + limit + "}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if (isEffective()) {
                    view.updateMessageList(msg);
                }
                if (!TextUtils.isEmpty(msg)) {
                    dataBeans = gson.fromJson(msg, new TypeToken<List<MessageBean.DataBean>>() {
                        }.getType());
                }
            }

            @Override
            protected void fail(String error) {
                if (isEffective()&&error!=null&&!error.contains("暂无数据")) {
                    view.fail("留言："+error);
                }
            }
        });
    }

    @Override
    public void delete(String messageId, String pid) {
        String key;
        String json;
        if (null == pid) {
            key = "delComment";
            json = "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"messageId\":\"" + messageId + "\"}";
        } else {
            key = "delCommentReply";
            json = "{\"pid\":\"" + pid + "\",\"messageId\":\"" + messageId + "\"}";
        }
        askInternet(key, json, new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if (isEffective()) {
                    getMessageList(User.getInstance().getUserId());
                }
            }

            @Override
            protected void fail(String error) {
                if (isEffective()) {
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
                if (isEffective()) {
                    getMessageList(User.getInstance().getUserId());
                }
            }

            @Override
            protected void fail(String error) {
                if (isEffective()) {
                    view.fail(error);
                }
            }
        });
    }

    @Override
    public void getReplyMessage(int index) {
        if(isEffective()&&null!=dataBeans&&dataBeans.size()>index){
            view.startReplyDialog(dataBeans.get(index));
        }
    }

    public void test() {
        RetrofitService.getInstance().getServiceResult("getTodayCourseListBySid", getJsonString("2030246"), new RetrofitService.CallTest());
    }
}
