package com.bshuiban.baselibrary.present;

import android.util.Log;

import com.bshuiban.baselibrary.contract.HomeworkListContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.SubjcetsBean;
import com.bshuiban.baselibrary.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public class HomeworkListPresent extends BasePresent<HomeworkListContract.View> implements HomeworkListContract.Present {
    private boolean tag=false;//待完成
    private ListPresent<HomeworkListContract.View> pendingComplete;
    private ListPresent<HomeworkListContract.View> complete;
    private int wtype;//作业类型(1课前2课中3课后)
    private int subjectId=-1;//0取待完成列表，传科目id取某科目的完成列表
    public HomeworkListPresent(HomeworkListContract.View view) {
        super(view);
        pendingComplete=new ListPresent<HomeworkListContract.View>(view){
            @Override
            public void getInterNetData() {
                askInternet("getWorkListByUidTid",getJsonMap(start,limit),callHTMLJsonArray);
            }

            @Override
            public void updateView(String json) {
                view.updateList(json);
            }
        };
        complete =new ListPresent<HomeworkListContract.View>(view){
            @Override
            public void getInterNetData() {
                askInternet("getWorkListByUidTid",getJsonMap(start,limit),callHTMLJsonArray);
            }

            @Override
            public void updateView(String json) {
                view.updateList(json);
            }
        };
    }

    public void reSetStart() {
        if(tag) {
            complete.start=0;
        }else{
            pendingComplete.start=0;
        }
    }
    public boolean getTAg(){
        return tag;
    }
    @Override
    public void loadMoreData() {
        if(tag){
            complete.loadMoreData();
        }else{
            pendingComplete.loadMoreData();
        }
    }

    @Override
    public void refresh() {
        if(tag){
            complete.refresh();
        }else{
            pendingComplete.refresh();
        }
    }

    @Override
    public void setTag(int subjectId) {
        Log.e("TAG", "setTag: "+subjectId+", old="+this.subjectId );
        if(this.subjectId!=subjectId||subjectId==0) {
            this.tag = subjectId != 0;
            this.subjectId = subjectId;
            refresh();
        }
    }

    @Override
    public void setTag(boolean tag) {
        this.tag=tag;
    }

    @Override
    public void loadSubjectList() {//{"classId":""}
        askInternet("getStuClassSub","{\"classId\":\""+User.getInstance().getClassId()+"\"}", new RetrofitService.CallHTML(){

            @Override
            protected void success(String msg) {
                if(isEffective()) {
                    view.updateSubjects(msg);
                }
            }

            @Override
            protected void fail(String error) {
                if(isEffective()){
                    view.fail(error);
                    view.updateSubjects(null);
                }
            }
        });
    }

    private void loadData(){
        if(tag){
            complete.getInterNetData();
        }else{
            pendingComplete.getInterNetData();
        }
    }
    public void setWtype(int type){
        wtype=type;
    }
    @Override
    public void cancel() {
        super.cancel();
        complete.cancel();
        pendingComplete.cancel();
    }
    private Map<String,Object> getJsonMap(int start,int limit) {
        Map<String,Object>map=new HashMap<>(5);
        map.put("userId", User.getInstance().getUserId());
        map.put("subjectId",subjectId);
        map.put("typeId",wtype);
        map.put("index",start);
        map.put("limit",limit);
        return map;
    }

}
