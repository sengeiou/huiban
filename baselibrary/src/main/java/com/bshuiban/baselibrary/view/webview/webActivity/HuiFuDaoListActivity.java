package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.contract.HuiFuDaoListContract;
import com.bshuiban.baselibrary.present.HuiFuDaoListPresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：慧辅导列表
 */
public class HuiFuDaoListActivity extends BaseWebActivity<HuiFuDaoListPresent> implements HuiFuDaoListContract.View {
    private final String HTML_FILE_NAME="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = ViewUtils.getFrameLayout(this);
        mWebView=getWebView(getApplicationContext());
        frameLayout.addView(mWebView);
        setContentView(frameLayout);
        tPresent=new HuiFuDaoListPresent(this);
        loadFileHtml(HTML_FILE_NAME);
        HuiFuDaoHtml messageList = new HuiFuDaoHtml();
        messageList.setOnListener(new MessageList.OnMessageListListener() {
            @Override
            public void refresh() {
                tPresent.refresh();
            }

            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }
        });
        registerWebViewH5Interface(messageList);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.getAllSubject();
        JsonObject jsonObject = new JsonObject();
        tPresent.screeningLesson("getHBCourseList",new Gson().toJson(jsonObject));
    }

    @Override
    public void updateList(String json) {
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

    @Override
    public void loadAllSubject(String json) {
        //loadJavascriptMethod("",json);
    }

    @Override
    public void loadGuessWhatYouThink(String json) {
        //loadJavascriptMethod("",json);
    }

    @Override
    public void loadScreeningData(String json) {
        //loadJavascriptMethod("",json);
    }

    class HuiFuDaoHtml extends MessageList{
        @JavascriptInterface
        public void reSetStart(){
            tPresent.reSetStart();
        }
        @JavascriptInterface
        public void getGuessWhatYouThink(String subjectId){
            tPresent.guessWhatYouThink(subjectId);
        }
        @JavascriptInterface
        public void dealWithJson(String key,String json){
            if("getHBCourseList".equals(key)){
                tPresent.screeningLesson("getHBCourseList",json);
            }else{//获取筛选条件
                tPresent.getScreeningData(json);
            }
        }
    }
}
