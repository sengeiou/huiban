package com.bshuiban.baselibrary.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.GeneralSituationContract;
import com.bshuiban.baselibrary.model.GeneralBean;
import com.bshuiban.baselibrary.present.GeneralSituationPresent;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：概况
 */
public class GeneralSituationFragment extends BaseFragment<GeneralSituationPresent> implements GeneralSituationContract.View{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new GeneralSituationPresent(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_situation, container, false);
        init(view);
        tPresent.askInterNetForData();
        return view;
    }

    private void init(View view) {

    }

    @Override
    public void updateView(GeneralBean.DataBean data) {

    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getClassId() {
        return null;
    }

    @Override
    public void fail(String error) {
        toast(error);
    }


    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
