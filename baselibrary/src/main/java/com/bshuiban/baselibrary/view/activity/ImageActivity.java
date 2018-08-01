package com.bshuiban.baselibrary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.LogUtils;
import com.bshuiban.baselibrary.utils.SystemStatusManager;
import com.bshuiban.baselibrary.view.customer.TouchImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by xinheng on 2018/7/17.<br/>
 * describe：
 */
public class ImageActivity extends AppCompatActivity {
    public static void startImageActivity(Context context, String path){
        LogUtils.e("TAG", "startImageActivity: "+path );
        if(TextUtils.isEmpty(path)){
            Toast.makeText(context,"图片地址错误",Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(new Intent(context,ImageActivity.class).putExtra("Image_Path",path));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SystemStatusManager(this).setTranslucentStatus(R.color.black);
        setContentView(R.layout.activity_image);
        String image_path = getIntent().getStringExtra("Image_Path");
        FrameLayout fl=findViewById(R.id.fl);
        fl.setOnClickListener(v -> finish());
        TouchImageView touchImageView=findViewById(R.id.iv);
        touchImageView.setMaxZoom(4f);
        Glide.with(this).asBitmap().load(image_path).into(new ViewTarget<TouchImageView, Bitmap>(touchImageView) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                this.view.setImageBitmap(resource);
            }
        });
    }
}
