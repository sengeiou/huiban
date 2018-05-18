package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.contract.LessonInfContract;
import com.bshuiban.baselibrary.present.LessonInfPresent;
import com.bshuiban.baselibrary.utils.ViewUtils;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public class LessonInfActivity extends BaseWebActivity<LessonInfPresent> implements LessonInfContract.View{
    private final String HTML_FILE_NAME="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = ViewUtils.getFrameLayout(this);
        mWebView=getWebView(getApplicationContext());
        frameLayout.addView(mWebView);
        setContentView(frameLayout);
        tPresent=new LessonInfPresent(this);
        loadFileHtml(HTML_FILE_NAME);
        registerWebViewH5Interface(new LessonInfHtml());
    }

    @Override
    public void updateView(String json) {

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
    class LessonInfHtml{
        @JavascriptInterface
        public void getLessonInf(String id){
            tPresent.getLessonInf(id);
        }
        @JavascriptInterface
        public void playLesson(String playUrl){
            runOnUiThread(()->{
                //播放视频
            });
        }
    }
}
