package com.bshuiban.teacher.view.webView.webFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webFragment.BaseWebFragment;
import com.bshuiban.baselibrary.view.webview.webFragment.InteractionBaseWebViewFragment;
import com.bshuiban.teacher.contract.PrepareLessonsContract;
import com.bshuiban.teacher.present.PrepareLessonsPresent;
import com.bshuiban.teacher.view.activity.PrepareLessonInfActivity;
import com.bshuiban.teacher.view.webView.webActivity.FilterConditionActivity;

import static com.bshuiban.teacher.view.activity.PrepareLessonInfActivity.PREID;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：备课
 */
public class PrepareLessonsWebFragment extends InteractionBaseWebViewFragment<PrepareLessonsPresent> implements PrepareLessonsContract.View {
    private static final int SEARCH_REQUESTCODE=111;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new PrepareLessonsPresent(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFileHtml("classList");
        PrepareLessonsHtml lessonsHtml = new PrepareLessonsHtml();
        lessonsHtml.setOnListener(new MessageList.OnMessageListListener(){
            @Override
            public void refresh() {
                tPresent.refresh();
            }

            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }
        });
        registerWebViewH5Interface(lessonsHtml);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadLessons(null);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SEARCH_REQUESTCODE:
                if(null!=data){
                    String text = data.getStringExtra("text");
                    tPresent.loadLessons(text);
                }
                break;
        }
    }

    @Override
    public void update(Bundle bundle) {

    }

    class PrepareLessonsHtml extends MessageList{
        /**
         * 跳转搜索页面
         */
        @JavascriptInterface
        public void toSearchPage(){
            getActivity().runOnUiThread(()->{
                startActivityForResult(new Intent(getActivity(),FilterConditionActivity.class),SEARCH_REQUESTCODE);
            });
        }

        /**
         * item点击
         * @param preId
         */
        @JavascriptInterface
        public void itemClick(String preId){
            startActivity(new Intent(getActivity(),PrepareLessonInfActivity.class).putExtra(PREID,preId));
        }
    }
}
