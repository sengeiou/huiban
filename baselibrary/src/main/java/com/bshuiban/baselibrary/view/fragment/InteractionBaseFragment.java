package com.bshuiban.baselibrary.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：fragment是否重新加载、更新、
 */
public abstract class InteractionBaseFragment extends BaseFragment {
    protected boolean needUpdateData;
    protected OnFragmentInteractionListener mListener;

    public abstract void update(Bundle bundle);
    protected abstract int getLayoutResource();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResource(),container,false);
    }

    public boolean isEffective(){
        return null!=mListener;
    }


    public interface OnFragmentInteractionListener {
        /**
         * 启动其他fragment
         * @param tag
         * @param bundle
         */
        void onFragmentInteraction(String tag, Bundle bundle);
    }
}
