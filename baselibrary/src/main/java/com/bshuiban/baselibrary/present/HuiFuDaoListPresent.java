package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.HuiFuDaoListContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.SubjectBean;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：慧辅导
 */
public class HuiFuDaoListPresent extends ListPresent<HuiFuDaoListContract.View> implements HuiFuDaoListContract.Present {
    private AllSubjectPresent allSubjectPresent;
    private String key, json;
    private int subjectId = -1;

    public HuiFuDaoListPresent(HuiFuDaoListContract.View view) {
        super(view);
        allSubjectPresent = new AllSubjectPresent<HuiFuDaoListContract.View>(view) {
            @Override
            protected void loadAllSubject(SubjectBean dataBean) {
                HuiFuDaoListPresent.this.loadAllSubject(dataBean);
            }
        };
    }

    public void reSetStart() {
        start = 0;
        clearArray();
    }

    @Override
    public void screeningLesson(String key, String json) {
        this.key = key;
        this.json=json;
        getInterNetData();
    }

    @Override
    public void getAllSubject() {
        allSubjectPresent.getAllSubject();
    }

    private void loadAllSubject(SubjectBean subjectBean) {
        if (isEffective() && null != subjectBean.getData()) {
            view.loadAllSubject(gson.toJson(subjectBean.getData()));
        }
    }

    @Override
    public void getScreeningData(String json) {
        RetrofitService.getInstance().getServiceResult("getVipRelationEduList", json, new RetrofitService.CallHTMLJsonArray() {
            @Override
            protected void success(JsonArray msg) {
                if (isEffective()) {
                    view.loadScreeningData(gson.toJson(msg));
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
    public void guessWhatYouThink(String subjectId) {
        RetrofitService.getInstance().getServiceResult("getVipSearchByUSub", "{\"subjectId\":\"" + subjectId + "\",\"userId\":\"" + User.getInstance().getUserId() + "\"}", new RetrofitService.CallHTMLJsonArray() {
            @Override
            protected void success(JsonArray msg) {
                if (isEffective()) {
                    String s = gson.toJson(msg);
                    view.loadGuessWhatYouThink(s);
                    view.addTag();
                }
            }

            @Override
            protected void fail(String error) {
                if (isEffective()) {
                    if(error!=null&&error.contains("暂无数据")) {
                        view.fail("猜你所想："+error);
                    }
                    view.addTag();
                }
            }
        });
    }

    @Override
    public void getInterNetData() {
        JsonElement parse = new JsonParser().parse(json);
        if (parse.isJsonObject()) {
            JsonObject asJsonObject = parse.getAsJsonObject();
            JsonElement subjectId1 = asJsonObject.get("subjectId");
            if (null != subjectId1) {
                subjectId = subjectId1.getAsInt();
                guessWhatYouThink(String.valueOf(subjectId));
            } else {//精品
                subjectId = -1;
                clearArray();
            }
            asJsonObject.addProperty("index", start);
            asJsonObject.addProperty("limit", limit);
            this.json = gson.toJson(asJsonObject);
            RetrofitService.getInstance().getServiceResult(key, json, callHTMLJsonArray);
        } else {
            if (isEffective()) {
                view.fail("提供的json串错误");
            }
        }
    }

    @Override
    public void updateView(String json) {
        if (isEffective()) {
            view.updateList(json);
            view.addTag();
        }
    }

    /**
     * 列表错误结果
     *
     * @param error
     */
    @Override
    public void fail(String error) {
        super.fail(error);
        view.addTag();//列表结果
    }

    public int getSubjectId() {
        return subjectId;
    }
}
