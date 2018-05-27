package com.bshuiban.baselibrary.view.activity;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.GlideCacheUtil;

import java.io.File;

public class CleanCacheActivity extends AppCompatActivity {
    private Button btn_cache;
    private TextView tv_use_space;
    private TextView tv_persent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_cache);
        btn_cache = findViewById(R.id.btn_cache);
        tv_use_space = findViewById(R.id.tv_use_space);
        btn_cache.setOnClickListener(v -> {
            //clearAllDefaultCache(this);
            GlideCacheUtil.getInstance().clearImageDiskCache(getApplicationContext());
            tv_use_space.setText(String.valueOf(0.0)+"MB");
            tv_persent.setText("占据手机0.0%储存空间");
        });
        tv_persent = findViewById(R.id.tv_persent);
        tv_use_space.setText(GlideCacheUtil.getInstance().getCacheSize(getApplicationContext()));
        long persent = (GlideCacheUtil.getInstance().getCacheSizeLong(getApplicationContext())/getSDTotalSize())*100;

        tv_persent.setText("占据手机"+persent+"%储存空间");
    }
    /**
     * 获得SD卡总大小
     *
     * @return
     */
    private Long getSDTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return blockSize * totalBlocks;
    }
   /* private void clearAllDefaultCache(Context context) {
        String path = StorageUtils.getIndividualCacheDirectory
                (context.getApplicationContext()).getAbsolutePath();
        FileUtils.deleteFiles(new File(path));
    }*/
}
