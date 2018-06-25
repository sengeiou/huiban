package com.bshuiban.student.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.activity.ClassActivity;
import com.bshuiban.baselibrary.view.activity.ClassScheduleActivity;
import com.bshuiban.baselibrary.view.dialog.MessageDialog;
import com.bshuiban.baselibrary.view.pulltorefresh.BaseRefreshListener;
import com.bshuiban.baselibrary.view.pulltorefresh.PullToRefreshLayout;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webActivity.GuanZhuListActivity;
import com.bshuiban.baselibrary.view.webview.webActivity.HuiFuDaoListActivity;
import com.bshuiban.baselibrary.view.webview.webActivity.LessonInfWebActivity;
import com.bshuiban.baselibrary.view.webview.webActivity.LiuYanMsgListActivity;
import com.bshuiban.baselibrary.view.webview.webActivity.NoticeActivity;
import com.bshuiban.baselibrary.view.webview.webFragment.HomePageFragment;

/**
 * Created by xinheng on 2018/5/15.<br/>
 * describe：学生首页
 * 1.获取今日学生课表
 * 2.慧辅导两条数据
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
        loadJavascriptMethod("userimgs",User.getInstance().getUserData().getIcoPath());
    }

    private void toNextActivity(int type) {
        //Log.e(TAG, "toNextActivity: type="+type );
        Class<?> cls;
        switch (type) {
            // 0 班级，1 课表，2 通知，3 关注，4 慧辅导，5 学习资源，6 去留言
            case 0://班级
                cls= ClassActivity.class;
                break;
            case 1://课表
                cls=ClassScheduleActivity.class;
                break;
            case 2://通知
                cls= NoticeActivity.class;
                break;
            case 3://关注
                cls= GuanZhuListActivity.class;
                break;
            case 4: //慧辅导
                cls= HuiFuDaoListActivity.class;
                break;
            case 6:// 去留言
                cls=LiuYanMsgListActivity.class;
                break;
            case 7://慧辅导详情
                cls=LessonInfWebActivity.class;
                break;
            default://5
                //学习资源
                try {
                    cls=Class.forName("com.bshuiban.teacher.view.webView.webActivity.LessonListActivity");
                } catch (ClassNotFoundException e) {
                    cls=null;
                    e.printStackTrace();
                }
        }
        if(null!=cls) {
            startActivity(new Intent(getActivity(), cls));
        }
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
        public void toNextHuiFuActivity(String courseId) {
            getActivity().runOnUiThread(() -> startActivity(new Intent(getActivity(),LessonInfWebActivity.class).putExtra("courseId",courseId)));
        }
        @JavascriptInterface
        public void toggleSlide(){
            getActivity().runOnUiThread(()->{
                mListener.transportData("toggleSlide");
            });
        }
        @JavascriptInterface
        public void reply(int index){
            getActivity().runOnUiThread(()->{
                tPresent.getReplyMessage(index);
            });
        }
        @JavascriptInterface
        @Override
        public void getMoreData(boolean action) {
            if(action) {
                tPresent.getTodaySchedule(User.getInstance().getUserId());
            }
            super.getMoreData(action);
        }
    }
}
