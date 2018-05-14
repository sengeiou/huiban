package com.bshuiban.baselibrary.view.webview.webFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.view.fragment.InteractionBaseFragment;
import com.bshuiban.baselibrary.view.interfaces.OnFragmentInteractionListener;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：
 */
public abstract class InteractionBaseWebViewFragment<T extends BasePresent> extends BaseWebFragment<T> {
    protected boolean needUpdateData;
    protected OnFragmentInteractionListener mListener;

    public abstract void update(Bundle bundle);
    protected int getLayoutResource(){
        return -1;
    }
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
            return inflater.inflate(getLayoutResource(), container, false);
        }else{
            return fragmentView;
        }
    }
    protected View getFragmentView(){
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mWebView = getWebView(getActivity());
        frameLayout.addView(mWebView);
        registerWebViewH5Interface();
        return frameLayout;
    }
    public boolean isEffective(){
        return null!=mListener;
    }
}
