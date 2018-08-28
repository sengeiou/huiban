package com.bshuiban.baselibrary.present;

import android.text.TextUtils;
import android.util.Log;

import com.bshuiban.baselibrary.contract.HomePageContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.MessageBean;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：
 */
public class HomePageParent extends ListPresent<HomePageContract.View> implements HomePageContract.Parent {
    private String userId ;
    private List<MessageBean.DataBean> dataBeans;
    /**
     * 回复留言数据第几条
     */
    private int dataIndex;
    private boolean refreshHuiFu;

    public HomePageParent(HomePageContract.View view) {
        super(view);
        userId=User.getInstance().getUserId();
        setLimit(3);
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
        String key;
        if(User.getInstance().isTeacher()){
            key="getTodayCourseListByTid";
        }else{
            key="getTodayCourseListBySid";
        }
        askInternet(key, getJsonString(userId), new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if (isEffective())
                    view.updateTodayClassSchedule(msg);
            }

            @Override
            protected void fail(String error) {
                if (isEffective())
                    view.fail("获取今日课表信息失败，请检查网络或重试");
            }
        });
    }

    /**
     * 获取慧辅导数据
     */
    public void getHuiFuDaoTwoData() {//{"index":0,"limit":2}，
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
    public void getMessageList(String userId) {//{"userId":""."start":,"limit":10} 限制3条
        Log.e("TAG", "getMessageList: "+start+", "+limit );
        askInternet("getUserLeaveList", "{\"userId\":\"" + userId + "\",\"start\":" + start + ",\"limit\":" + limit + "}",callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        view.updateMessageList(json);
        if (!TextUtils.isEmpty(json)) {
            dataBeans = gson.fromJson(json, new TypeToken<List<MessageBean.DataBean>>() {
            }.getType());
            //Log.e("TAG", "updateView: "+dataBeans.size() );
            if(refreshHuiFu&&dataBeans!=null&&dataBeans.size()>dataIndex) {
                refreshHuiFu=false;
                view.updateReplyDialog(dataBeans.get(dataIndex));
            }
        }
    }

    @Override
    public void fail(String error) {
        if(start>0&&error.contains("暂无数据")){
            error="没有更多数据了";
        }else {
            view.updateMessageList("[]");
        }
        view.fail("留言："+error);
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
                    //getMessageList(User.getInstance().getUserId());
                    refresh();
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
                    //getMessageList(User.getInstance().getUserId());
                    refreshHuiFu=true;
                    refresh();
                }
            }

            @Override
            protected void fail(String error) {
                refreshHuiFu=false;
                if (isEffective()) {
                    view.fail(error);
                }
            }
        });
    }

    @Override
    public void getReplyMessage(int index) {
        this.dataIndex=index;
        if(isEffective()&&null!=dataBeans&&dataBeans.size()>index){
            view.startReplyDialog(dataBeans.get(index));
        }
    }
}
