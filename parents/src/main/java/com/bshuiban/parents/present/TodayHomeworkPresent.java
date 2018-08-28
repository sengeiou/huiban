package com.bshuiban.parents.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.parents.contract.TodayHomeworkContract;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public class TodayHomeworkPresent extends BasePresent<TodayHomeworkContract.View> implements TodayHomeworkContract.Present {
    public TodayHomeworkPresent(TodayHomeworkContract.View view) {
        super(view);
    }

    @Override
    public void loadTodayHomework() {//{"userId":"","type":}
        askInternet("getHBParTodayWork", "{\"userId\":\""+ User.getInstance().getUserId()+"\"}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    if(null==msg){
                        view.updateListView(1,"[]");
                        return;
                    }
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                    JsonElement parse = new JsonParser().parse(msg);
                    if(parse!=null&&parse.isJsonArray()){
                        JsonArray jsonArray = parse.getAsJsonArray();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonElement jsonElement = jsonArray.get(i);
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            JsonElement endTime = jsonObject.get("endTime");
                            int asInt = endTime.getAsInt();
                            String format = sf.format(new Date(asInt * 1000l));
                            jsonObject.remove("endTime");
                            jsonObject.addProperty("endTime",format);
                        }
                    }
                    view.updateListView(1,new Gson().toJson(parse));
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
