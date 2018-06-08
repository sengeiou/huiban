package com.bshuiban.teacher.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bshuiban.baselibrary.utils.SpaceItemDecoration;
import com.bshuiban.baselibrary.view.activity.BaseActivity;
import com.bshuiban.baselibrary.view.adapter.SortHomewrokAdapter;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bshuiban.teacher.R;
import com.bshuiban.teacher.contract.HomeworkAnswerInfContract;
import com.bshuiban.teacher.present.HomeworkAnswerInfPresent;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_TYPE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：课中作业答题详情
 */
public class HomeworkAnswerInfActivity extends BaseActivity<HomeworkAnswerInfPresent> implements HomeworkAnswerInfContract.View {
    private String preId;
    private int home_type=2,workId;
    private String classId;
    private SortHomewrokAdapter adapter;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_homework_answer_inf);
        preId = getIntent().getStringExtra("preId");
        workId = getIntent().getIntExtra(HOME_Work_Id, -1);
        home_type = getIntent().getIntExtra(HOME_TYPE, 2);
        classId = getIntent().getIntExtra("classId",-1)+"";
        initViews();
        tPresent=new HomeworkAnswerInfPresent(this);
    }
    private void initViews() {
        TitleView titleView = findViewById(R.id.titleView);
        titleView.setOnClickListener(new TitleView.OnClickListener() {
            @Override
            public void leftClick(View v) {
                finish();
            }

            @Override
            public void rightClick(View v) {

            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycle);
        adapter = new SortHomewrokAdapter();
        adapter.setOnItemClickListener(position -> {
            tPresent.loadProblemContent(position);
        });
        recyclerView.setAdapter(adapter);
        int dimension = (int) getResources().getDimension(R.dimen.dp_10);
        int dimension15 = (int) getResources().getDimension(R.dimen.dp_15);
        recyclerView.setPadding(dimension15,dimension,0,dimension);
        recyclerView.addItemDecoration(new SpaceItemDecoration(this,RecyclerView.HORIZONTAL, dimension15, Color.WHITE));
        mWebView = findViewById(R.id.webView);
        initWebView();
    }

    private void initWebView() {
        mWebView.loadUrl("file:///android_asset/exercise.html");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                tPresent.loadHomeworkAnswerData(preId,workId,home_type,classId);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(null!=mWebView){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebView.clearHistory();
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView=null;
        }
        super.onDestroy();
    }

    @Override
    public void updateWebView(String json) {
        String url = "javascript:" + "exercise" + "('" + json.replace("\\", "\\\\") + "')";
        mWebView.loadUrl(url);
    }

    @Override
    public void loadWebView(int size) {
        if(size>0) {
            adapter.setCount(size);
            tPresent.loadProblemContent(0);
        }else {
            toast("暂无数据");
        }
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
}
