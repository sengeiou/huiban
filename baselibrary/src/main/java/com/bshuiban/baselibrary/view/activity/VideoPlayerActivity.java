package com.bshuiban.baselibrary.view.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bshuiban.baselibrary.R;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoPlayerActivity extends BaseActivity {
    public static final int URL_FOR_WEB = 1;//网页
    public static final int URL_FOR_FILE = 2;//本地文件地址
    private VideoView surface_view;
    private WebView webview_player;
    private final MHandler handler = new MHandler(this);
    public static void startVideoActivity(Context context, String url){
        Bundle bundle = new Bundle();
        bundle.putInt("url_type", VideoPlayerActivity.URL_FOR_FILE);
        bundle.putString("video_url", url);
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    private static class MHandler extends Handler {
        private final WeakReference<VideoPlayerActivity> mActivity;

        public MHandler(VideoPlayerActivity activity) {
            mActivity = new WeakReference<VideoPlayerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            VideoPlayerActivity activity = mActivity.get();
            Log.e("---", msg.obj.toString());
            activity.play(msg.obj.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Vitamio.isInitialized(this);
        webview_player = (WebView) findViewById(R.id.webview_player);
        surface_view = (VideoView) findViewById(R.id.surface_view);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("video_url");
        switch (bundle.getInt("url_type")) {
            case URL_FOR_WEB:
                initWebView(url);
                break;
            case URL_FOR_FILE:
                play(url);
                break;
        }

    }

    private void initWebView(String html) {
        webview_player.getSettings().setJavaScriptEnabled(true);
        webview_player.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webview_player.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                super.onPageFinished(view, url);
            }
        });
        webview_player.loadUrl(html);
    }

    /**
     * 播放视频
     */
    private void play(String url) {
        if (TextUtils.isEmpty(url)) {
            toast( "视频地址有误...");
            finish();
            return;
        }
        if (url.contains(".swf")) {
            toast( "Flash资源暂不支持播放");
            finish();
        } else {
            toast( "加载中,请稍后...");
            surface_view.setVideoPath(url);
            Log.e("---", "播放地址:" + url);
            MediaController controller = new MediaController(this);

            surface_view.setMediaController(controller);
            surface_view.requestFocus();

            surface_view.start();
            surface_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @SuppressLint("CheckResult")
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
//                    surface_view.start();
                    //todo m3u8视频无法自动播放，，延迟1S后在启动
                    Flowable.timer(1000, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    if (!surface_view.isPlaying()) {
                                        Log.e("---", "播放");
                                        surface_view.start();
                                    }
                                }
                            });
                }
            });
        }
        surface_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            int start = html.indexOf("http");
            int end = html.indexOf("</body>");
            String path = html.substring(start, end).toString().replace("&amp;", "&");
            Log.e("TAG", "showSource: =="+html );
            Log.e("TAG", "showSource: =="+path );
            handler.obtainMessage(0, path).sendToTarget();
        }
    }
}
