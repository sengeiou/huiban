package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ErrorFilterContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by xinheng on 2018/5/26.<br/>
 * describe：
 */
public class ErrorFilterPresent extends BasePresent<ErrorFilterContract.View> implements ErrorFilterContract.Present {
    private Gson gson=new Gson();
    private JsonParser jsonParser=new JsonParser();
    public ErrorFilterPresent(ErrorFilterContract.View view) {
        super(view);
    }

    @Override
    public void loadFilter(String key,String json) {
        JsonElement parse = jsonParser.parse(json);
        if(parse.isJsonObject()) {
            JsonObject asJsonObject = parse.getAsJsonObject();
            if(key.equals("getSiteHyOrganLBySid")){//机构
                asJsonObject.addProperty("schoolId",User.getInstance().getSchoolId());
                //asJsonObject.addProperty("filterModule", "stuWrong");//已添加
            }else {
                asJsonObject.addProperty("userId", User.getInstance().getUserId());
                if(!"getFilterChapterL".equals(key)&&!"getFilterKnowL".equals(key))// 不是 章节/知识点 目录
                    asJsonObject.addProperty("filterModule", "prepare");
                else
                    if("getFilterKnowL".equals(key))
                        asJsonObject.addProperty("siteId",User.getInstance().getSchoolId());
                if(null==asJsonObject.get("organs")) {
                    //asJsonObject.addProperty("organs","1-"+User.getInstance().getSchoolId());
                }
            }
            json=gson.toJson(asJsonObject);
        }
        askInternet(key, json, new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateView(msg);
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
}
