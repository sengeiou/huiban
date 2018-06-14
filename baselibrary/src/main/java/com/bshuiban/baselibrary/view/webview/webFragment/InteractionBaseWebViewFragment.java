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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
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
        return R.layout.activity_test_web_view;
    }
    protected void initWebView(View view) {
        mWebView=view.findViewById(R.id.webview);
        setWebViewSetting(mWebView);
        TextView tv_sure=view.findViewById(R.id.tv_sure);
        EditText et_content = view.findViewById(R.id.et_content);
        et_content.setText("http://192.168.0.3:90/");
        tv_sure.setOnClickListener(v-> loadPathHtml(et_content.getText().toString().trim()));
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
            View inflate = inflater.inflate(getLayoutResource(), container, false);
            initWebView(inflate);
            return inflate;
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
