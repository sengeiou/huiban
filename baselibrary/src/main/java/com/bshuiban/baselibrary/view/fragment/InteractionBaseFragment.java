package com.bshuiban.baselibrary.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.view.interfaces.OnFragmentInteractionListener;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：fragment是否重新加载、更新、
 */
public abstract class InteractionBaseFragment extends BaseFragment {
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

    @Nullable
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
        return null;
    }
    public boolean isEffective(){
        return null!=mListener;
    }


}
