package com.bshuiban.teacher.view.webView.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.baselibrary.view.webview.webActivity.LessonInfWebActivity;
import com.bshuiban.teacher.contract.LessonListContract;
import com.bshuiban.teacher.present.LessonListPresent;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：课程列表
 */
public class LessonListActivity extends BaseWebActivity<LessonListPresent> implements LessonListContract.View {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new LessonListPresent(this);
        loadFileHtml("stuResource");
        LessonListHtml object = new LessonListHtml();
        object.setOnListener(new MessageList.OnMessageListListener(){
            @Override
            public void refresh() {
                tPresent.refresh();
            }

            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }
        });
        registerWebViewH5Interface(object);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadLessonListData(null);
    }

    @Override
    public void recommendParent() {
        //to next page
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
    class LessonListHtml extends MessageList{
        /**
         * 推荐给家长
         * @param courseId  教学资源标识
         */
        @JavascriptInterface
        public void recommendParent(String courseId){
            tPresent.loadRecommendParent(courseId);
        }

        /**
         * 列表每个小单元 点击
         * @param courseId
         */
        @JavascriptInterface
        public void itemClick(String courseId){
            runOnUiThread(()->{
                startActivity(new Intent(getApplicationContext(), TeacherLessonInfWebActivity.class).putExtra("courseId", courseId));
            });
        }
        @JavascriptInterface
        public void dealWithJson(String search){
            tPresent.loadSearchLessonListData(search);
        }
        @JavascriptInterface
        public void toSearchPage(){
            runOnUiThread(()->{
                Intent intent = new Intent(getApplicationContext(),FilterConditionActivity.class);
                startActivityForResult(intent,100);
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                if(data!=null){
                    String json = data.getStringExtra("text");
                    tPresent.loadLessonListData(json);
                }
                break;
        }
    }
}
