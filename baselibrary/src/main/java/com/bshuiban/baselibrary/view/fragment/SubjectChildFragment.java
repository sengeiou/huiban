package com.bshuiban.baselibrary.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.SubjectChildContract;

import com.bshuiban.baselibrary.model.StudyBottomBean;
import com.bshuiban.baselibrary.model.StudyReportBean;
import com.bshuiban.baselibrary.model.observebean.ReportUpdateBean;
import com.bshuiban.baselibrary.present.SubjectChildPresent;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.utils.observer.ObserveModeGroupList;
import com.bshuiban.baselibrary.utils.observer.OnObserver;
import com.bshuiban.baselibrary.view.activity.StatisticalChartActivity;
import com.bshuiban.baselibrary.view.customer.CircleColorProgressView;
import com.bshuiban.baselibrary.view.interfaces.OnReportDateListener;

/**
 * A simple {@link Fragment} subclass.
 * 子科目 如：语文
 */
public class SubjectChildFragment extends InteractionBaseFragment<SubjectChildPresent> implements SubjectChildContract.View {

    private TextView tv_class_ranking;
    private TextView tv_class_progress;
    private TextView tv_gradle_ranking;
    private CircleColorProgressView circleProgressView;
    private View tv_look_statistical_inf;
    private View include;
    private int subjectId;
    private OnObserver<ReportUpdateBean> observer;
    private String date;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e("TAG", "setUserVisibleHint: child="+isVisibleToUser );
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onStart() {
        Log.e("TAG", "onStart: child" );
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new SubjectChildPresent(this);
        observer = new OnObserver<ReportUpdateBean>() {
            @Override
            public void dealWithNotice(boolean isUpdate, ReportUpdateBean bean) {
                if(isUpdate){
                    if(bean.isUpdate()||!bean.getDate().equals(date)) {
                        date = bean.getDate();
                        updateDataForDate();
                    }
                }
            }
        };
        ObserveModeGroupList.getInstance().register(ReportUpdateBean.class, observer);
    }

    @Override
    public void update(Bundle bundle) {
        if (null != bundle) {
            subjectId = bundle.getInt("subjectId");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject_child, container, false);
        circleProgressView = view.findViewById(R.id.circleProgressView);
        tv_class_ranking = view.findViewById(R.id.tv_class_ranking);
        tv_class_progress = view.findViewById(R.id.tv_class_progress);
        tv_gradle_ranking = view.findViewById(R.id.tv_gradle_ranking);
        tv_look_statistical_inf = view.findViewById(R.id.tv_look_statistical_inf);
        tv_look_statistical_inf.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), StatisticalChartActivity.class)
                    .putExtra("time", date)
                    .putExtra("subjectId", subjectId+""));
        });
        include = view.findViewById(R.id.include);
        date=getArguments().getString("date");
        updateDataForDate();
        return view;
    }
    @Override
    public void onResume() {
        Log.e("TAG", "onResume: child " );
        super.onResume();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e("TAG", "onHiddenChanged:child= "+hidden );
        if(!hidden){
            updateDataForDate();
        }
        super.onHiddenChanged(hidden);
    }

    public void updateDataForDate() {
        tPresent.loadStudyBottom(subjectId,date);
        tPresent.loadStudyReportData(subjectId, date);
    }
    @Override
    public void updateProgressView(StudyReportBean.DataBean data) {
        if (null != data) {
            StudyReportBean.DataBean.MineBean mine = data.getMine();
            if (null != mine) {
                String ss;
                String rate = mine.getRate();//我的正确率
                float v=-1;
                try {
                    v = Float.parseFloat(rate);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                circleProgressView.setProgress(v);
                tv_class_ranking.setText(TextUtils.clearStringForMinusone(mine.getCrank()));
                tv_class_progress.setText(TextUtils.clearStringForMinusone(mine.getProgress() + ""));
                tv_gradle_ranking.setText(TextUtils.clearStringForMinusone(mine.getGrank()));
            }

        }
    }

    @Override
    public void updateStudyBottom(StudyBottomBean.DataBean data) {
        ViewUtils.setViewData(include, data);
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
    }
    @Override
    public void onDetach() {
        ObserveModeGroupList.getInstance().unregister(ReportUpdateBean.class,observer);
        super.onDetach();
    }
}
