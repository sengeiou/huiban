package com.bshuiban.teacher.view.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bshuiban.baselibrary.view.activity.BaseActivity;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bshuiban.teacher.R;
import com.bshuiban.teacher.contract.CorrectsHomeworkContract;
import com.bshuiban.teacher.present.CorrectsHomeworkPresent;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_TYPE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

public class CorrectsHomeworkActivity extends BaseActivity<CorrectsHomeworkPresent>implements CorrectsHomeworkContract.View {

    private WebView mWebView;
    private ImageView iv_right;
    private ImageView iv_half_right;
    private ImageView iv_wrong;
    private int workId;
    private int home_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workId = getIntent().getIntExtra(HOME_Work_Id, -1);
        home_type = getIntent().getIntExtra(HOME_TYPE, 1);
        tPresent=new CorrectsHomeworkPresent(this);
        setContentView(R.layout.activity_corrects_homework);
        initViews();
        tPresent.loadHomeworkInf(workId,home_type);
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
        RecyclerView recyclerView = findViewById(R.id.titleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mWebView = findViewById(R.id.webView);
        initWebView();
        iv_right = findViewById(R.id.iv_right);
        iv_half_right = findViewById(R.id.iv_half_right);
        iv_wrong = findViewById(R.id.wrong);
    }
    private void initWebView(){
        CorrectsHomeworkHtml homeworkHtml=new CorrectsHomeworkHtml();
        mWebView.addJavascriptInterface(homeworkHtml,"android");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }
    /**
     * 答案正确
     * @param show 是否显示其余项
     */
    private void setRight(boolean show){
        if(show){
            iv_right.clearColorFilter();
            iv_half_right.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect));
            iv_wrong.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect));
        }else{
            iv_half_right.setVisibility(View.GONE);
            iv_wrong.setVisibility(View.GONE);
        }
    }

    private void setHalfRight(boolean show){
        if(show){
            iv_half_right.clearColorFilter();
            iv_right.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect));
            iv_wrong.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect));
        }else{
            iv_right.setVisibility(View.GONE);
            iv_wrong.setVisibility(View.GONE);
        }
    }

    private void setWrong(boolean show){
        if(show){
            iv_wrong.clearColorFilter();
            iv_half_right.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect));
            iv_right.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect));
        }else{
            iv_half_right.setVisibility(View.GONE);
            iv_right.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateListCountView() {

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
    class CorrectsHomeworkHtml{
        @JavascriptInterface
        public String getTestContent(){
            return "";
        }
    }
}
