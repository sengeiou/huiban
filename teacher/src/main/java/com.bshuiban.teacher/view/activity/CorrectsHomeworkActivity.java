package com.bshuiban.teacher.view.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.utils.SpaceItemDecoration;
import com.bshuiban.baselibrary.view.activity.BaseActivity;
import com.bshuiban.baselibrary.view.adapter.HomeworkCountAdapter;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bshuiban.teacher.R;
import com.bshuiban.teacher.contract.CorrectsHomeworkContract;
import com.bshuiban.teacher.present.CorrectsHomeworkPresent;

import java.util.List;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_TYPE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

/**
 * 批改作业
 */
public class CorrectsHomeworkActivity extends BaseActivity<CorrectsHomeworkPresent>implements CorrectsHomeworkContract.View {

    private WebView mWebView;
    private ImageView iv_right;
    private ImageView iv_half_right;
    private ImageView iv_wrong;
    private int workId;
    private int home_type;
    private HomeworkCountAdapter adapter;
    /**
     * 当前
     */
    private HomeworkBean mHomeworkBean;
    private boolean firstLoad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrects_homework);
        workId = getIntent().getIntExtra(HOME_Work_Id, -1);
        home_type = getIntent().getIntExtra(HOME_TYPE, 1);
        tPresent=new CorrectsHomeworkPresent(this);
        initViews();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter = new HomeworkCountAdapter();
        adapter.setOnItemClickListener(homeworkBean -> {
            loadHtmlContent(homeworkBean);
        });
        recyclerView.setAdapter(adapter);
        int dimension = (int) getResources().getDimension(R.dimen.dp_10);
        int dimension5 = (int) getResources().getDimension(R.dimen.dp_5);
        recyclerView.setPadding(dimension5,dimension5,0,dimension5);
        recyclerView.addItemDecoration(new SpaceItemDecoration(this,RecyclerView.HORIZONTAL, dimension, Color.WHITE));
        mWebView = findViewById(R.id.webView);
        initWebView();
        iv_right = findViewById(R.id.iv_right);
        iv_half_right = findViewById(R.id.iv_half_right);
        iv_wrong = findViewById(R.id.wrong);
        iv_right.setOnClickListener(onClickListener);
        iv_half_right.setOnClickListener(onClickListener);
        iv_wrong.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            mHomeworkBean.setCorrect(true);
            if(id==R.id.iv_right){
                setRight(true);
                mHomeworkBean.setResult(1);
            }else if(id==R.id.iv_half_right){
                setHalfRight(true);
                mHomeworkBean.setResult(3);
            }else {
                setWrong(true);
                mHomeworkBean.setResult(0);
            }
            adapter.notifyItemChangedPosition();
        }
    };
    /**
     * 加载网页数据
     * @param homeworkBean
     */
    private void loadHtmlContent(HomeworkBean homeworkBean){
        this.mHomeworkBean=homeworkBean;
        String json;
        if(null!=homeworkBean) {
            String type = homeworkBean.getType();
            int pageIndex = homeworkBean.getPageIndex();
            int typeIndex = homeworkBean.getTypeIndex();
            int problemIndex = homeworkBean.getProblemIndex();
            json = HomeworkBean.getProblemContent(User.getInstance().getHomeworkInfBean(), type, pageIndex, typeIndex, problemIndex);
            boolean correct = homeworkBean.getCorrect();
            if(correct){
                if(homeworkBean.getResult()==1){
                    setRight(false);
                }else{
                    setWrong(false);
                }
                setImagEnable(false);
            }else{
                setImagEnable(true);
                restore();
            }
        }else {
            json="";
            setImagEnable(false);
        }
        String url = "javascript:setContent('" + (json.replace("\\", "\\\\")) + "')";
        mWebView.loadUrl(url);
    }

    private void restore() {
        iv_wrong.setVisibility(View.VISIBLE);
        iv_right.setVisibility(View.VISIBLE);
        iv_half_right.setVisibility(View.VISIBLE);
        iv_right.clearColorFilter();
        iv_half_right.clearColorFilter();
        iv_wrong.clearColorFilter();
    }

    private void initWebView(){
        CorrectsHomeworkHtml homeworkHtml=new CorrectsHomeworkHtml();
        mWebView.addJavascriptInterface(homeworkHtml,"test");
        mWebView.loadUrl("file:///android_asset/test_work.html");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if(firstLoad) {
                    firstLoad=false;
                    tPresent.loadHomeworkInf(workId, home_type);
                }
            }
        });
    }
    private void setImagEnable(boolean tag){
        iv_right.setEnabled(tag);
        iv_half_right.setEnabled(tag);
        iv_wrong.setEnabled(tag);
    }
    /**
     * 答案正确
     * @param show 是否显示其余项
     */
    private void setRight(boolean show){
        if(show){
            iv_right.clearColorFilter();
            iv_right.setVisibility(View.VISIBLE);
            iv_half_right.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect),PorterDuff.Mode.SRC_ATOP);
            iv_wrong.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect),PorterDuff.Mode.SRC_ATOP);
        }else{
            iv_half_right.setVisibility(View.GONE);
            iv_wrong.setVisibility(View.GONE);
        }
    }

    private void setHalfRight(boolean show){
        if(show){
            iv_half_right.clearColorFilter();
            iv_half_right.setVisibility(View.VISIBLE);
            iv_right.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect),PorterDuff.Mode.SRC_ATOP);
            iv_wrong.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect),PorterDuff.Mode.SRC_ATOP);
        }else{
            iv_right.setVisibility(View.GONE);
            iv_wrong.setVisibility(View.GONE);
        }
    }

    private void setWrong(boolean show){
        if(show){
            iv_wrong.clearColorFilter();
            iv_wrong.setVisibility(View.VISIBLE);
            iv_half_right.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect),PorterDuff.Mode.SRC_ATOP);
            iv_right.setColorFilter(ContextCompat.getColor(this, R.color.tab_unselect),PorterDuff.Mode.SRC_ATOP);
        }else{
            iv_half_right.setVisibility(View.GONE);
            iv_right.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateListCountView(List<HomeworkBean> homeworkBean) {
        adapter.setList(homeworkBean);
        if(HomeworkBean.isEffictive(homeworkBean)) {
            loadHtmlContent(homeworkBean.get(0));
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
        /**
         * 试卷标志
         * 0 可作答，1 不可作答，显示解析，2 不可作答，批阅状态
         */
        @JavascriptInterface
        public int getTag(){
            return 2;
        }
    }
}
