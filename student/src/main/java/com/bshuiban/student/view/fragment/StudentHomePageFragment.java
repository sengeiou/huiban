package com.bshuiban.student.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.webview.webFragment.HomePageFragment;

/**
 * Created by xinheng on 2018/5/15.<br/>
 * describe：学生首页
 * 1.获取今日学生课表  classList
 * 2.慧辅导两条数据 train
 * 3.留言列表 message
 */
public class StudentHomePageFragment extends HomePageFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String userId = User.getInstance().getUserId();
        String userId = "2030246";
        tPresent.getTodaySchedule(userId);
        tPresent.getHuiFuDaoTwoData();
        tPresent.getMessageList(userId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setWebViewSetting(mWebView);
        loadFileHtml("follow");
        return view;
    }
}
