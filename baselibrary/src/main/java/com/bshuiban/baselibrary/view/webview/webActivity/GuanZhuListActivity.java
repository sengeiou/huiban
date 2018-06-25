package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.GuanZhuListContract;
import com.bshuiban.baselibrary.present.GuanZhuListPresent;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;

/**
 * 关注列表
 */
public class GuanZhuListActivity extends BaseWebActivity<GuanZhuListPresent> implements GuanZhuListContract.View {
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tPresent = new GuanZhuListPresent(this);
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
        mWebView.addJavascriptInterface(object, "android");
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.getInterNetData();
    }

    @Override
    public void goLiuYan(String name, String userId) {
        Intent intent = new Intent(this, LiuYanMsgListActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }


    @Override
    public void guanZhuResult(boolean tag) {
        if (tag) {
            loadJavascriptMethod("removeType", String.valueOf(index));
            toast("添加关注成功");
        } else {
            loadJavascriptMethod("addType", String.valueOf(index));
            toast("取消关注成功");
        }
    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("getContent", json);
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

    class GuanZhuList extends MessageList {
        @JavascriptInterface
        public void goLiuYan(String name, String userId) {
            handler.post(() -> {
                GuanZhuListActivity.this.goLiuYan(name, userId);
            });
        }

        /**
         * 关注
         */
        @JavascriptInterface
        public void dealWithAttention(boolean tag, String id, int index) {
            tPresent.guanZhu(tag, id);
            GuanZhuListActivity.this.index = index;
        }

        @JavascriptInterface
        public void search(String msg) {
            tPresent.getSearchData(msg);
        }
    }
}
