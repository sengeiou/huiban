package com.bshuiban.baselibrary.view.webview.webFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.HomeworkListContract;
import com.bshuiban.baselibrary.present.HomeworkListPresent;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：作业列表
 */
public class HomeworkListFragment extends BaseWebFragment<HomeworkListPresent> implements HomeworkListContract.View{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new HomeworkListPresent(this);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.getInterNetData();
    }

    @Override
    public void updateList(String json) {

    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {

    }
    class HomeworkListHtml extends MessageList{
        @JavascriptInterface
        public void reSetStart(){
            tPresent.reSetStart();
        }

        /**
         * 获取作业
         * @param tag true 已完成
         */
        @JavascriptInterface
        public void getHomework(boolean tag){
            tPresent.getInterNetData();
        }
    }
}
