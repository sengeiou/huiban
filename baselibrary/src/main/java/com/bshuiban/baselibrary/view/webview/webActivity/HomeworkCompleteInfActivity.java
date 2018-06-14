package com.bshuiban.baselibrary.view.webview.webActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.Homework;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.HomeworkListData;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.utils.SpaceItemDecoration;
import com.bshuiban.baselibrary.view.adapter.HomeworkCountAdapter;
import com.bshuiban.baselibrary.view.adapter.SortHomewrokAdapter;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.HomeworkInfHtml;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.UserTypeHtml;
import com.google.gson.Gson;

import java.util.List;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_PREPARE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

/**
 * 已完成作业详情
 *
 * HTML
 * 几套试卷列表 - item
 *
 */
public class HomeworkCompleteInfActivity extends BaseWebActivity{
    private int prepareId,workId;
    private RecyclerView recycleView;
    private List<HomeworkBean> homeworkBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_complete);
        Intent intent = getIntent();
        prepareId = intent.getIntExtra(HOME_PREPARE, -1);
        workId = intent.getIntExtra(HOME_Work_Id, -1);
        mWebView=findViewById(R.id.webview);
        recycleView=findViewById(R.id.recycleView);

        TitleView titleView = findViewById(R.id.titleView);
        titleView.setOnClickListener(v-> finish());
        titleView.setRight_text(null, Color.WHITE,14);
        loadFileHtml("homework_details");
        registerWebViewH5Interface(new HomeworkCompleteInfHtml());
        int dimension = (int) getResources().getDimension(R.dimen.dp_10);
        int dimension5 = (int) getResources().getDimension(R.dimen.dp_5);
        recycleView.setPadding(dimension5, dimension5, 0, dimension5);
        recycleView.addItemDecoration(new SpaceItemDecoration(this, RecyclerView.HORIZONTAL, dimension, Color.WHITE));

    }

    @Override
    protected boolean initWebView() {
        return false;
    }

    @Override
    protected void webViewLoadFinished() {
        HomeworkInfBean.DataBean homeworkInfBean = User.getInstance().getHomeworkInfBean();
        homeworkBean = HomeworkBean.getHomeworkBean(homeworkInfBean);
        Homework<HomeworkBean> homework = new Homework();
        homework.setTitle(homeworkInfBean.getTitle());
        homework.setFTime(homeworkInfBean.getTimes());
        homework.setHomework(homeworkBean);
        User.getInstance().setHomework(homework);
        if(HomeworkBean.isEffictive(homeworkBean)) {
            SortHomewrokAdapter adapter = new SortHomewrokAdapter();
            adapter.setCount(homeworkBean.size());
            recycleView.setAdapter(adapter);
            adapter.setOnItemClickListener(position -> loadHtmlData(position));
            loadHtmlData(0);
        }
    }
    private void loadHtmlData(int position){
        HomeworkBean bean = this.homeworkBean.get(position);
        String type = bean.getType();
        int pageIndex = bean.getPageIndex();
        int typeIndex = bean.getTypeIndex();
        int problemIndex = bean.getProblemIndex();
        String json = HomeworkBean.getProblemContent(User.getInstance().getHomeworkInfBean(), type, pageIndex, typeIndex, problemIndex);
        loadJavascriptMethod("rende",json);
    }
    class HomeworkCompleteInfHtml extends UserTypeHtml{
        @JavascriptInterface
        public void toHomeworkResultPage(){
            runOnUiThread(()->{
                startActivity(new Intent(getApplicationContext(),HomeworkResultWebActivity.class).putExtra(HOME_Work_Id,workId).putExtra(HOME_PREPARE,prepareId).putExtra("complete",true));
            });
        }
    }
}
