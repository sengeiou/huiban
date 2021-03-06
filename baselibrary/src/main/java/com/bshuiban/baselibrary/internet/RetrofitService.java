package com.bshuiban.baselibrary.internet;

import android.text.TextUtils;
import android.util.Log;

import com.bshuiban.baselibrary.model.LogUtils;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.utils.aes.AESUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitService extends BaseRetrofit {
    private static RetrofitService retrofitService;

    public static RetrofitService getInstance() {
        if (retrofitService == null) {
            synchronized (RetrofitService.class) {
                if (retrofitService == null) {
                    retrofitService = new RetrofitService();
                }
            }
        }
        return retrofitService;
    }

    private RetrofitService() {
        retrofit = getRetrofit(UrlManage.getInstance().getBASE_URL());
    }

    private BaseCall getResponseBodyCall() {
        return retrofit.create(BaseCall.class);
    }

    public void getServiceResultForResString(String modu, String resString, CallResTest callback) {
        //Log.e(TAG, "getServiceResultForResString: "+modu+", "+resString );
        retrofit2.Call<ResponseBody> cipherText = getResponseBodyCall().getCipherText(modu, resString);
        try {
            String string = cipherText.execute().body().string();
            String s = AESUtils.desEncrypt(string);
            System.out.print(s + "\n");
            callback.result(s);
        } catch (IOException e) {
            e.printStackTrace();
            callback.result(e.getMessage());
        }

    }

    /**
     * json数据访问
     * @param modu key值
     * @param map map数据集合
     * @param callback 回调
     * @return 接口响应
     */
    public retrofit2.Call<ResponseBody> getServiceMapResult(String modu, Map<String, Object> map, Callback<ResponseBody> callback) {
        String json = gson.toJson(map);
        map.clear();
        return getServiceResult(modu, json, callback);
    }

    /**
     * json数据访问
     * @param modu key值
     * @param data json串
     * @param callback 回调
     * @return 接口响应
     */
    public retrofit2.Call<ResponseBody> getServiceResult(String modu, String data, Callback<ResponseBody> callback) {
        String encrypt = null;
        if (!android.text.TextUtils.isEmpty(data)) {
            Log.e(TAG, "getServiceResult: " + modu + ", " + data);
        } else {
            data = "{\"xxx\":\"xxx\"}";
        }
        encrypt = AESUtils.encrypt(data);
        Log.e(TAG, "getServiceResult: " + encrypt);
        retrofit2.Call<ResponseBody> cipherText = getResponseBodyCall().getCipherText(modu, encrypt);
        cipherText.enqueue(callback);
        return cipherText;
    }

    /**
     * 明文
     *
     * @param <T>
     */
    public abstract static class CallPlaintext<T> implements Callback<ResponseBody> {
        private Class<T> tClass;

        public CallPlaintext(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String s = response.body().string();
                //Log.e(TAG, "onResponse: " + string);
                if (null != s && s.length() > 0) {
                    if (";".equals(String.valueOf(s.charAt(s.length() - 1)))) {
                        s = s.substring(0, s.length() - 1);
                    }
                }
                LogUtils.e(TAG, "onResponse: " + s);
                T t = gson.fromJson(s, tClass);
                result(t);
            } catch (Exception e) {
                e.printStackTrace();
                error("parse-error");
            }
        }

        protected abstract void error(String s);

        protected abstract void result(T t);

        @Override
        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            if (null != t) {
                t.printStackTrace();
            }
            error("访问网络失败");
        }
    }

    public static abstract class Call<T> implements Callback<ResponseBody> {
        protected Class<T> tClass;

        public Call(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String string = response.body().string();
                //Log.e(TAG, "onResponse: " + string);
                String s = AESUtils.desEncrypt(string).trim();
                HttpUrl url = call.request().url();
                String key = url.queryParameter("key");
                String param = url.queryParameter("param");
                if (!TextUtils.isEmpty(param)) {
                    param = AESUtils.desEncrypt(param);
                }
                Log.e(TAG, "参数: key=" + key + ", param=" + param);
                LogUtils.e(TAG, "结果: " + s);
                T t = gson.fromJson(s, tClass);
                result(t);
            } catch (Exception e) {
                e.printStackTrace();
                error("parse-error");
            }
        }

        @Override
        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            if (null != t) {
                t.printStackTrace();
                error("访问网络失败");
            } else {
                error("internet-error");
            }
        }

        protected abstract void result(T t);

        protected abstract void error(String error);
    }

    public static abstract class CallResult<T extends ResultBean> extends Call<T> {

        public CallResult(Class<T> tClass) {
            super(tClass);
        }

        @Override
        protected void result(T t) {
            if (t != null) {
                if (t.isSuccess()) {
                    success(t);
                } else {
                    String msg = t.getMsg();
//                    if(msg.contains("暂无")){
//                        createEmptyTClass();
//                    }else {
                        error(msg);
//                    }
                }
            } else {
                error("暂无数据");
                //createEmptyTClass();
            }
        }

        private void createEmptyTClass() {
            T t;
            try {
                t = tClass.newInstance();
                success(t);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        /**
         * 成功结果
         *
         * @param t 不肯能为null
         */
        protected abstract void success(T t);
    }

    public static class CallResTest {
        protected void result(String s) {

        }
    }

    public static abstract class CallHTMLJsonArray implements Callback<ResponseBody> {
        @Override
        public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
            Request request = call.request();
            String string = null;
            if (LogUtils.mTag) {
                HttpUrl url = request.url();
                String key = url.queryParameter("key");
                String param = url.queryParameter("param");
                //String host = url.host();
                if (!TextUtils.isEmpty(param)) {
                    param = AESUtils.desEncrypt(param);
                }
                Log.e(TAG, "onResponse: key=" + key + ", param=" + param);
            }
            try {
                string = response.body().string();
                String s = AESUtils.desEncrypt(string);
                Log.e(TAG, "onResponse: " + s);
                JsonElement parse = new JsonParser().parse(s);
                if (parse.isJsonObject()) {
                    JsonObject jsonObject = parse.getAsJsonObject();
                    String code = jsonObject.get("code").getAsString();
                    if (ResultBean.isSuccess(code)) {
                        JsonElement data = jsonObject.get("data");
                        if (null != data && data.isJsonArray()) {
                            JsonArray asJsonArray = data.getAsJsonArray();
                            success(asJsonArray);
                        } else {
                            success(null);
                        }
                    } else {
                        String error = jsonObject.get("msg").getAsString();
                        if(null!=error&&error.contains("暂无")){
                            success(null);
                            return;
                        }
                        fail(error);
                    }
                } else {
                    Log.e(TAG, "onResponse:CallHTML " + s);
                    fail(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }

        @Override
        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            if(null!=t)
                t.printStackTrace();
            fail("访问网络失败");
        }

        /**
         * 接口数据提示成功
         *
         * @param msg 数据内容
         */
        protected abstract void success(JsonArray msg);

        /**
         * 失败
         *
         * @param error 错误原因
         */
        protected abstract void fail(String error);
    }

    public static abstract class CallHTML implements Callback<ResponseBody> {

        @Override
        public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
            Request request = call.request();
            String string;
            HttpUrl url = request.url();
            String key = url.queryParameter("key");
            String param = url.queryParameter("param");
            //String host = url.host();
            //LogUtils.e(TAG, "onResponse: url=" + url.toString());
            LogUtils.e(TAG, "onResponse: key=" + key + ", param=" + AESUtils.desEncrypt(param));
            try {
                string = response.body().string();
                String s = AESUtils.desEncrypt(string);
                Log.e(TAG, "onResponse: " + s);
                JsonElement parse = new JsonParser().parse(s);
                if (parse.isJsonObject()) {
                    JsonObject jsonObject = parse.getAsJsonObject();
                    String code = jsonObject.get("code").getAsString();
                    if (ResultBean.isSuccess(code)) {
                        JsonElement data = jsonObject.get("data");
                        if (null != data) {
                            if (data.isJsonArray()) {
                                JsonArray asJsonArray = data.getAsJsonArray();
                                if (null != asJsonArray && asJsonArray.size() > 0) {
                                    success(gson.toJson(asJsonArray));
                                } else {
                                    success(null);
                                }
                            } else {
                                success(gson.toJson(data));
                            }
                        } else {
                            success(null);
                        }
                    } else {
                        String error = jsonObject.get("msg").getAsString();
                        if(null!=error&&error.contains("暂无")){
                            success(null);
                            return;
                        }
                        fail(error);
                    }
                } else {
                    Log.e(TAG, "onResponse:CallHTML " + s);
                    fail(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }
        /**
         * 接口数据提示成功
         *
         * @param msg 数据内容
         */
        protected abstract void success(String msg);

        /**
         * 失败
         *
         * @param error 错误原因
         */
        protected abstract void fail(String error);

        @Override
        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            if (null != t) {
                t.printStackTrace();
                fail("访问网络失败");
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
                //LogUtils.e(TAG, "onResponse: result = "+s );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        protected void result(String s) {

        }

        ;

        protected void error(String s) {
        }

        @Override
        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            if (null != t) {
                t.printStackTrace();
            }
            //Log.e(TAG, "onResponse: result = 网络错误" );
            //System.out.print("网络错误");
            error("访问网络失败");
        }
    }

    private static final String TAG = "TAG" + RetrofitService.class.getSimpleName();
}
