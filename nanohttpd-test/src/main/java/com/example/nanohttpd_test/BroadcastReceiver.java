package com.example.nanohttpd_test;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URLEncoder;

/**
 * Created by xinheng on 2017/11/29.
 * describe：
 */

public class BroadcastReceiver {
    public static void sendBroadCastToCenter(Context app,String data){
        WifiManager wifiMgr = (WifiManager) app.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        /*这里获取了IP地址，获取到的IP地址还是int类型的。*/
        int ip = wifiInfo.getIpAddress();
        /*这里就是将int类型的IP地址通过工具转化成String类型的，便于阅读
        String ips = Formatter.formatIpAddress(ip);
        */
        /*这一步就是将本机的IP地址转换成xxx.xxx.xxx.255*/
        int broadCastIP = ip | 0xFF000000;

        DatagramSocket theSocket = null;
        try {
            InetAddress server = InetAddress.getByName(Formatter.formatIpAddress(broadCastIP));
            theSocket = new DatagramSocket();
            //String data = "While are you like";

            byte[] bytes = data.getBytes();
            DatagramPacket theOutput = new DatagramPacket(bytes, bytes.length, server, 8091);
            /*这一句就是发送广播了，其实255就代表所有的该网段的IP地址，是由路由器完成的工作*/
            theSocket.send(theOutput);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (theSocket != null)
                theSocket.close();
            theSocket.disconnect();
            Log.e("TAg", "sendBroadCastToCenter: "+theSocket.isClosed()+", "+theSocket.isConnected() );
        }
    }
}
