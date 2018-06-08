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
import com.bshuiban.baselibrary.view.activity.PlayerVideoActivity;
import com.bshuiban.baselibrary.view.activity.VideoPlayerActivity;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.UserTypeHtml;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：课程详情
 */
public class LessonInfWebActivity<T extends LessonInfPresent> extends BaseWebActivity<T> implements LessonInfContract.View{
    private final String HTML_FILE_NAME="courseDetails";
    protected String courseId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseId = getIntent().getStringExtra("courseId");
        tPresent=getPresent();
        loadFileHtml(getHtmlFileName());
        registerWebViewH5Interface(getRegisterWebViewH5Class());
    }
    protected String getHtmlFileName(){
        return HTML_FILE_NAME;
    }
    protected Object getRegisterWebViewH5Class(){
        return new LessonInfHtml();
    }
    protected T getPresent(){
        return (T) new LessonInfPresent(this);
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
        loadJavascriptMethod("getContent",(json));
    }

    @Override
    public void collectSuccess(String tag) {
        loadJavascriptMethod("isCollect",tag);
    }

    @Override
    public void recommendSuccess() {

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
    protected class LessonInfHtml extends UserTypeHtml{
        /**
         * 收藏
         * @param tag 添加收藏 true；取消收藏 false
         */
        @JavascriptInterface
        public void makeCollect( boolean tag){
            if(tag) {
                tPresent.addCollect(courseId);
            }else {
                tPresent.deleteCollect(courseId);
            }
        }
        @JavascriptInterface
        public void playLesson(String playUrl){
            runOnUiThread(()->{
                //播放视频
                Log.e(TAG, "playLesson: "+playUrl );
                PlayerVideoActivity.startPlayerVideoActivity(getApplicationContext(),playUrl);
            });
        }
    }
}
