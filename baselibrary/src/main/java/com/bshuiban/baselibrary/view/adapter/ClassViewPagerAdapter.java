package com.bshuiban.baselibrary.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bshuiban.baselibrary.view.fragment.ClassActivityFragment;
import com.bshuiban.baselibrary.view.fragment.ClassScheduleFragment;
import com.bshuiban.baselibrary.view.fragment.ClassScheduleFragment1;
import com.bshuiban.baselibrary.view.fragment.GeneralSituationFragment;
import com.bshuiban.baselibrary.view.fragment.LearningDynamicFragment;

import java.util.ArrayList;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：班级viewPager适配器
 */
public class ClassViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mList;

    public ClassViewPagerAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public Fragment getItem(int position) {
//        arrayList.add("概况");
//        arrayList.add("学习动态");
//        arrayList.add("班级活动");
//        arrayList.add("班级课表");
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new GeneralSituationFragment();
                break;
            case 1:
                fragment = new LearningDynamicFragment();
                break;
            case 2:
                fragment = new ClassActivityFragment();
                break;
            default:
                fragment = new ClassScheduleFragment1();
        }
        return fragment;
    }
}
