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
        if(parse.isJsonObject()){
            JsonObject asJsonObject = parse.getAsJsonObject();
            asJsonObject.addProperty("userId", User.getInstance().getUserId());
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
