package com.bshuiban.baselibrary.internet;

import java.io.File;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by xinheng on 2018/6/7.<br/>
 * describe：
 */
public class RetrofitUpload {
    private static RetrofitUpload retrofitService;
    private Retrofit retrofit;
    public static RetrofitUpload getInstance() {
        if (retrofitService == null) {
            synchronized (RetrofitUpload.class) {
                if (retrofitService == null) {
                    retrofitService = new RetrofitUpload();
                }
            }
        }
        return retrofitService;
    }

    private RetrofitUpload() {
        retrofit=getRetrofit();
    }
    private Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlManage.getInstance().getUploadUrl())
                .build();
        return retrofit;
    }
    public Call<ResponseBody> loadFile(String path,Callback<ResponseBody> callback){
        return loadFile(new File(path),callback);
    }
    public Call<ResponseBody> loadFile(File file, Callback<ResponseBody> callback){
        RequestBody requestFile =RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body =MultipartBody.Part.createFormData("img_file", UUID.randomUUID().toString()+file.getName(), requestFile);

        String descriptionString = "This is a image";
        RequestBody description =RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        Call<ResponseBody> responseBodyCall = retrofit.create(BaseCall.class).uploadFile(description,body);
        responseBodyCall.enqueue(callback);
        return responseBodyCall;
    }
}
