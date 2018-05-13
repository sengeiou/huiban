package com.example.nanohttpd_test;

import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Map<String, String> params=new HashMap<String, String>(){
            @Override
            public String toString() {
                Set<Entry<String, String>> entries = entrySet();
                Iterator<Entry<String, String>> iterator = entries.iterator();
                StringBuffer stringBuffer=new StringBuffer();
                do {
                    Entry<String, String> next = iterator.next();
                    String key = next.getKey();
                    String value = next.getValue();
                    if(stringBuffer.toString().length()==0){
                        stringBuffer.append("{");
                    }
                    stringBuffer.append("\""+key+"\":");
                    stringBuffer.append("\""+value+"\"");
                    if(!iterator.hasNext()){
                        stringBuffer.append("}");
                    }else{
                        stringBuffer.append(",");
                    }
                }while (iterator.hasNext());
                return stringBuffer.toString();
            }
        };
        //params.put("Stamp", (System.currentTimeMillis() / 1000) + "");
        params.put("Token", "656466465asdf");
        params.put("Module", "getModue");
        params.put("Handler", "isHandler");
        assertEquals(4, 2 + 2);
        OutputStream outputStream=null;
        URL url=new URL("http://192.168.0.152:8080/test?type=1");
        HttpURLConnection httpConnection= (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setDoOutput(true);
        httpConnection.setDoInput(true);
        httpConnection.setConnectTimeout(1000*600);
        httpConnection.setReadTimeout(1000*600);
        httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        outputStream = httpConnection.getOutputStream();
        String string = params.toString();
        System.out.println(string);
        outputStream.write(string.getBytes());
        outputStream.flush();
        outputStream.close();
        int responseCode = httpConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.err.println("成功");
            InputStream in = httpConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader bufr = new BufferedReader(isr);
            String str;
            while ((str = bufr.readLine()) != null) {
                System.out.println(str);
            }
            bufr.close();
            in.close();
        } else {
            System.err.println("失败");
        }
    }
}