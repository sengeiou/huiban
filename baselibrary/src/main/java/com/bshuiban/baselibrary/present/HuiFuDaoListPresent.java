package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.HuiFuDaoListContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public class HuiFuDaoListPresent extends ListPresent<HuiFuDaoListContract.View> implements HuiFuDaoListContract.Present {
    private String key,json;
    public HuiFuDaoListPresent(HuiFuDaoListContract.View view) {
        super(view);
    }
    public void reSetStart(){
        start=0;
    }
    @Override
    public void screeningLesson(String key, String json) {
        this.key=key;
        JsonElement parse = new JsonParser().parse(json);
        if(parse.isJsonObject()){
            JsonObject asJsonObject = parse.getAsJsonObject();
            asJsonObject.addProperty("index",start);
            asJsonObject.addProperty("limit",limit);
            this.json=gson.toJson(asJsonObject);
            getInterNetData();
        }else{
            if (isEffective()){
                view.fail("提供的json串错误");
            }
        }
    }

    @Override
    public void getAllSubject() {
        RetrofitService.getInstance().getServiceResult("getVipCourseSubjectList", null, new RetrofitService.CallHTMLJsonArray() {
            @Override
            protected void success(JsonArray msg) {
                if(isEffective()){
                    view.loadAllSubject(gson.toJson(msg));
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
    public void getScreeningData(String json) {
        RetrofitService.getInstance().getServiceResult("getVipRelationEduList", json, new RetrofitService.CallHTMLJsonArray() {
            @Override
            protected void success(JsonArray msg) {
                if(isEffective()){
                    view.loadScreeningData(gson.toJson(msg));
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
    public void guessWhatYouThink(String subjectId) {
        RetrofitService.getInstance().getServiceResult("getVipSearchByUSub", "{\"subjectId\":\"" + subjectId + "\",\"userId\":\"" + User.getInstance().getUserId() + "\"}", new RetrofitService.CallHTMLJsonArray() {
            @Override
            protected void success(JsonArray msg) {
                if(isEffective()){
                    String s = gson.toJson(msg);
                    view.loadGuessWhatYouThink(s);
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
    public void getInterNetData() {
        RetrofitService.getInstance().getServiceResult(key,json,callHTMLJsonArray);
    }
}
