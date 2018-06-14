package com.bshuiban.baselibrary.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.view.customer.CameraPreview;

import java.io.File;

public class CameraActivity extends Activity {
    private CameraPreview camera_preview;
    public static final int TAKE_PICTURE = 0x000007;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        camera_preview = (CameraPreview) findViewById(R.id.camera_preview);
        camera_preview.setOnCameraCallBack(file -> {
            String absolutePath = file.getAbsolutePath();
            Intent i = new Intent();
            i.putExtra("path", absolutePath);
            setResult(RESULT_OK, i);
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera_preview.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera_preview.onResume();
    }
}
