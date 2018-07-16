package com.bshuiban.teacher.view.webView.webActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.bshuiban.baselibrary.contract.ListContract;
import com.bshuiban.baselibrary.contract.LiuYanMsgListContract;
import com.bshuiban.baselibrary.model.MessageBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.LiuYanMsgListParent;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.view.activity.PlayerVideoActivity;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webActivity.BaseWebActivity;
import com.bshuiban.baselibrary.view.webview.webActivity.LiuYanMsgListActivity;
import com.bshuiban.teacher.contract.MySpaceContract;
import com.bshuiban.teacher.present.MySpacePresent;

/**
 * 我的空间
 */
public class MySpaceWebActivity extends BaseWebActivity<MySpacePresent>implements MySpaceContract.View,LiuYanMsgListContract.View {

    private String userId;
    private LiuYanMsgListParent liuYanMsgListParent;
    private boolean isSelf;
    private boolean oncreat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getStringExtra("userId");
        if(android.text.TextUtils.isEmpty(userId)){
            userId = User.getInstance().getUserId();
        }
        loadFileHtml("mySpace");
        tPresent=new MySpacePresent(this,userId);
        liuYanMsgListParent = new LiuYanMsgListParent(this,userId);
        liuYanMsgListParent.setLimit(3);
        isSelf = userId == User.getInstance().getUserId();
        MySpaceHtml object = new MySpaceHtml("", isSelf,liuYanMsgListParent);
        object.setOnListener(liuYanMsgListParent.getMessageListListener());
        registerWebViewH5Interface(object);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadSpaceHeadData();
        liuYanMsgListParent.getInterNetData();
    }

    @Override
    protected void onResume() {
        tPresent.loadMessageListInf(userId);
        super.onResume();
    }

    @Override
    public void updateSpaceHeadView(String json) {
        loadJavascriptMethod("getMsg",json);
    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("message",json);
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
    public void startReplyDialog(MessageBean.DataBean dataBean) {
        liuYanMsgListParent.startReplyDialog(getSupportFragmentManager(),this,dataBean,isSelf);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    class MySpaceHtml extends LiuYanMsgListParent.LiuYanMsgListHtml{
        public MySpaceHtml(String name, boolean isSelf, LiuYanMsgListParent liuYanMsgListParent) {
            super(name, isSelf, liuYanMsgListParent);
        }

        /**
         * 跳转页面
         * @param tag 0 主讲微课-查看全部；1 留言查看全部
         */
        @JavascriptInterface
        public void toNextPage(int tag,String userName){
            switch (tag){
                case 0:
                    toNextActivity(MainWeiClassWebActivity.class,userName);
                    break;
                default:
                    //goLiuYan();
                    toNextActivity(LiuYanMsgListActivity.class,userName);
            }
        }
        /**
         * 转码中
         * @param s
         */
        @JavascriptInterface
        public void toast(String s){
            runOnUiThread(()->MySpaceWebActivity.this.toast(s));
        }

        /**
         * 非转码，视频地址有效
         * @param url
         */
        @JavascriptInterface
        public void playVideo(String url){
            runOnUiThread(()-> PlayerVideoActivity.startPlayerVideoActivity(getApplicationContext(),url));
        }
    }
    private void toNextActivity(Class<?> cls,String s){
        runOnUiThread(()-> startActivity(new Intent(getApplicationContext(),cls).putExtra("userId",userId).putExtra("name",s)));
    }
}
