package com.example.nanohttpd_test;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.utils.aes.AESUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by xinheng on 2017/11/29.
 * describe：
 */

public abstract class LocalNetWork extends NanoHTTPD {

    private Timer timer;

    public LocalNetWork(int port) {
        this(null, port);
    }

    public LocalNetWork(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Log.e("TAG", "serve: " + uri);
        Response response = null;
        Map<String, String> stringStringHashMap = session.getParms();
        String key = stringStringHashMap.get("key");
        String param = stringStringHashMap.get("param");
        response = askInternet(key, param);
        response.addHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    private Response askInternet(String key, String param) {
        final Response[] responses = new Response[1];
        Log.e("TAG", "askInternet: "+key+", "+param );
        RetrofitService.getInstance().getServiceResultForResString(key, AESUtils.encrypt(param), new RetrofitService.CallResTest() {
            @Override
            protected void result(String s) {
                responses[0] = new Response(s);
            }
        });
        return responses[0];
    }

    public void start(Context c) throws IOException {
        start();
        //startNetWorkBroadCast(c,null);
    }

    public Response getFileResponse(String path) {
        //closeTime();
        //File file = new File(Environment.getExternalStorageDirectory()+"/movie.mp4");
        File file = new File(path);
        try {
            InputStream inputStream = new FileInputStream(file);
            return new Response(NanoHTTPD.Response.Status.OK, "video/mp4", inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Response("{\"code\":0,\"message\":\"FileNotFound\"}");
        }
    }

    public abstract String getText();

    public abstract Response getRespose(String uri);

    private Response getTextResponse() {
        return new Response(getText());
    }

    public Response response404(String uri) {
        return new Response("{\"code\":0,\"message\":\"Sorry, Can't Found " + uri + " !\"}");
    }

    public Response getCloseResponse() {
        String s = "{\"code\":1,\"message\":\"成功\"}";
        closeTime();
        return new Response(s);
    }

    private void closeTime() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void startNetWorkBroadCast(Context context, final String text) {
        final int ip = getLocalIp(context.getApplicationContext());
        timer = new Timer("NetWorkBroadCast");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendBroadCastToNetWork(ip, text);
            }
        }, 500, 2000);
    }

    private void sendBroadCastToNetWork(int ip, String data) {
        int broadCastIP = ip | 0xFF000000;
        DatagramSocket theSocket = null;
        try {
            InetAddress server = InetAddress.getByName(Formatter.formatIpAddress(broadCastIP));
            theSocket = new DatagramSocket();
            //String data = "While are you like";
            if (data == null) {
                data = "test";
            }
            byte[] bytes = data.getBytes();
            DatagramPacket theOutput = new DatagramPacket(bytes, bytes.length, server, 8091);
            /*这一句就是发送广播了，其实255就代表所有的该网段的IP地址，是由路由器完成的工作*/
            theSocket.send(theOutput);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (theSocket != null)
                theSocket.close();
            Log.e("TAg", "sendBroadCastToCenter: " + theSocket.isClosed() + ", " + theSocket.isConnected());
        }
    }

    public static int getLocalIp(Context c) {
        WifiManager wifiMgr = (WifiManager) c.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        /*这里获取了IP地址，获取到的IP地址还是int类型的。*/
        int ip = wifiInfo.getIpAddress();
        return ip;
    }
    /*private OnCloseNetWorkBroadCastListener l;
    public void setOnCloseNetWorkBroadCastListener(OnCloseNetWorkBroadCastListener l){
        this.l=l;
    }
    interface OnCloseNetWorkBroadCastListener{
        void closeUdp();
    }*/
}
