package com.bshuiban.baselibrary.internet;

import android.util.Log;

import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.utils.aes.AESUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitService {
    private static RetrofitService retrofitService;
    public static RetrofitService getInstance() {
        if(retrofitService==null){
            synchronized (RetrofitService.class){
                if(retrofitService==null){
                    retrofitService=new RetrofitService();
                }
            }
        }
        return retrofitService;
    }
    private RetrofitService(){}
    private static Gson gson=new Gson();
    private Retrofit getRetrofit(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(UrlManage.getInstance().getBASE_URL())
                .build();
        return retrofit;
    }
    private BaseCall getResponseBodyCall(){
        return getRetrofit().create(BaseCall.class);
    }
    public void getServiceResultForResString(String modu,String resString, CallResTest callback){
        //Log.e(TAG, "getServiceResultForResString: "+modu+", "+resString );
        retrofit2.Call<ResponseBody> cipherText = getResponseBodyCall().getCipherText(modu, resString);
        try {
            String string = cipherText.execute().body().string();
            String s = AESUtils.desEncrypt(string);
            System.out.print(s+"\n");
            callback.result(s);
        } catch (IOException e) {
            e.printStackTrace();
            callback.result(e.getMessage());
        }

    }
    public retrofit2.Call<ResponseBody> getServiceResult(String modu, Map<String ,Object> map, Callback<ResponseBody> callback){
        String json = gson.toJson(map);
        map.clear();
        return getServiceResult(modu, json,callback);
    }
    public retrofit2.Call<ResponseBody> getServiceResult(String modu, String data, Callback<ResponseBody> callback){
        System.out.print(data+"\n");
        String encrypt = AESUtils.encrypt(data);
        System.out.print(encrypt+"\n");
        retrofit2.Call<ResponseBody> cipherText = getResponseBodyCall().getCipherText(modu, encrypt);
        cipherText.enqueue(callback);
        return cipherText;
    }
    public static abstract class Call<T> implements Callback<ResponseBody> {
        private Class<T> tClass;
        public Call(Class<T> tClass){
            this.tClass=tClass;
        }
        @Override
        public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String string = response.body().string();
                String s = AESUtils.desEncrypt(string);
                T t = gson.fromJson(s, tClass);
                result(t);
            } catch (IOException e) {
                e.printStackTrace();
                error("parse-error");
            }
        }

        @Override
        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            if(null!=t){
                t.printStackTrace();
                error(t.getMessage());
            }else {
                error("internet-error");
            }
        }
        protected abstract void result(T t);
        protected abstract void error(String error);
    }
    public static abstract class CallResult<T extends ResultBean>extends Call<T>{

        public CallResult(Class<T> tClass) {
            super(tClass);
        }

        @Override
        protected void result(T t) {
            if(t!=null){
                if(t.isSuccess()){
                    success(t);
                }else{
                    error(t.getMsg());
                }
            }else{
                error("暂无数据");
            }
        }
        protected abstract void success(T t);
    }
    public static class CallResTest{
        protected void result(String s){

        };
    }
    public static abstract class CallHTML implements Callback<ResponseBody> {

        @Override
        public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
            String string = null;
            try {
                string = response.body().string();
                String s = AESUtils.desEncrypt(string);
                JsonElement parse = new JsonParser().parse(s);
                if(parse.isJsonObject()){
                    JsonObject jsonObject = parse.getAsJsonObject();
                    String code = jsonObject.get("code").getAsString();
                    if(ResultBean.isSuccess(code)){
                        JsonElement data = jsonObject.get("data");
                        if(null!=data&&data.isJsonArray()){
                            JsonArray asJsonArray = data.getAsJsonArray();
                            success(gson.toJson(asJsonArray));
                        }else{
                            success(null);
                        }
                    }else{
                        String error = jsonObject.get("msg").getAsString();
                        fail(error);
                        }
                }else{
                    Log.e(TAG, "onResponse:CallHTML "+s );
                    fail(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }
        protected abstract void success(String msg);
        protected abstract void fail(String error);
        @Override
        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            if(null!=t){
                fail(t.getMessage());
            }
        }
    }
    public static class CallTest implements Callback<ResponseBody> {

        @Override
        public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
            String string = null;
            try {
                string = response.body().string();
                String s = AESUtils.desEncrypt(string);
                System.out.print(s);
                result(s);
                //Log.e(TAG, "onResponse: result = "+s );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        protected void result(String s){

        };
        protected void error(String s){}
        @Override
        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            if(null!=t){
                t.printStackTrace();
            }
            //Log.e(TAG, "onResponse: result = 网络错误" );
            //System.out.print("网络错误");
            error("网络错误");
        }
    }
    private static final String TAG="TAG"+RetrofitService.class.getSimpleName();
}
