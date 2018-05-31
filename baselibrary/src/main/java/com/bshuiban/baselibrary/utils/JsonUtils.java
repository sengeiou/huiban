package com.bshuiban.baselibrary.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public class JsonUtils {
    public static JsonObject parseJsonObjetNotNull(String json) {
        JsonObject mJson;
        if (android.text.TextUtils.isEmpty(json)) {
            mJson = new JsonObject();
        } else {
            JsonParser jsonParser = new JsonParser();
            JsonElement parse = jsonParser.parse(json);
            if (parse.isJsonObject()) {
                mJson = parse.getAsJsonObject();
            } else {
                mJson = new JsonObject();
            }
        }
        return mJson;
    }
}
