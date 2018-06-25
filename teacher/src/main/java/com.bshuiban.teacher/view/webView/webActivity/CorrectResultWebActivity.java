package com.bshuiban.teacher.view.webView.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.model.Homework;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.LogUtils;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.teacher.contract.CorrectResultContract;
import com.bshuiban.teacher.present.CorrectResultPresent;
import com.bshuiban.teacher.view.activity.PrepareLessonInfActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_TYPE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：批阅结果
 */
public class CorrectResultWebActivity extends BaseWebActivity<CorrectResultPresent> implements CorrectResultContract.View {
    private String jsonObjectS;
    private int workId,home_type;
    private String studentId;
    private String preId;
    private List<HomeworkBean> homeworkBeans;
    private String classId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        preId = intent.getStringExtra("preparationId");
        studentId = intent.getIntExtra("studentId",-1)+"";
        String stuName = intent.getStringExtra("stuName");
        String title = intent.getStringExtra("title");
        Type type1 = new TypeToken<List<HomeworkBean>>() {
        }.getType();
        homeworkBeans = new Gson().fromJson(json, type1);
        JsonArray asJsonArray = new JsonParser().parse(json).getAsJsonArray();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("title",title);
        jsonObject.addProperty("stuName",stuName);
        jsonObject.add("homework",asJsonArray);
        jsonObjectS=new Gson().toJson(jsonObject);
        loadFileHtml("remakes");
        registerWebViewH5Interface(new CorrectResultHtml());
        this.workId = getIntent().getIntExtra(HOME_Work_Id, -1);
        home_type = getIntent().getIntExtra(HOME_TYPE, 1);
        classId = getIntent().getStringExtra("classId");
        tPresent=new CorrectResultPresent(this);
    }

    @Override
    protected void webViewLoadFinished() {
        loadJavascriptMethod("remakes",jsonObjectS);
    }

    private String changeForMarkHBWork(List<HomeworkBean> homeworkBeans) {
        if (HomeworkBean.isEffictive(homeworkBeans)) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < homeworkBeans.size(); i++) {
                HomeworkBean homeworkBean = homeworkBeans.get(i);
                if(homeworkBean.getCorrect()) {
                    stringBuffer.append(homeworkBean.getProblemId());
                    stringBuffer.append("___");
                    stringBuffer.append(getAnswerResult(homeworkBean.getResult()));
                    //stringBuffer.append("_,_");
                    stringBuffer.append("_,_");
                }
            }
            int length = stringBuffer.length();
            String s = stringBuffer.delete(length - 3, length).toString();
            LogUtils.e(TAG, "changeForMarkHBWork: "+s );
            return s;
        }
        return null;
    }

    private int getAnswerResult(int statue) {
        switch (statue) {//0 错误 1 正确 2 未知 3 半对
            case 1://1对2错3半对
                return 1;
            case 3:
                return 3;
            default:
                return 2;
        }
    }

    @Override
    public void commitSuccess() {
        startActivity(new Intent(getApplicationContext(),PrepareLessonInfActivity.class));
        finish();
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
    class CorrectResultHtml{
        @JavascriptInterface
        public void commit(){
            tPresent.commitHomeworkResult(classId,preId,home_type,workId,studentId,changeForMarkHBWork(homeworkBeans));
        }
    }
}
