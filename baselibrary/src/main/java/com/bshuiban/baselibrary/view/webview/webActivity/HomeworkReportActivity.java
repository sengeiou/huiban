package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.HomeworkReportContract;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.HomeworkReportPresent;
import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.HomeworkInfHtml;
import com.google.gson.Gson;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_PREPARE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_TYPE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

/**
 * 已完成
 * 作业报告
 *
 * HTML
 * count--统计数据
 */
public class HomeworkReportActivity extends BaseWebActivity<HomeworkReportPresent> implements HomeworkReportContract.View {

    private int prepareId,workId,home_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        home_type = intent.getIntExtra(HOME_TYPE, 1);
        prepareId = intent.getIntExtra(HOME_PREPARE, -1);
        workId = intent.getIntExtra(HOME_Work_Id, -1);
        tPresent=new HomeworkReportPresent(this);
        loadFileHtml("echarts");
        registerWebViewH5Interface(new HomeworkReportHtml());
        if(User.getInstance().getUserData()==null){
            String userResJson = UserSharedPreferencesUtils.getUserResJson(getApplicationContext());
            LoginResultBean.Data data = new Gson().fromJson(userResJson, LoginResultBean.Data.class);
            User.getInstance().setData(data);
        }
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadHomeworkInf(workId, home_type);
    }

    @Override
    public void updateView(String json) {
        loadJavascriptMethod("count",json);
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
    class HomeworkReportHtml extends HomeworkInfHtml{
        @JavascriptInterface
        public void toNextPage(String type){
            runOnUiThread(()->{
                switch (type){
                    case "describe"://作业描述
                        startActivity(new Intent(getApplicationContext(),HomeworkDescribeActivity.class));
                        break;
                    case "homeworkInf"://作业详情
                        startActivity(new Intent(getApplicationContext(),HomeworkCompleteInfActivity.class).putExtra(HOME_Work_Id,workId).putExtra(HOME_PREPARE,prepareId));
                        break;
                }
            });
        }
    }
}
