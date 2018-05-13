package com.bshuiban.baselibrary.internet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseCall {
    //key param
    @GET("interface/HuiBanApi.php")
    Call<ResponseBody> getCipherText(@Query("key") String module, @Query("param") String cipherText);
}
