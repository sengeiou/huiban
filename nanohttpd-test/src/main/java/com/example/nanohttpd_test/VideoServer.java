package com.example.nanohttpd_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.util.Log;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

/**
 * Created by xinheng on 2017/11/27.
 * describe：
 */

public class VideoServer extends NanoHTTPD {

    public static final int DEFAULT_SERVER_PORT = 8080;
    public static final String TAG = VideoServer.class.getSimpleName();

    private static final String REQUEST_ROOT = "/";

    private String mVideoFilePath;
    private int mVideoWidth = 0;
    private int mVideoHeight = 0;

    public VideoServer(String filepath, int width, int height, int port) {
        super(DEFAULT_SERVER_PORT);
        mVideoFilePath = filepath;
        mVideoWidth = width;
        mVideoHeight = height;
    }
    private OnCloseUDPListener onCloseUDPListener;
    public void setOnCloseUDPListener(OnCloseUDPListener onCloseUDPListener){
        this.onCloseUDPListener=onCloseUDPListener;
    }
    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Log.d(TAG, "OnRequest: " + uri);
        if (REQUEST_ROOT.equals(uri)) {
            return responseRootPage(session);
        } else if ("/test".equals(uri)) {
            return responseText(session);
        } else if (mVideoFilePath.equals(uri)) {
            return responseVideoStream(session);
        }
        return response404(session, uri);
        //return super.serve(session);
    }

    private Response responseText(IHTTPSession session) {
        Log.e(TAG, "responseText: "+session.getClass() );
        Map<String, String> headers = session.getHeaders();
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            Log.e(TAG, "responseText: header key=" + key + ", value=" + value);
        }
        CookieHandler cookies = session.getCookies();
        Iterator<String> iterator1 = cookies.iterator();
        while (iterator1.hasNext()) {
            String next = iterator1.next();
            Log.e(TAG, "responseText: cookies values=" + next);
        }

        Map<String, String> parms = session.getParms();
        try {
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            session.parseBody(stringStringHashMap);
            Set<Map.Entry<String, String>> entriesParms = stringStringHashMap.entrySet();
            Iterator<Map.Entry<String, String>> iterator2 = entriesParms.iterator();
            while (iterator2.hasNext()) {
                Map.Entry<String, String> next = iterator2.next();
                String key = next.getKey();
                String value = next.getValue();
                Log.e(TAG, "responseText:stringStringHashMap parms key=" + key + ", value=" + value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }

        Set<Map.Entry<String, String>> entriesParms = parms.entrySet();
        Iterator<Map.Entry<String, String>> iterator2 = entriesParms.iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, String> next = iterator2.next();
            String key = next.getKey();
            String value = next.getValue();
            Log.e(TAG, "responseText: parms key=" + key + ", value=" + value);
            if(key.equals("udp_statue")&&value.equals("close")){
                if(onCloseUDPListener!=null){
                    onCloseUDPListener.closeUdp();
                }
            }
        }
        Method method = session.getMethod();
        Log.e(TAG, "responseText: method=" + method.name());
        Log.e(TAG, "responseText: QueryParameterString " + session.getQueryParameterString());
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html><html><body>");
        builder.append("<video ");
        builder.append("width=" + getQuotaStr(String.valueOf(mVideoWidth)) + " ");
        builder.append("height=" + getQuotaStr(String.valueOf(mVideoHeight)) + " ");
        builder.append("controls>");
        builder.append("<source src=" + getQuotaStr("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4") + " ");
        builder.append("type=" + getQuotaStr("video/mp4") + ">");
        builder.append("Your browser doestn't support HTML5");
        builder.append("</video>");
        builder.append("</body></html>\n");
        return new Response(builder.toString());
    }

    private Response responseRootPage(IHTTPSession session) {
        File file = new File(mVideoFilePath);
        if (!file.exists()) {
            return response404(session, mVideoFilePath);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html><html><body>");
        builder.append("<video ");
        builder.append("width=" + getQuotaStr(String.valueOf(mVideoWidth)) + " ");
        builder.append("height=" + getQuotaStr(String.valueOf(mVideoHeight)) + " ");
        builder.append("controls>");
        builder.append("<source src=" + getQuotaStr(mVideoFilePath) + " ");
        builder.append("type=" + getQuotaStr("video/mp4") + ">");
        builder.append("Your browser doestn't support HTML5");
        builder.append("</video>");
        builder.append("</body></html>\n");
        return new Response(builder.toString());
    }

    public Response responseVideoStream(IHTTPSession session) {
        try {
            FileInputStream fis = new FileInputStream(mVideoFilePath);
            return new NanoHTTPD.Response(Status.OK, "video/mp4", fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return response404(session, mVideoFilePath);
        }
    }

    public Response response404(IHTTPSession session, String url) {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html><html><body>");
        builder.append("Sorry, Can't Found " + url + " !");
        builder.append("</body></html>\n");
        return new Response(builder.toString());
    }


    protected String getQuotaStr(String text) {
        return "\"" + text + "\"";
    }
    interface OnCloseUDPListener{
        void closeUdp();
    }
}
