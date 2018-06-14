package com.bshuiban.baselibrary.view.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.NetUtils;
import com.bshuiban.baselibrary.view.dialog.HuiBanLoadingDialog;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：
 */
public class BaseFragment<T extends BasePresent> extends Fragment {
    protected T tPresent;
    private Toast toast;
    private HuiBanLoadingDialog myLoadingDialog;

    /**
     * 吐司
     * @param s 内容
     */
    protected void toast(String s){
        if(null==toast){
            toast=Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_SHORT);
        }
        toast.setText(s);
        toast.show();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        toastDismiss();
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onStop() {
        toastDismiss();
        super.onStop();
    }

    private void toastDismiss() {
        if(null!=toast){
            toast.cancel();
            toast=null;
        }
    }

    @Override
    public void onDetach() {
        if(null!=tPresent) {
            tPresent.cancel();
            tPresent=null;
        }
        super.onDetach();
    }
    protected void showLoadingDialog() {
        if (myLoadingDialog == null) {
            myLoadingDialog = new HuiBanLoadingDialog(getActivity());
        } else {
            myLoadingDialog.setContext(getActivity());
        }
        if (myLoadingDialog != null && myLoadingDialog.isShowing()) {
            myLoadingDialog.dismiss();
        }
        if (!NetUtils.isNetworkAvalible(getActivity().getApplicationContext())) {
            return;
        }
        myLoadingDialog.show();
    }
    protected void dismissLoadingDialog() {
        if (myLoadingDialog != null && myLoadingDialog.isShowing()) {
            myLoadingDialog.dismiss();
        }
    }
}
