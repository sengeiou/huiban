package com.bshuiban.baselibrary.internet;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by xinheng on 2018/6/25.<br/>
 * describe：进度条
 */
public class ProgressResponseBody extends ResponseBody {
    private final ResponseBody responseBody;
    private final ProgressListener listener;
    private BufferedSource bufferedSource;
    private static MainHandler handlerMain;

    private class MainHandler extends Handler {
        public MainHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listener.onProgress(msg.arg1, msg.arg2, (boolean) msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        this.responseBody = responseBody;
        this.listener = listener;
        handlerMain = new MainHandler();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (null == bufferedSource) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //handlerMain.handleMessage(handlerMain.obtainMessage(0, (int) totalBytesRead, (int) responseBody.contentLength(), bytesRead == -1));
                listener.onProgress((int) totalBytesRead, (int) responseBody.contentLength(), bytesRead == -1);
                return bytesRead;
            }
        };
    }

    public interface ProgressListener {
        /**
         * @param progress 已经下载或上传字节数
         * @param total    总字节数
         * @param done     是否完成
         */
        void onProgress(int progress, int total, boolean done);
    }

}