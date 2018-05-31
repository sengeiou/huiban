package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.ConsolidationContract;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.ConsolidationPresent;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * 巩固练习
 */
public class ConsolidationWebActivity extends BaseWebActivity<ConsolidationPresent> implements ConsolidationContract.View {

    private int examId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        examId = getIntent().getIntExtra("examId", -1);
        tPresent=new ConsolidationPresent(this);
        loadFileHtml("practive");
        registerWebViewH5Interface(new Consolidation());
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadConsolidationHomeworkData(examId);
    }

    @Override
    public void updateView(String json) {
        json = json.replace("\\", "\\\\");//没办法
        loadJavascriptMethod("getContent",json);
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        toast(error);
    }
    class Consolidation{
        @JavascriptInterface
        public void dealWithJson(String json){
            Log.e(TAG, "dealWithJson: ThreadName="+(Looper.myLooper().getThread().getName()) );
            try {
                JsonElement parse = new JsonParser().parse(json);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("knowId",examId);
                jsonObject.addProperty("userId", User.getInstance().getUserId());
                jsonObject.add("wrong",parse);
                tPresent.commitData(new Gson().toJson(jsonObject));
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                runOnUiThread(()->{
                    toast("json串错误-"+json);
                });
            }
        }
    }
}
