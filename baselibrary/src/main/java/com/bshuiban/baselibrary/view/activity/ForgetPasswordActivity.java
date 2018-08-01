package com.bshuiban.baselibrary.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.ForgetPasswordContract;
import com.bshuiban.baselibrary.present.ForgetPasswordPresent;
import com.bshuiban.baselibrary.view.customer.TitleView;

import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xinheng on 2018/7/3.<br/>
 * describe：
 */
public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresent> implements ForgetPasswordContract.View {

    private EditText et_phone;
    private EditText et_code;
    private EditText et_password1;
    private EditText et_password;
    private TextView tv_send_code;
    private Button bt_submit;

    private static class MyHandler extends Handler {
        private WeakReference<ForgetPasswordActivity> reference;
        private int i = 60;

        public MyHandler(ForgetPasswordActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ForgetPasswordActivity forgetPasswordActivity = reference.get();
                    if (null != forgetPasswordActivity) {
                        if (i < 1) {
                            forgetPasswordActivity.tv_send_code.setText("获取验证码");
                            forgetPasswordActivity.tv_send_code.setEnabled(true);
                            forgetPasswordActivity.et_phone.setEnabled(true);
                            removeMessages(0);
                        } else {
                            forgetPasswordActivity.tv_send_code.setText(i + "秒后重新获取");
                            i--;
                            sendEmptyMessageDelayed(0, 1000);
                        }
                    }
                    break;
                case 1:
                    removeMessages(0);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    private MyHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        TitleView titleView = findViewById(R.id.titleView);
        handler = new MyHandler(this);
        tPresent = new ForgetPasswordPresent(this);
        et_phone = findViewById(R.id.et_phone);
        et_code = findViewById(R.id.et_code);
        et_password = findViewById(R.id.et_password);
        et_password1 = findViewById(R.id.et_password1);
        tv_send_code = findViewById(R.id.tv_send_code);
        bt_submit = findViewById(R.id.bt_submit);
        titleView.setOnClickListener(this::onClick);
        tv_send_code.setOnClickListener(this::onClick);
        bt_submit.setOnClickListener(this::onClick);
        bt_submit.setEnabled(false);
    }

    private String phone;

    private void onClick(View v) {
        int i = v.getId();
        if (i == R.id.titleView) {
            finish();
        } else if (i == R.id.tv_send_code) {
            String phone1 = et_phone.getText().toString();
            String phoneError = getPhoneError(phone1);
            if (null != phoneError) {
                toast(phoneError);
                return;
            }
            this.phone=phone1;
            tv_send_code.setEnabled(false);
            tPresent.loadSendCode(phone);
        } else if (i == R.id.bt_submit) {
            if(null==phone) {
                phone = et_phone.getText().toString();
            }
            String code = et_code.getText().toString();
            String password = et_password.getText().toString();
            String password1 = et_password1.getText().toString();
            String phoneError = getPhoneError(phone);
            if(null!=phoneError){
                toast(phoneError);
                return;
            }else if (TextUtils.isEmpty(code)) {
                toast("请输入验证码");
                return;
            } else if (password.length() <= 5) {
                toast("密码至少6位");
                return;
            } else if (!password.equals(password1)) {
                toast("两次输入的密码不同");
                return;
            }
            startDialog();
            tPresent.loadChangePassword(phone, code, password);
        }
    }

    @Override
    public void changeSuccess() {
        dismissDialog();
        toast("修改成功");
        new Handler().postDelayed(() -> finish(), 1000);
    }

    @Override
    public void sendSuccess() {
        //et_phone.setEnabled(false);
        handler.sendEmptyMessage(0);
        tv_send_code.setEnabled(false);
        bt_submit.setEnabled(true);
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
        if (error != null && error.contains("验证码")) {
            tv_send_code.setEnabled(true);
        }
        toast(error);
    }

    private String getPhoneError(String phone) {
        if(TextUtils.isEmpty(phone)){
            return "请输入手机号";
        }
        String regex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        if (phone.length() != 11) {
            return "手机号应为11位数";
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (isMatch) {
                return null;
            } else {
                return "手机号错误";
            }
        }
    }
}
