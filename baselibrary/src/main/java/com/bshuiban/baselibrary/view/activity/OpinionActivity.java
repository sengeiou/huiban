package com.bshuiban.baselibrary.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    private TextView tv_text_count;
    private int Max_Count=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        tPresent=new OpinionPresent(this);
        View iv_title_bar_head = findViewById(R.id.iv_title_bar_head);
        TextView tv_title_bar_title = findViewById(R.id.tv_title_bar_title);
        et_content = findViewById(R.id.et_content);
        tv_text_count = findViewById(R.id.tv_count);
        updateContentCount(et_content);
        tv_title_bar_title.setText("意见反馈");
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
    private void updateContentCount(EditText et_content) {
        et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Max_Count)});
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = charSequence.length();
                tv_text_count.setText(length+"/"+Max_Count);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    private void sendOpinion() {
        String content = et_content.getText().toString();
        if(TextUtils.isEmpty(content)){
            toast("请填写内容");
            return;
        }
        startDialog();
        tPresent.sendOpinion(content);
    }

    @Override
    public void sendSuccess() {
        dismissDialog();
        toast("发送成功");
        et_content.setText("");
    }

    @Override
    public void startDialog() {
        showLoadingDialog();
    }

    @Override
    public void dismissDialog() {
        dismissLoadingDialog();
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
