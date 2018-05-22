package com.bshuiban.baselibrary.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.SubjectChildContract;
import com.bshuiban.baselibrary.present.StudyBottomBean;
import com.bshuiban.baselibrary.present.StudyReportBean;
import com.bshuiban.baselibrary.present.SubjectChildPresent;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.customer.CircleProgressView;

/**
 * A simple {@link Fragment} subclass.
 * 子科目 如：语文
 */
public class SubjectChildFragment extends InteractionBaseFragment<SubjectChildPresent> implements SubjectChildContract.View {

    private TextView tv_class_ranking;
    private TextView tv_class_progress;
    private TextView tv_gradle_ranking;
    private CircleProgressView circleProgressView;
    private View tv_look_statistical_inf;
    private View include;

    public SubjectChildFragment() {
        // Required empty public constructor
    }


    @Override
    public void update(Bundle bundle) {

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
        include = view.findViewById(R.id.include);
        return view;
    }

    @Override
    public void updateProgressView(StudyReportBean.DataBean data) {
        if(null!=data){
            StudyReportBean.DataBean.MineBean mine = data.getMine();
            if(null!=mine){
                String rate = mine.getRate();//我的正确率
                circleProgressView.setProgressValue(rate);
                tv_class_ranking.setText(TextUtils.cleanNull(mine.getCrank()));
            }
        }
    }

    @Override
    public void updateStudyBottom(StudyBottomBean.DataBean data) {
        ViewUtils.setViewData(include,data);
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {

    }
}
