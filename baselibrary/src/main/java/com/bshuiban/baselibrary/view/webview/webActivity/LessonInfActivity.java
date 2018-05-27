package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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
    private final String HTML_FILE_NAME="courseDetails";
    private String courseId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courseId = getIntent().getStringExtra("courseId");
        tPresent=new LessonInfPresent(this);
        loadFileHtml(HTML_FILE_NAME);
        registerWebViewH5Interface(new LessonInfHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        if(!TextUtils.isEmpty(courseId))
            tPresent.getLessonInf(courseId);
        else {
            toast("标识-id，无效");
        }
    }

    @Override
    public void updateView(String json) {
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
    class LessonInfHtml{
        @JavascriptInterface
        public void getLessonInf(String id){
            tPresent.getLessonInf(id);
        }
        @JavascriptInterface
        public void playLesson(String playUrl){
            runOnUiThread(()->{
                //播放视频
                Log.e(TAG, "playLesson: "+playUrl );
            });
        }
    }
}
