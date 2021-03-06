package com.bshuiban.baselibrary.internet;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface BaseCall {
    /**
     * 加密数据获取
     * @param module key值
     * @param cipherText 密文（aes）
     * @return
     */
    @GET("interface/HuiBanApi.php")
    Call<ResponseBody> getCipherText(@Query("key") String module, @Query("param") String cipherText);
//    @POST("interface/HuiBanApi.php")
//    Call<ResponseBody> getCipherTextPost();
    /**
     * 单文件上传
     * @param description
     * @param file @Part MultipartBody.Part file 使用MultipartBody.Part类发送文件file到服务器
     * @return 状态信息String
     */
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadFile(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    /**
     * 文件下载
     * @param fileUrl 地址
     * @return
     */
    @GET
    @Streaming
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
