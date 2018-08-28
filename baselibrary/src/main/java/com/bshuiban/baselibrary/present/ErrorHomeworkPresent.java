package com.bshuiban.baselibrary.present;

import android.content.Intent;

import com.bshuiban.baselibrary.contract.ErrorHomeworkContract;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：
 */
public class ErrorHomeworkPresent extends ListPresent<ErrorHomeworkContract.View> implements ErrorHomeworkContract.Present {
    private String userId = User.getInstance().getUserId();    //int，用户id
    private String mSubjectId;     //int, 科目id
    private String mVersionId;     //int，版本id
    private String mFasId;            //int，分册id
    private String mChapBranId;   //int，章节id
    private String mSeriBrandId;   //int，知识点id
    private String mStageId;   //int，学段 id
    private String mOrgans;   //int，机构

    public ErrorHomeworkPresent(ErrorHomeworkContract.View view) {
        super(view);
    }

    @Override
    public void loadErrorHomeworkData() {
        getInterNetData();
    }

    @Override
    public void itemClick(int index) {
        if (null!=jsonArray&&jsonArray.size()>index){
            JsonElement jsonElement = jsonArray.get(index);
            view.toErrorHomeworkAnalysisPage(new Gson().toJson(jsonElement));
        }
    }

    @Override
    public void getInterNetData() {
        askInternet("getStuWrongList", getJsonMap(), callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        if(isEffective()){
            view.updateList(json);
        }
    }

    private Map<String, Object> getJsonMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("index",start);
        map.put("limit",limit);

        map.put("subjectId", clear100(mSubjectId));
        map.put("versionId",clear100(mVersionId));
        map.put("fasId",clear100(mFasId));
        map.put("chapBranId",clear100(mChapBranId));
        map.put("seriBrandId",clear100(mSeriBrandId));

        map.put("stageId",clear100(mStageId));
        map.put("schoolId",clear100(mOrgans));
        //ErrorFilterActivity.class
        return map;
    }
    private Object clear100(String tag){
        if("-100".equals(tag)){
            return "";
        }
        return tag;
    }
    public boolean setSelectInf(int subjectId, int versionId, int fasId, int chapBranId, int seriBrandId,int stageId,String organs) {
        if (Integer.toString(subjectId).equals(mSubjectId) &&
                Integer.toString(versionId).equals(mVersionId) &&
                Integer.toString(fasId).equals(mFasId) &&
                Integer.toString(chapBranId).equals(mChapBranId) &&
                Integer.toString(seriBrandId).equals(mSeriBrandId)&&
                Integer.toString(stageId).equals(mStageId)&&
                organs.equals(mOrgans)) {
            return false;
        }
        mSubjectId = Integer.toString(subjectId);
        mVersionId = Integer.toString(versionId);
        mFasId = Integer.toString(fasId);
        mChapBranId = Integer.toString(chapBranId);
        mSeriBrandId = Integer.toString(seriBrandId);
        mStageId = Integer.toString(stageId);
        mOrgans = organs;
        getInterNetData();
        return true;
    }
}
