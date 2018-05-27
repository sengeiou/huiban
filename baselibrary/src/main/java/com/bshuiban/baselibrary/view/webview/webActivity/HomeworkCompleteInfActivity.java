package com.bshuiban.baselibrary.view.webview.webActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.HomeworkListData;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.HomeworkInfHtml;
import com.google.gson.Gson;
import java.util.List;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_PREPARE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_TYPE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

/**
 * 已完成作业详情
 *
 * HTML
 * 几套试卷列表 - item
 *
 */
public class HomeworkCompleteInfActivity extends BaseWebActivity{
    private Gson gson=new Gson();
    private int prepareId,workId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        prepareId = intent.getIntExtra(HOME_PREPARE, -1);
        workId = intent.getIntExtra(HOME_Work_Id, -1);
        loadFileHtml("details");
        registerWebViewH5Interface(new HomeworkCompleteInfHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        //tPresent.loadHomeworkInf(workId,homeType);
        HomeworkInfBean.DataBean homeworkInfBean = User.getInstance().getHomeworkInfBean();
        List<HomeworkListData> homeworkInfList = HomeworkListData.getHomeworkInfList(homeworkInfBean);
        loadJavascriptMethod("item", gson.toJson(homeworkInfList));
    }

    class HomeworkCompleteInfHtml extends HomeworkInfHtml{
        @JavascriptInterface
        public void toHomeworkResultPage(){
            runOnUiThread(()->{
                startActivity(new Intent(getApplicationContext(),HomeworkResultWebActivity.class).putExtra(HOME_Work_Id,workId).putExtra(HOME_PREPARE,prepareId).putExtra("complete",true));
            });
        }
    }
}
