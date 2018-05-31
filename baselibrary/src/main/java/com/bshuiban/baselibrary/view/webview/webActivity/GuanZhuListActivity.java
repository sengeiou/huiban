package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.contract.GuanZhuListContract;
import com.bshuiban.baselibrary.present.GuanZhuListPresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;

/**
 * 关注列表
 */
public class GuanZhuListActivity extends BaseWebActivity<GuanZhuListPresent> implements GuanZhuListContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tPresent=new GuanZhuListPresent(this);
        loadFileHtml("follow");
        GuanZhuList object = new GuanZhuList();
        object.setOnListener(new MessageList.OnMessageListListener() {
            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }

            @Override
            public void refresh() {
                tPresent.refresh();
            }
        });
        mWebView.addJavascriptInterface(object,"android");
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.getInterNetData();
    }

    @Override
    public void goLiuYan(String name,String userId) {
        Intent intent = new Intent(this, LiuYanMsgListActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }


    @Override
    public void guanZhuResult() {
        toast("成功");
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

    }
    class GuanZhuList extends MessageList{
        @JavascriptInterface
        public void goLiuYan(String name,String userId){
            handler.post(()->{
               GuanZhuListActivity.this.goLiuYan(name,userId);
            });
        }

        /**
         * 关注
         */
        @JavascriptInterface
        public void dealWithJson(String key,String json){
           tPresent.guanZhu(key,json);
        }
        @JavascriptInterface
        public void search(){

        }
    }
}
