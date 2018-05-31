package com.bshuiban.teacher.view.webView.webActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.teacher.contract.MainWeiClassContract;
import com.bshuiban.teacher.present.MainWeiClassPresent;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：主讲微课
 */
public class MainWeiClassWebActivity extends BaseWebActivity<MainWeiClassPresent> implements MainWeiClassContract.View{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        tPresent=new MainWeiClassPresent(this);
        loadFileHtml("mainClassList");
        MessageList object = new MessageList();
        object.setOnListener(new MessageList.OnMessageListListener(){
            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }

            @Override
            public void refresh() {
                tPresent.refresh();
            }
        });
        registerWebViewH5Interface(object);
    }

    @Override
    public void toNextPage(Class<?> cls) {

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
}
