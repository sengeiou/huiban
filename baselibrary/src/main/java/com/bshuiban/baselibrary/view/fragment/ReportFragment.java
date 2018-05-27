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
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.ReportContract;
import com.bshuiban.baselibrary.model.SubjectBean;
import com.bshuiban.baselibrary.present.ReportPresent;
import com.bshuiban.baselibrary.view.activity.BaseFragmentActivity;
import com.bshuiban.baselibrary.view.adapter.ClassViewPagerAdapter;
import com.bshuiban.baselibrary.view.webview.webFragment.InteractionBaseWebViewFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：报告
 */
public class ReportFragment extends InteractionBaseWebViewFragment<ReportPresent> implements ReportContract.View {
    private List<SubjectBean.DataBean> arrayList;
    private ReportViewPagerAdapter classViewPagerAdapter;
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private CommonNavigatorAdapter commonNavigatorAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new ReportPresent(this);
        arrayList=new ArrayList<>();
    }

    @Override
    public void update(Bundle bundle) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        magicIndicator=view.findViewById(R.id.magic);
        viewPager=view.findViewById(R.id.viewPager);
        updateTitleData();
        tPresent.getTitleBarData();
        return view;
    }

    @Override
    public void updateTitleBar(List<SubjectBean.DataBean> data) {
        if(null!=data&&data.size()>0) {
            arrayList.addAll(data);
            commonNavigatorAdapter.notifyDataSetChanged();
            classViewPagerAdapter.notifyDataSetChanged();
        }
    }

    private void updateTitleData() {
        SubjectBean.DataBean e = new SubjectBean.DataBean();
        e.setSubjectName("总览");
        e.setId(-1);
        arrayList.add(e);
        classViewPagerAdapter=new ReportViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(classViewPagerAdapter);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigatorAdapter = new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(getActivity());
                simplePagerTitleView.setNormalColor(Color.BLACK);
                simplePagerTitleView.setPadding(15,0,15,0);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.guide_start_btn));
                simplePagerTitleView.setOnClickListener((v)->{
                    viewPager.setCurrentItem(index,false);
                });
                simplePagerTitleView.setText(arrayList.get(index).getSubjectName());
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(getActivity());
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.guide_start_btn));
                return linePagerIndicator;
            }
        };
        commonNavigator.setAdapter(commonNavigatorAdapter);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator,viewPager);
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
    private class ReportViewPagerAdapter extends FragmentPagerAdapter{

        public ReportViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return position==0?new SubjectAllFragment():new SubjectChildFragment();
        }

        @Override
        public int getCount() {
            return arrayList==null?0:arrayList.size();

        }
    }
}
