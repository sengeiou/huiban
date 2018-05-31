package com.bshuiban.teacher.view.webView.webActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bshuiban.baselibrary.utils.DialogUtils;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.teacher.R;
import com.bshuiban.teacher.contract.StudentAnswerInfContract;
import com.bshuiban.teacher.model.PrepareLessonBean;
import com.bshuiban.teacher.present.StudentAnswerInfPresent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：学生答题情况
 */
public class StudentAnswerInfWebActivity extends BaseWebActivity<StudentAnswerInfPresent> implements StudentAnswerInfContract.View {
    private String preparationId,className;
    private int process,  workId,  classId;
    private List<PrepareLessonBean.DataBean.ClassArrBean> classArrBeans;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        preparationId = intent.getStringExtra("preparationId");
        process = intent.getIntExtra("process",-1);//课前0，课中1，课后2
        workId = intent.getIntExtra("workId",-1);
        classId = intent.getIntExtra("classId",-1);
        className = intent.getStringExtra("className");
//        Type type1 = new TypeToken<List<PrepareLessonBean.DataBean.ClassArrBean>>(){}.getType();
//        classArrBeans = new Gson().fromJson(text, type1);
        tPresent=new StudentAnswerInfPresent(this);
    }

    @Override
    protected void webViewLoadFinished() {
        PrepareLessonBean.DataBean.ClassArrBean classArrBean = classArrBeans.get(0);
        classId= classArrBean.getClassId();
        String className = classArrBean.getClassName();
        loadJavascriptMethod("",className);
        tPresent.loadAnswerInf(preparationId,process,workId,classId);
    }

    @Override
    public void updateView(String json) {
        loadJavascriptMethod("",json);
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
    class StudentAnswerInfHtml{
        /**
         * 启动班级弹窗
         */
        @JavascriptInterface
        void startClassDialog(){
            runOnUiThread(()->{
                showClassDialog();
            });
        }
    }

    private void showClassDialog() {
        if(dialog==null||!dialog.isShowing()) {
            dialog = new Dialog(this);
            ListView listView = new ListView(this);
            dialog.setContentView(listView);
            DialogUtils.setDialogWithMatcherScreen(dialog, Gravity.BOTTOM);
            dialog.setCanceledOnTouchOutside(true);
            ArrayAdapter<PrepareLessonBean.DataBean.ClassArrBean> adapter = new ArrayAdapter<PrepareLessonBean.DataBean.ClassArrBean>(this, R.layout.layout_textview_item, R.id.tv, classArrBeans);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                PrepareLessonBean.DataBean.ClassArrBean classArrBean = classArrBeans.get(position);
                String className = classArrBean.getClassName();
                classId = classArrBean.getClassId();
                loadJavascriptMethod("", className);
                tPresent.loadAnswerInf(preparationId,process,workId,classId);
            });
            dialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dialog=null;
        super.onDestroy();
    }
}
