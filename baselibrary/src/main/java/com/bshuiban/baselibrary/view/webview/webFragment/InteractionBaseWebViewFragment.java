package com.bshuiban.baselibrary.view.webview.webFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.ActivityH5Utils;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.fragment.InteractionBaseFragment;
import com.bshuiban.baselibrary.view.interfaces.OnFragmentInteractionListener;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：必须实现OnFragmentInteractionListener接口
 */
public abstract class InteractionBaseWebViewFragment<T extends BasePresent> extends BaseWebFragment<T> {
    protected boolean needUpdateData;
    protected OnFragmentInteractionListener mListener;

    public abstract void update(Bundle bundle);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = getFragmentView();
        if(null== fragmentView) {
            View inflate = inflater.inflate(getLayoutResource(), container, false);
            mWebViewFactory.initTestWebView(inflate);
            return inflate;
        }else{
            return fragmentView;
        }
    }
    protected View getFragmentView(){
        String className = getClass().getSimpleName();
        String activityHtml5Name = ActivityH5Utils.getActivityHtml5Name(className);
        Log.e(TAG, "getFragmentView: "+className+", "+activityHtml5Name );
        //if(!TextUtils.isEmpty(activityHtml5Name)){
            //return null;
        //}
        FrameLayout frameLayout = ViewUtils.getFrameLayout(getActivity());
        mWebView = getWebView(getActivity());
        frameLayout.addView(mWebView);
        return frameLayout;
    }
    public boolean isEffective(){
        return null!=mListener;
    }
}
