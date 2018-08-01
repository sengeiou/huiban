package com.bshuiban.baselibrary.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bshuiban.baselibrary.utils.ClassChange;
import com.bshuiban.baselibrary.view.fragment.ClassActivityFragment;
import com.bshuiban.baselibrary.view.fragment.ClassScheduleFragment;
import com.bshuiban.baselibrary.view.fragment.GeneralSituationFragment;
import com.bshuiban.baselibrary.view.fragment.LearningDynamicFragment;

import java.util.ArrayList;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：班级viewPager适配器
 */
public class ClassViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mList;
    private ArrayList<Fragment> mListFragment;
    //private ClassScheduleFragment fragment;
    private ClassChange classChange;
    public ClassViewPagerAdapter(FragmentManager fm, ArrayList<String> list, ClassChange classChange) {
        super(fm);
        this.mList = list;
        mListFragment=new ArrayList<>();
        this.classChange=classChange;
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }
    public Fragment getFragment(int index){
        return mListFragment.get(index);
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
            default: {
                fragment = new ClassScheduleFragment();
            }
        }
        classChange.setOnChangeListener((ClassChange.OnChangeListener) fragment);
        mListFragment.add(fragment);
        return fragment;
    }
}
