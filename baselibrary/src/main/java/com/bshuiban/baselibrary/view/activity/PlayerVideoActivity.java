package com.bshuiban.baselibrary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bshuiban.baselibrary.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PlayerVideoActivity extends BaseActivity {

    public static void startPlayerVideoActivity(Context context,String videoPath){
        context.startActivity(new Intent(context,PlayerVideoActivity.class).putExtra("path",videoPath));
    }
    private JCVideoPlayerStandard jcVideoPlayerStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_video);
        String path = getIntent().getStringExtra("path");
        jcVideoPlayerStandard= (JCVideoPlayerStandard) findViewById(R.id.jiecao_Player);
        jcVideoPlayerStandard.setUp(path,jcVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"视频");
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
