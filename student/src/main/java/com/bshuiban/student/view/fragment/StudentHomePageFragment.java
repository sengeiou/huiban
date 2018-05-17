package com.bshuiban.student.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bshuiban.baselibrary.model.User;
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
        //loadFileHtml("student_homepage");
        loadFileHtml("index");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String userId = User.getInstance().getUserId();
                tPresent.getTodaySchedule(userId);
                tPresent.getHuiFuDaoTwoData();
                tPresent.getMessageList(userId);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                //return super.onConsoleMessage(consoleMessage);
                Log.e(TAG, "onConsoleMessage: "+consoleMessage.message()+", "+consoleMessage.sourceId()+", "+consoleMessage.lineNumber() );
                return false;
            }
        });
        return view;
    }

    @Override
    protected void delete(String messageId, String pid) {
        super.delete(messageId, pid);
    }

    @Override
    protected void addRecevier(String json) {
        super.addRecevier(json);
    }
}
