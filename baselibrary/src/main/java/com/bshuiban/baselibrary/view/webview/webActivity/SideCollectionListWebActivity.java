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
        tPresent=new SideCollectionListPresent(this);
        loadFileHtml("myFavorite");
        SideCollectionHtml object = new SideCollectionHtml();
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
        tPresent.loadCollectionListData();
    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("rend",(json));
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
    class SideCollectionHtml extends MessageList{
        @JavascriptInterface
        public void toNextHuiFuActivity(String courseId) {
            runOnUiThread(() -> startActivity(new Intent(getApplicationContext(),LessonInfWebActivity.class).putExtra("courseId",courseId)));
        }
    }
}
