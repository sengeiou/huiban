package com.bshuiban.baselibrary.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.bshuiban.baselibrary.contract.ClassScheduleContract;
import com.bshuiban.baselibrary.model.ClassScheduleBean;
import com.bshuiban.baselibrary.model.StudentClassClassScheduleBean;
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
    public void updateSchedule(String classId){
        if(tPresent!=null){
            tPresent.askInternetForScheduleData(classId);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ScrollView scrollView=new ScrollView(container.getContext());
        classSchedule=new ClassSchedule(getActivity());
        classSchedule.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tPresent.askInternetForScheduleData(User.getInstance().getClassId());
        scrollView.addView(classSchedule);
        return scrollView;
    }

    @Override
    public void updateSchedule(List<List<ClassScheduleBean.DataBean>> data) {
        classSchedule.setData(data);
    }

    @Override
    public void updateSchedule1(StudentClassClassScheduleBean bean) {

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
