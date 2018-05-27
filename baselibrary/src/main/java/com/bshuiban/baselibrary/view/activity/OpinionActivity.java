package com.bshuiban.baselibrary.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.OpinionContract;
import com.bshuiban.baselibrary.present.OpinionPresent;

import java.io.File;

public class OpinionActivity extends BaseActivity<OpinionPresent> implements OpinionContract.View{
    private File imageFile;
    private EditText et_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        tPresent=new OpinionPresent(this);
        View iv_title_bar_head = findViewById(R.id.iv_title_bar_head);
        TextView tv_title_bar_title = findViewById(R.id.tv_title_bar_title);
        et_content = findViewById(R.id.et_content);
        tv_title_bar_title.setText("表意见反馈");
        ImageView iv_select_subject = findViewById(R.id.iv_select_subject);
        iv_select_subject.setImageResource(R.mipmap.iv_send_feedback);
        //View rv_select_picture = findViewById(R.id.rv_select_picture);
        iv_select_subject.setVisibility(View.VISIBLE);
        iv_title_bar_head.setOnClickListener(onClickListener);
        iv_select_subject.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener= v -> {
        int i = v.getId();
        if (i == R.id.iv_title_bar_head) {
            finish();
        } else if (i == R.id.iv_select_subject) {
            sendOpinion();
        }else{

        }
    };

    private void sendOpinion() {
        String content = et_content.getText().toString();
        tPresent.sendOpinion(content);
    }

    @Override
    public void sendSuccess() {
        toast("发送成功");
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        toast(error);
    }
    /*private void takeImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File path = new File(Environment.getExternalStorageDirectory() + File.separator + "huiban/image/");
        if (!path.exists())
            path.mkdirs();
        imageFile = new File(path, System.currentTimeMillis()+".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Uri contentUri = FileProvider.getUriForFile(this,
                    "com.bshuiban.fileprovider",
                    imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }*/
}
