package com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by xinheng on 2018/5/17.<br/>
 * describe：列表
 */
public class MessageList {
    protected Handler handler;
    private MessageListListener onListener;
    private ListRunnable runnable;

    public MessageList() {
        handler = new Handler(Looper.myLooper());
    }

    private static final String TAG = "HTML5";

    @JavascriptInterface
    public void log(String msg) {
        Log.e(TAG, "log: " + msg);
    }

    /**
     * 删除消息
     *
     * @param messageId 消息id
     * @param pid       上级id
     */
    @JavascriptInterface
    public void deleteMessageItem(final String messageId, final String pid) {
        handler.post(getListRunnable().setMessageId(messageId, pid));
    }

    /**
     * 回复消息
     *
     * @param json
     */
    @JavascriptInterface
    public void replayMessage(final String json) {
        handler.post(getListRunnable().setJson(json));
    }

    /**
     * 列表获取更多数据
     *
     * @param action true-->下拉刷新，false-->上拉加载
     */
    @JavascriptInterface
    public void getMoreData(boolean action) {
        if (action) {
            handler.post(getListRunnable().setType(3));
        } else {
            handler.post(getListRunnable().setType(2));
        }
    }

    public void setOnListener(MessageListListener onListener) {
        this.onListener = onListener;
    }

    private ListRunnable getListRunnable() {
        if (null == runnable) {
            return new ListRunnable();
        }
        return runnable;
    }

    class ListRunnable implements Runnable {
        private int type;
        private String messageId, pid, json;

        public ListRunnable setType(int type) {
            this.type = type;
            return this;
        }

        public ListRunnable setMessageId(String messageId, String pid) {
            this.messageId = messageId;
            this.pid = pid;
            setType(0);
            return this;
        }

        public ListRunnable setJson(String json) {
            this.json = json;
            setType(1);
            return this;
        }

        @Override
        public void run() {
            if (null == onListener) {
                return;
            }
            switch (type) {
                case 0:
                    onListener.deleteMessageItem(messageId, pid);//0
                    break;
                case 1:
                    onListener.replayMessage(json);
                    break;
                case 2:
                    onListener.loadMore();
                    break;
                case 3:
                    onListener.refresh();
                    break;
                default:

            }
        }

    }
    public static class OnMessageListListener implements MessageListListener{

        @Override
        public void deleteMessageItem(String messageId, String pid) {

        }

        @Override
        public void replayMessage(String json) {

        }

        @Override
        public void loadMore() {

        }

        @Override
        public void refresh() {

        }
    }
    public interface MessageListListener {
        void deleteMessageItem(String messageId, String pid);//0

        void replayMessage(String json);//1

        void loadMore();//2

        void refresh();//3
    }
}
