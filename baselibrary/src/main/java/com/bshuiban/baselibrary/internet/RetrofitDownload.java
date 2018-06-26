package com.bshuiban.baselibrary.internet;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.User;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static com.bshuiban.baselibrary.model.LogUtils.TAG;

/**
 * Created by xinheng on 2018/6/25.<br/>
 * describe：
 */
public class RetrofitDownload {
    private final Retrofit retrofit;
    private ProgressResponseBody.ProgressListener mProgressListener;
    private ProgressDialog dialog;

    public RetrofitDownload() {
        retrofit = getRetrofit();
        mProgressListener=(progress, total, done) -> {
            Log.e(TAG, (Looper.myLooper())+ "");
            Log.e(TAG, "onProgress: " + "total ---->" + total + " done ---->" + progress+", 进度："+progress*100f/total+"%");
        };
    }

    public RetrofitDownload(ProgressResponseBody.ProgressListener progressListener) {
        retrofit = getRetrofit();
        this.mProgressListener = progressListener;
    }

    private Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                //拦截
                .client(new OkHttpClient.Builder().addNetworkInterceptor(chain -> {
                    Response response = chain.proceed(chain.request());
                    return response.newBuilder()
                            .body(new ProgressResponseBody(response.body(), mProgressListener)).build();
                }).build())
                .baseUrl(UrlManage.getInstance().getBASE_URL())
                .build();
        return retrofit;
    }

    public Call<ResponseBody> downloadFile(String downloadUrl) {
        Call<ResponseBody> responseBodyCall = retrofit.create(BaseCall.class).downloadFile(downloadUrl);
        return responseBodyCall;
    }

    public void downloadFile(String dowunloadUrl, String saveFilePath) {
        Call<ResponseBody> responseBodyCall = downloadFile(dowunloadUrl);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                FileOutputStream fos = null;
                BufferedInputStream bis = null;
                InputStream is = null;
                File file = new File(saveFilePath);
                if (file.exists()) {
                    file.getAbsoluteFile().delete();
                }else{
                    file.getParentFile().mkdirs();
                }
                try {
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (null != fos)
                            fos.close();
                        if (null != bis)
                            bis.close();
                        if (null != is)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (file.length() > 0) {
                        Log.e(TAG, "success");
                    }
                }
                Log.e(TAG, "finish");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (null != t) {
                    t.printStackTrace();
                }
            }
        });
    }
    public ProgressDialog showDialog(View view, Context mContext){
        if(null==dialog) {
            dialog = new ProgressDialog(mContext);
            // 设置对话框参数
            dialog.setIcon(R.mipmap.app_logo);
            dialog.setTitle("软件下载");
            dialog.setMessage("软件下载进度：");
            dialog.setCancelable(false);
            // 设置进度条参数
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
//            dialog.incrementProgressBy(20);
            dialog.setIndeterminate(false); // 填false表示是明确显示进度的 填true表示不是明确显示进度的
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(mContext, "确定", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(dialog.isShowing()){

        }else {
            dialog.show();
        }
        return dialog;
    }
}
