package com.bshuiban.baselibrary.internet;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.view.customer.LineView;

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
    private Dialog dialog;
    private Call<ResponseBody> responseBodyCall;

    public RetrofitDownload() {
        retrofit = getRetrofit();
        mProgressListener = (progress, total, done) -> {
            Log.e(TAG, (Looper.myLooper()) + "");
            Log.e(TAG, "onProgress: " + "total ---->" + total + " done ---->" + progress + ", 进度：" + progress * 100f / total + "%");
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
        responseBodyCall = downloadFile(dowunloadUrl);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                FileOutputStream fos = null;
                BufferedInputStream bis = null;
                InputStream is = null;
                File file = new File(saveFilePath);
                if (file.exists()) {
                    file.getAbsoluteFile().delete();
                } else {
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
                        if (null != downloadListener) {
                            downloadListener.loadFinish(file.getAbsolutePath());
                        }
                    } else {
                        if (null != downloadListener) {
                            downloadListener.loadFail("下载失败");
                        }
                    }
                }
                Log.e(TAG, "finish");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error;
                if (null != t) {
                    t.printStackTrace();
                    error = t.getMessage();
                } else {
                    error = "下载更新失败";
                }
                if (null != downloadListener) {
                    downloadListener.loadFail(error);
                }
            }
        });
    }

    private DownloadListener downloadListener;

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public interface DownloadListener {
        void loadFinish(String downLoadPath);

        void loadFail(String error);
    }

    public Dialog showDialog(Context mContext,float progress) {
        if(null==dialog){
            dialog=new Dialog(mContext);
            Window window = dialog.getWindow();
            window.requestFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_update_progress);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getDecorView().setPadding(0,0,0,0);
            TextView tv_sure = dialog.findViewById(R.id.tv_sure);
            TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
            View.OnClickListener onClickListener=v -> {
                int i = v.getId();
                if (i == R.id.tv_sure) {
                    if(null!=responseBodyCall&&responseBodyCall.isCanceled()){
                        responseBodyCall.cancel();
                        dialog.dismiss();
                    }
                } else if (i == R.id.tv_cancel) {
                    if(null!=dialog&&dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            };
            tv_cancel.setOnClickListener(onClickListener);
            tv_sure.setOnClickListener(onClickListener);
        }
        LineView lineView = dialog.findViewById(R.id.lineView);
        TextView tv_progress = dialog.findViewById(R.id.tv_progress);
        tv_progress.setText((int)(progress*100)+"%");
        lineView.setProgress(progress);
        if (dialog.isShowing()) {

        } else {
            dialog.show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width= (int) mContext.getResources().getDimension(R.dimen.dp_258);
            window.setAttributes(attributes);
            window.setGravity(Gravity.CENTER);
        }
        return dialog;
    }
}
