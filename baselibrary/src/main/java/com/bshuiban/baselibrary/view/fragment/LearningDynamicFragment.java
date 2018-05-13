package com.bshuiban.baselibrary.view.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.view.adapter.LearningDynamicAdapter;
import com.bshuiban.baselibrary.contract.LearningDynamicContract;
import com.bshuiban.baselibrary.model.LearningDynamicBean;
import com.bshuiban.baselibrary.present.LearningDynamicPresent;
import com.bshuiban.baselibrary.view.customer.PullToRefreshView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 学习动态
 */
public class LearningDynamicFragment extends RecycleViewFragment<LearningDynamicBean.DataBean, LearningDynamicAdapter, LearningDynamicPresent> implements LearningDynamicContract.View {
    private PullToRefreshView pullToRefreshView;

    @Override
    protected void initPresent() {
        tPresent = new LearningDynamicPresent(this);
    }

    @Override
    public int getLayoutResourcesId() {
        return R.layout.layout_recycle_item;
    }

    @Override
    protected RecyclerView getRecycleView(View view) {
        return view.findViewById(R.id.recycle);
    }

    @Override
    protected void initView(View view) {
        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                startPresent();
            }
        });
    }

    @Override
    protected LearningDynamicAdapter getAdapter() {
        return new LearningDynamicAdapter(getActivity());
    }

    @Override
    protected void startPresent() {
        tPresent.askInternetForLearningDynamicData("", start, limit);
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
    public void updateViewForData(List<LearningDynamicBean.DataBean> list) {
        super.updateView(list);
    }
}
