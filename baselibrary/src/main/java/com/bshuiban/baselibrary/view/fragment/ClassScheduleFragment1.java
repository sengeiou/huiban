package com.bshuiban.baselibrary.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.ClassScheduleContract;
import com.bshuiban.baselibrary.model.ClassScheduleBean;
import com.bshuiban.baselibrary.model.StudentClassClassScheduleBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.ClassSchedulePresent;
import com.bshuiban.baselibrary.utils.SpaceItemDecorationUtils;
import com.bshuiban.baselibrary.view.activity.StudentClassScheduleAdapter;
import com.bshuiban.baselibrary.view.customer.ClassSchedule;

import java.util.List;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：
 */
public class ClassScheduleFragment1 extends BaseFragment<ClassSchedulePresent> implements ClassScheduleContract.View{
    private ClassSchedule classSchedule;
    private RecyclerView mRvClassSchedule;
    private StudentClassScheduleAdapter mStudentClassScheduleAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new ClassSchedulePresent(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        classSchedule=new ClassSchedule(getActivity());
        View view = inflater.inflate(R.layout.activity_student_schedule, container, false);
        initView(view);
        tPresent.askInternetForScheduleData();
        return view;
    }

    private void initView(View view) {
        mRvClassSchedule = view.findViewById(R.id.rv_class_schedule);
    }

    @Override
    public void updateSchedule(List<List<ClassScheduleBean.DataBean>> data) {

    }

    @Override
    public void updateSchedule1(StudentClassClassScheduleBean bean) {
        GridLayoutManager layoutManager = null;
        layoutManager = new GridLayoutManager(getActivity(), 1);
        mRvClassSchedule.setLayoutManager(layoutManager);
        if (mStudentClassScheduleAdapter == null) {
            SpaceItemDecorationUtils spaceItemDecoration = new SpaceItemDecorationUtils(10);
            mRvClassSchedule.addItemDecoration(spaceItemDecoration);
            mStudentClassScheduleAdapter = new StudentClassScheduleAdapter(getActivity(),bean.getData());
            mRvClassSchedule.setAdapter(mStudentClassScheduleAdapter);
        } else {
            mStudentClassScheduleAdapter.notifyDataSetChanged();
        }
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
