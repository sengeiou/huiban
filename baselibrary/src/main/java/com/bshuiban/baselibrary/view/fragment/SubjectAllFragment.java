package com.bshuiban.baselibrary.view.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.SubjectAllContract;
import com.bshuiban.baselibrary.model.StuLearnReportBean;
import com.bshuiban.baselibrary.present.StudyBottomBean;
import com.bshuiban.baselibrary.present.SubjectAllPresent;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.adapter.RankPagerAdapter;
import com.bshuiban.baselibrary.view.customer.BarsCharView;
import com.bshuiban.baselibrary.view.customer.WordGridView;
import com.bshuiban.baselibrary.view.customer.ZoomOutPageTransformer;
import com.bshuiban.baselibrary.view.interfaces.OnReportDateListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 总览
 */
public class SubjectAllFragment extends BaseFragment<SubjectAllPresent> implements SubjectAllContract.View {

    private View include;
    private ViewPager viewPager;
    private BarsCharView barsCharView;
    private WordGridView wordGridView;
    private RelativeLayout classHonor;
    private RankPagerAdapter adapter;

    public SubjectAllFragment() {
        // Required empty public constructor
        tPresent=new SubjectAllPresent(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_all, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        classHonor = view.findViewById(R.id.classHonor);
        wordGridView=view.findViewById(R.id.wordGrid);
        barsCharView=view.findViewById(R.id.barsChar);
        initViewPager();
        include = view.findViewById(R.id.include);
        updateDataForDate();
        return view;
    }

    public void updateDataForDate() {
        String date=onReportDateListener.getDate();
        tPresent.loadStuLearnReportAll(date);
        tPresent.loadStudyBottom(date);
    }

    private void initViewPager() {
        viewPager.setClipChildren(false); //VP的内容可以不在限制内绘制
        classHonor.setClipChildren(false);  //VP可以不在限制内绘制
        //adapter = new RankPagerAdapter(getChildFragmentManager(),null);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        viewPager.setLayoutParams(new RelativeLayout.LayoutParams((int) (widthPixels*0.5), RelativeLayout.LayoutParams.MATCH_PARENT));
        //viewPager.setAdapter(adapter);
        //实现画廊效果
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());   //实现需要的页面转换效果
        viewPager.setOffscreenPageLimit(2); //缓存页面数
        //viewPager.setPageMargin(100); //每页的间隔
        //触摸事件反馈给viewpager
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

    @Override
    public void updateStudyBottom(StudyBottomBean.DataBean data) {
        ViewUtils.setViewData(include,data);
    }

    @Override
    public void showRankInfo(List<StuLearnReportBean.DataBean.RankBean> listRank) {
        adapter = new RankPagerAdapter(getChildFragmentManager(),listRank);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void showContrastInfo(List<StuLearnReportBean.DataBean.ContrastBean> listContrast) {
        wordGridView.setContrastBeans(listContrast);
        if(null!=onContrastData){
            onContrastData.setContrasts(listContrast);
        }
    }

    @Override
    public void showExceedInfo(List<StuLearnReportBean.DataBean.ExceedBean> exceedBeans) {
        if (exceedBeans == null || exceedBeans.size() <= 0) {
            barsCharView.setXArray(null);
            return;
        }
        List<BarsCharView.Entry> entries = new ArrayList<>();
        String[] xArray = new String[exceedBeans.size()];
        for (int i = 0; i < exceedBeans.size(); i++) {
            StuLearnReportBean.DataBean.ExceedBean exceedBean = exceedBeans.get(i);
            entries.add(new BarsCharView.Entry(i, exceedBean.getPer() + "", Color.parseColor(exceedBean.getColor().replace(";", ""))));
            xArray[i] = exceedBean.getSubjectName();
        }
        barsCharView.setList_all(entries,true);
        barsCharView.setXArray(xArray);
    }
    private OnContrastData onContrastData;
    private OnReportDateListener onReportDateListener;

    public void setOnReportDateListener(OnReportDateListener onReportDateListener) {
        this.onReportDateListener = onReportDateListener;
    }

    public void setOnContrastData(OnContrastData onContrastData) {
        this.onContrastData = onContrastData;
    }

    public interface OnContrastData {
        void setContrasts(List<StuLearnReportBean.DataBean.ContrastBean> contrastBeans);
    }
}
