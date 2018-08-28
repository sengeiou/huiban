package com.bshuiban.baselibrary.internet.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by xinheng on 2018/8/24.<br/>
 * describe：
 */
public interface LoadCall {
    /**
     * 文件下载
     * @param fileUrl 地址
     * @return
     */
    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
