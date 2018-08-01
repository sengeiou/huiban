package com.bshuiban.baselibrary.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.DialogUtils;
import com.bshuiban.baselibrary.view.customer.FullJCVideoPlayer;
import com.bshuiban.baselibrary.view.dialog.MessageDialog;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PlayerVideoActivity extends BaseActivity {

    private MessageDialog messageDialog;

    public static void startPlayerVideoActivity(Context context, String videoPath){
        Intent path = new Intent(context, PlayerVideoActivity.class).putExtra("path", videoPath);
        if(context instanceof Activity) {
            context.startActivity(path);
        }else {
            path.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(path);
        }
    }
    private FullJCVideoPlayer jcVideoPlayerStandard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_video);
        String path = getIntent().getStringExtra("path");
        Log.e("TAG", "onCreate: video_path="+path );
        jcVideoPlayerStandard= (FullJCVideoPlayer) findViewById(R.id.jiecao_Player);
        if(TextUtils.isEmpty(path)||path.equals("undefined")||path.equals("null")){
            //Toast.makeText(context,"播放路径错误",Toast.LENGTH_SHORT).show();
            messageDialog = new MessageDialog(this)
                    .setTypeMessage("播放路径无效", MessageDialog.TYPE_MESSAGE_SURE, Gravity.CENTER)
            .setOnClickListenerSure(v -> finish());
            messageDialog.setCancelable(false);
            messageDialog.show();
            return;
        }
        jcVideoPlayerStandard.setUp(path,jcVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN,"视频");
        jcVideoPlayerStandard.setOnBackListener(()->finish());
    }

    /*@Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String path = getIntent().getStringExtra("path");
        jcVideoPlayerStandard= (FullJCVideoPlayer) findViewById(R.id.jiecao_Player);
        jcVideoPlayerStandard.setUp(path,jcVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN,"视频");
        jcVideoPlayerStandard.setOnBackListener(()->finish());
    }*/

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            //return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
