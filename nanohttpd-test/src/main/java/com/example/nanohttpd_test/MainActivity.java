package com.example.nanohttpd_test;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import fi.iki.elonen.NanoHTTPD;

public class MainActivity extends AppCompatActivity implements VideoServer.OnCloseUDPListener{

    private static final String DEFAULT_FILE_PATH  = Environment.getExternalStorageDirectory() + "/movie.mp4";
    private static final int VIDEO_WIDTH  = 320;
    private static final int VIDEO_HEIGHT = 240;

    private TextView mTipsTextView;
    private VideoServer mVideoServer;
    private Button bt;
    private String[] answers={"","",""};
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTipsTextView = (TextView)findViewById(R.id.TipsTextView);
        bt = (Button)findViewById(R.id.bt);
        mVideoServer = new VideoServer(DEFAULT_FILE_PATH, VIDEO_WIDTH, VIDEO_HEIGHT, VideoServer.DEFAULT_SERVER_PORT);
        mVideoServer.setOnCloseUDPListener(this);
        LocalNetWork localNetWork = new LocalNetWork(VideoServer.DEFAULT_SERVER_PORT) {
            @Override
            public String getText() {
                return null;
            }

            @Override
            public Response getRespose(String uri) {
                return null;
            }
        };
        mTipsTextView.setText("请在远程浏览器中输入:\n\n"+getLocalIpStr(this)+":"+VideoServer.DEFAULT_SERVER_PORT);
        try {
            //mVideoServer.start();
            localNetWork.start(this);
        }
        catch (IOException e) {
            e.printStackTrace();
            mTipsTextView.setText(e.getMessage());
        }
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        BroadcastReceiver.sendBroadCastToCenter(getApplicationContext(),"发送成功哈农子女欧安大佛安抚of哪里呢服务器噢诶让你发吗");
                    }
                },500,2000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        mVideoServer.stop();
        super.onDestroy();
    }

    public static String getLocalIpStr(Context context) {
        WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return intToIpAddr(wifiInfo.getIpAddress());
    }

    private static String intToIpAddr(int ip) {
        return (ip & 0xff) + "." + ((ip>>8)&0xff) + "." + ((ip>>16)&0xff) + "." + ((ip>>24)&0xff);
    }

    @Override
    public void closeUdp() {
        timer.cancel();
        Log.e("TAG", "closeUdp: ");
    }
}
