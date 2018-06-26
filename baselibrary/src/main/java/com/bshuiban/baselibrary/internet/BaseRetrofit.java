package com.bshuiban.baselibrary.internet;

import com.google.gson.Gson;

import retrofit2.Retrofit;

/**
 * Created by xinheng on 2018/6/25.<br/>
 * describe：
 */
public class BaseRetrofit {
    protected Retrofit retrofit;
    protected static Gson gson = new Gson();
    protected Retrofit getRetrofit(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        return retrofit;
    }

}
