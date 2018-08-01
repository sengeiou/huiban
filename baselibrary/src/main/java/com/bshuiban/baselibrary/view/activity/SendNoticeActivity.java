package com.bshuiban.baselibrary.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.SendNoticeContract;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.TeachClassBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.SendNoticePresent;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bshuiban.baselibrary.view.dialog.TeachClassDialog;

import java.util.List;

/**
 * Created by xinheng on 2018/7/12.<br/>
 * describe：发送消息 通知或班级活动
 */
public class SendNoticeActivity extends BaseActivity<SendNoticePresent> implements SendNoticeContract.View {

    private EditText et;
    private int send_type;
    private TeachClassDialog teachClassDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notice);
        send_type = getIntent().getIntExtra("send_type", 0);
        et = findViewById(R.id.et);
        tPresent=new SendNoticePresent(this);
        tPresent.loadClassList();
        TitleView titleView=findViewById(R.id.titleView);
        titleView.setOnClickListener(new TitleView.OnClickListener() {
            @Override
            public void leftClick(View v) {
                finish();
            }

            @Override
            public void rightClick(View v) {
                List<TeachClassBean.DataBean> teachClassData = User.getInstance().getTeachClassData();
                if(!HomeworkBean.isEffictive(teachClassData)){
                    toast("无班级信息");
                    return;
                }
                String content = et.getText().toString().replace(" ","").trim();
                if(content.length()==0){
                    toast("请输入信息");
                    return;
                }
                startSendNoticeDialog(teachClassData, content);
            }
        });
    }

    private void startSendNoticeDialog(List<TeachClassBean.DataBean> teachClassData, String content) {
        if(null==teachClassDialog) {
            teachClassDialog = new TeachClassDialog(SendNoticeActivity.this);
            teachClassDialog.setSendClickListener(v1 -> {
                String selectClass = teachClassDialog.getSelectClass();
                String key = send_type == 0 ? SendNoticePresent.SEND_NOTICE : SendNoticePresent.SEND_ACTIVITY;
                tPresent.sendNotice(key, selectClass, content);
            });
        }
        teachClassDialog.setTexts(teachClassData);
        teachClassDialog.show();
    }

    @Override
    public void updateClassList(List<TeachClassBean.DataBean> dataBeanList) {

    }

    @Override
    public void sendNoticeSuccess() {
        if(null!=teachClassDialog&&teachClassDialog.isShow()){
            teachClassDialog.dismiss();
        }
        toast("发送成功");
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {

    }
}
