package com.bshuiban.baselibrary.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.utils.DialogUtils;
import com.bshuiban.baselibrary.view.adapter.ClassActivityAdapter;
import com.bshuiban.baselibrary.contract.ClassActivityContract;
import com.bshuiban.baselibrary.model.ClassActivityBean;
import com.bshuiban.baselibrary.present.ClassActivityPresent;
import com.bshuiban.baselibrary.view.dialog.MessageDialog;

import java.util.List;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：班级活动页面
 */
public class ClassActivityFragment extends RecycleViewFragment<ClassActivityBean.DataBean, ClassActivityAdapter, ClassActivityPresent> implements ClassActivityContract.View {
    private String classId;
    private boolean creat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        creat = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
//        if(!creat){
//            tPresent.askInternetForClassActivityData(classId,start,limit);
//        }else {
//            creat=false;
//        }
        super.onResume();
    }

    @Override
    public void updateViewForData(List<ClassActivityBean.DataBean> data) {
        super.updateView(data);
    }

    @Override
    public void refresh() {
        start = 0;
        tPresent.askInternetForClassActivityData(classId, start, limit);
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        //toast(error);
        dismissFresh();
    }

    @Override
    protected void initPresent() {
        tPresent = new ClassActivityPresent(this);
    }

    @Override
    public int getLayoutResourcesId() {
        return R.layout.layout_recycle_item;
    }

    @Override
    protected ClassActivityAdapter getAdapter() {
        ClassActivityAdapter classActivityAdapter = new ClassActivityAdapter(getActivity());

        classActivityAdapter.setOnDeleteListener(id -> {
            DialogUtils.showMessageSureCancelDialog(getActivity(), "确认删除此班级活动？", v -> {
                tPresent.deleteActivity(classId, id);
            });
        });

        return classActivityAdapter;
    }

    @Override
    protected void startPresent() {
        classId = User.getInstance().getClassId();
        tPresent.askInternetForClassActivityData(classId, start, limit);
    }

    public void update(String classId) {
        if (null != classId) {
            this.classId = classId;
        }
        start = 0;
        tPresent.askInternetForClassActivityData(this.classId, start, limit);
    }
}
