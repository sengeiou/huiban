package com.bshuiban.baselibrary.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.model.StuLearnReportBean;
import com.bshuiban.baselibrary.view.fragment.RankFragment;

import java.util.List;

/**
 * Created by xinheng on 2018/5/27.<br/>
 * describe：
 */
public class RankPagerAdapter extends FragmentStatePagerAdapter {
    private List<StuLearnReportBean.DataBean.RankBean> listRank;
    public RankPagerAdapter(FragmentManager fm, List<StuLearnReportBean.DataBean.RankBean> listRank) {
        super(fm);
        this.listRank = listRank;
    }

    public void refresh(List<StuLearnReportBean.DataBean.RankBean> listRank){
        this.listRank = listRank;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        StuLearnReportBean.DataBean.RankBean rankBean = listRank.get(position);
        //rectRankingInf.setSubject_name(rankBean.getSubjectName())
//                .setSubject_name_rata(rankBean.getRate())
//                .setClass_ranking(rankBean.getCrank())
//                .setClass_progress(rankBean.getProgress() + "")
//                .setGrade_ranking(rankBean.getGrank());
        return RankFragment.newInstance(rankBean.getSubjectName(),
                rankBean.getRate(),
                rankBean.getCrank(),
                rankBean.getProgress(),rankBean.getGrank(),rankBean.getColor());
    }

    @Override
    public int getCount() {
        return listRank==null?0:listRank.size();
    }
}
