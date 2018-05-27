package com.bshuiban.baselibrary.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.contract.ClassScheduleContract;
import com.bshuiban.baselibrary.model.ClassScheduleBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.ClassSchedulePresent;
import com.bshuiban.baselibrary.view.customer.ClassSchedule;

import java.util.List;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：
 */
public class ClassScheduleFragment extends BaseFragment<ClassSchedulePresent> implements ClassScheduleContract.View{
    private ClassSchedule classSchedule;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new ClassSchedulePresent(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        classSchedule=new ClassSchedule(getActivity());
        classSchedule.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tPresent.askInternetForScheduleData(User.getInstance().getClassId());
        return classSchedule;
    }

    @Override
    public void updateSchedule(List<List<ClassScheduleBean.DataBean>> data) {
        classSchedule.setData(data);
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        toast(error);
        //classSchedule.setData(null);
    }
}
