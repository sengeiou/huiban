package com.bshuiban.baselibrary.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.ReportContract;
import com.bshuiban.baselibrary.model.StuLearnReportBean;
import com.bshuiban.baselibrary.model.SubjectBean;
import com.bshuiban.baselibrary.model.observebean.ReportUpdateBean;
import com.bshuiban.baselibrary.present.ReportPresent;
import com.bshuiban.baselibrary.utils.observer.ObserveModeGroupList;
import com.bshuiban.baselibrary.utils.observer.Observer;
import com.bshuiban.baselibrary.utils.observer.OnObserver;
import com.bshuiban.baselibrary.view.activity.BaseFragmentActivity;
import com.bshuiban.baselibrary.view.adapter.ClassViewPagerAdapter;
import com.bshuiban.baselibrary.view.interfaces.OnReportDateListener;
import com.bshuiban.baselibrary.view.webview.webFragment.InteractionBaseWebViewFragment;
import com.xinheng.date_dialog.dialog.SelectDateDialog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：报告
 */
public class ReportFragment extends InteractionBaseWebViewFragment<ReportPresent> implements ReportContract.View, OnReportDateListener {
    private List<StuLearnReportBean.DataBean.ContrastBean> arrayList;
    private ReportViewPagerAdapter classViewPagerAdapter;
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private TextView tv_date;
    private int mYear, mMonth;
    private CommonNavigatorAdapter commonNavigatorAdapter;
    private OnObserver<ReportUpdateBean> observer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new ReportPresent(this);
        arrayList = new ArrayList<>();

    }

    @Override
    public void update(Bundle bundle) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        magicIndicator = view.findViewById(R.id.magic);
        viewPager = view.findViewById(R.id.viewPager);
        tv_date = view.findViewById(R.id.tv_date);
        tv_date.setOnClickListener(v -> {
            SelectDateDialog selectDateDialog = new SelectDateDialog(getActivity(), new SelectDateDialog.OnClickListener() {
                @Override
                public boolean onSure(int year, int month, int day, long time) {
                    if (year > nowYear || (year == nowYear && month > nowMonth)) {
                        toast("日期超了。。。");
                        return false;
                    }
                    if (year != mYear || mMonth != month) {
                        mYear = year;
                        mMonth = month;
                        updateTVDate(true);
                    }
                    return false;
                }

                @Override
                public boolean onCancel() {
                    return false;
                }
            });
            selectDateDialog.setDateType(SelectDateDialog.YEAR_MONTH);
            selectDateDialog.show(mYear, mMonth , 1);
        });
        updateTitleData();
        setDate();
        //tPresent.getTitleBarData();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, "onHiddenChanged: Report="+hidden );
        if(!hidden) {
            ReportUpdateBean bean = new ReportUpdateBean();
            bean.setDate(getDate());
            bean.setUpdate(true);
            ObserveModeGroupList.getInstance().postNotice(true, bean);
        }
        super.onHiddenChanged(hidden);
    }

    private int nowMonth, nowYear;

    private void setDate() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        nowYear = mYear = calendar.get(Calendar.YEAR);
        nowMonth = mMonth = calendar.get(Calendar.MONTH) + 1;
        updateTVDate(false);
    }

    private void updateTVDate(boolean tag) {
        tv_date.setText(String.format("%d年%02d月统计报告", mYear, mMonth));
        if (tag) {//发送更新消息
            ReportUpdateBean bean = new ReportUpdateBean();
            bean.setUpdate(false);
            bean.setDate(getDate());
            ObserveModeGroupList.getInstance().postNotice(true, bean);
        }
    }

    public void updateTitleBar1(List<StuLearnReportBean.DataBean.ContrastBean> contrastBeans) {
        if (null != contrastBeans && contrastBeans.size() > 0) {
            if (arrayList.size() < 2) {
                arrayList.addAll(contrastBeans);
                commonNavigatorAdapter.notifyDataSetChanged();
                classViewPagerAdapter.notifyDataSetChanged();
            }
        }
    }

    public void updateTitleBar(List<SubjectBean.DataBean> data) {
//        if(null!=data&&data.size()>0) {
//            arrayList.addAll(data);
//            commonNavigatorAdapter.notifyDataSetChanged();
//            classViewPagerAdapter.notifyDataSetChanged();
//        }
    }

    private void updateTitleData() {
        StuLearnReportBean.DataBean.ContrastBean e = new StuLearnReportBean.DataBean.ContrastBean();
        e.setSubjectName("总览");
        //e.setId(-1);
        arrayList.add(e);
        classViewPagerAdapter = new ReportViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(classViewPagerAdapter);
        magicIndicator.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.guide_start_btn));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigatorAdapter = new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(getActivity());
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(getActivity(), R.color.guide_light));
                simplePagerTitleView.setPadding(15, 0, 15, 0);
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener((v) -> viewPager.setCurrentItem(index, false));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.dp_15));
                simplePagerTitleView.setText(arrayList.get(index).getSubjectName());
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(getActivity());
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        };
        commonNavigator.setAdapter(commonNavigatorAdapter);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void updateDateView() {

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
    public String getDate() {
        return String.format("%d%02d", mYear, mMonth);
    }


    private class ReportViewPagerAdapter extends FragmentPagerAdapter {

        public ReportViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("date",getDate());
            if (position == 0) {
                SubjectAllFragment subjectAllFragment = new SubjectAllFragment();
                subjectAllFragment.setArguments(bundle);
                subjectAllFragment.setOnContrastData(contrastBeans -> {
                    //List<StuLearnReportBean.DataBean.ContrastBean> contrastBeans;
                    updateTitleBar1(contrastBeans);
                });
                return subjectAllFragment;
            } else {
                bundle.putInt("subjectId", arrayList.get(position).getSubjectId());
                SubjectChildFragment subjectChildFragment = new SubjectChildFragment();
                subjectChildFragment.update(bundle);
                subjectChildFragment.setArguments(bundle);
                return subjectChildFragment;
            }
        }

        @Override
        public int getCount() {
            return arrayList == null ? 0 : arrayList.size();
        }
    }

}
