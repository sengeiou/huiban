package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.SideCollectionListContract;
import com.bshuiban.baselibrary.present.SideCollectionListPresent;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;

public class SideCollectionListWebActivity extends BaseWebActivity<SideCollectionListPresent> implements SideCollectionListContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new SideCollectionListPresent(this);
        loadFileHtml("myFavorite");
        //loadFileHtml("stuResource");
        SideCollectionHtml object = new SideCollectionHtml();
        object.setOnListener(new MessageList.OnMessageListListener() {
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
        startDialog();
        //ObserveModeGroupList.getInstance().register(this);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadCollectionListData();
    }

    @Override
    protected void onResume() {
//        if (update) {
//            update = false;
//        }
            tPresent.refresh();
        super.onResume();
    }

    @Override
    public void updateList(String json) {
        dismissDialog();
        loadJavascriptMethod("rend", (json));
        //loadJavascriptMethod("getContent", json);
    }

    @Override
    public void startDialog() {
        showLoadingDialog();
    }

    @Override
    public void dismissDialog() {
        dismissLoadingDialog();
    }

    @Override
    public void fail(String error) {
        dismissDialog();
        toast(error);
    }

    protected boolean update;

    @Override
    protected void onDestroy() {
        //ObserveModeGroupList.getInstance().unregister(this);
        super.onDestroy();
    }

    class SideCollectionHtml extends MessageList {
        /**
         * 推荐给家长
         *
         * @param courseId 教学资源标识
         */
        @JavascriptInterface
        public void recommendParent(String courseId) {
            //tPresent.loadRecommendParent(courseId);
            runOnUiThread(() -> {
                Class<?> cls = null;
                try {
                    cls = Class.forName("com.bshuiban.teacher.view.webView.webActivity.TeacherLessonInfWebActivity");
                    startActivity(new Intent(getApplicationContext(), cls).putExtra("courseId", courseId).putExtra("send", true));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }

        @JavascriptInterface
        public void toNextHuiFuActivity(String courseId) {
            //startActivity(new Intent(getApplicationContext(), TeacherLessonInfWebActivity.class).putExtra("courseId", courseId));
            runOnUiThread(() -> {
                //Class<LessonInfWebActivity> cls = LessonInfWebActivity.class;
                Class<?> cls = null;
                try {
                    cls = Class.forName("com.bshuiban.teacher.view.webView.webActivity.TeacherLessonInfWebActivity");
                    startActivity(new Intent(getApplicationContext(), cls).putExtra("courseId", courseId));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
