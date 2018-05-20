package com.bshuiban.student.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webFragment.HomePageFragment;

/**
 * Created by xinheng on 2018/5/15.<br/>
 * describe：学生首页
 * 1.获取今日学生课表  classList
 * 2.慧辅导两条数据 train
 * 3.留言列表 message
 *
 *
 * H5-android
 * 删除--deleteMessageItem
 * 回复--replayMessage
 * 加载更多--
 */
public class StudentHomePageFragment extends HomePageFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String userId = User.getInstance().getUserId();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setWebViewSetting(mWebView);
        StuHomePageHtml stuHomePageHtml=new StuHomePageHtml();
        stuHomePageHtml.setOnListener(new MessageList.MessageListListener() {
            @Override
            public void deleteMessageItem(String messageId, String pid) {
                tPresent.delete(messageId,pid);
            }

            @Override
            public void replayMessage(String json) {
                tPresent.addRecevier(json);
            }

            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }

            @Override
            public void refresh() {
                tPresent.refresh();
            }
        });
        loadFileHtml("homework");
        registerWebViewH5Interface(stuHomePageHtml);
        return view;
    }

    @Override
    protected void webViewLoadFinished() {
        String userId = User.getInstance().getUserId();
        tPresent.getTodaySchedule(userId);//今日课表
        tPresent.getHuiFuDaoTwoData();//慧辅导两条数据
        tPresent.getMessageList(userId);//留言列表
    }

    @Override
    protected void delete(String messageId, String pid) {
        super.delete(messageId, pid);
    }

    @Override
    protected void addRecevier(String json) {
        super.addRecevier(json);
    }
    private void toNextActivity(int type) {
        Log.e(TAG, "toNextActivity: type="+type );
        Class<?> cls;
        switch (type) {
            case 0://班级

                break;
            case 1://课表

                break;
            case 2://通知

                break;
            case 3://关注
                break;
            case 4: //慧辅导
                tPresent.getReplyMessage(1);
                break;
            default:
                //学习资源

        }
        //startActivity(new Intent(this, cls));
    }

    class StuHomePageHtml extends MessageList {
        /**
         * 进入下一个页面
         * @param type 0 班级，1 课表，2 通知，3 关注，4 慧辅导，5 学习资源，6 去留言
         */
        @JavascriptInterface
        public void toNextActivity(int type) {
            getActivity().runOnUiThread(() -> {
                StudentHomePageFragment.this.toNextActivity(type);
            });
        }
        @JavascriptInterface
        public void reply(int index){

        }
    }
}
