package com.bshuiban.baselibrary.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.view.customer.MyLoading;


/**
 * Created by weidanyan on 2016/9/30.
 */
public class HuiBanLoadingDialog extends Dialog {
    private Context context;
    private String title;
    private MyLoading tl_dialog;
    private CountDownTimer mCountDownTimer;

    public HuiBanLoadingDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    public HuiBanLoadingDialog(Context context, String title) {
        super(context, R.style.MyDialog);
        this.title = title;
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_loading_dialog);
        tl_dialog = (MyLoading) findViewById(R.id.tl_dialog);
        //外部点击不响应
        setCanceledOnTouchOutside(false);
        //不可以手动取消
        setCancelable(false);
    }

    private void timeOut(final HuiBanLoadingDialog loadingDialog) {
        if(mCountDownTimer==null){
            mCountDownTimer = new CountDownTimer(10000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    if(loadingDialog != null){
                        if(tl_dialog != null){
                            tl_dialog.stop();
                        }
                        loadingDialog.dismiss();
                    }
                }
            };
        } else{
            if(mCountDownTimer!=null){
                mCountDownTimer.start();
            }
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        //停止动画
        if(tl_dialog!=null){
            tl_dialog.stop();
        }
    }

    @Override
    public void show() {
        try {
            timeOut(this);
            if (tl_dialog != null) {
                tl_dialog.start();
            }
            if (!((Activity)context).isFinishing()) {
                super.show();
            }
        }catch (Exception e){
            tl_dialog.stop();
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        if(tl_dialog!=null){
            tl_dialog.stop();
        }
        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
        if (!((Activity)context).isFinishing()) {
            super.dismiss();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
