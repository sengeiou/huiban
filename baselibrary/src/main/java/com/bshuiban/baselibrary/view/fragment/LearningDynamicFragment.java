package com.bshuiban.baselibrary.view.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.TeachClassBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.utils.ClassChange;
import com.bshuiban.baselibrary.view.adapter.LearningDynamicAdapter;
import com.bshuiban.baselibrary.contract.LearningDynamicContract;
import com.bshuiban.baselibrary.model.LearningDynamicBean;
import com.bshuiban.baselibrary.present.LearningDynamicPresent;
import com.bshuiban.baselibrary.view.pulltorefresh.BaseRefreshListener;
import com.bshuiban.baselibrary.view.pulltorefresh.PullToRefreshLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 学习动态
 */
public class LearningDynamicFragment extends RecycleViewFragment<LearningDynamicBean.DataBean, LearningDynamicAdapter, LearningDynamicPresent> implements LearningDynamicContract.View,ClassChange.OnChangeListener {

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
        super.initView(view);
    }

    @Override
    protected LearningDynamicAdapter getAdapter() {
        return new LearningDynamicAdapter(getActivity());
    }

    @Override
    protected void startPresent() {
        tPresent.askInternetForLearningDynamicData(User.getInstance().getUserId(), start, limit);
    }

    @Override
    public void startDialog() {
        showLoadingDialog();
    }

    @Override
    public void dismissDialog() {
        dismissLoadingDialog();
    }

    @Override
    public void fail(String error) {
        toast(error);
        dismissFresh();
    }

    @Override
    public void updateViewForData(List<LearningDynamicBean.DataBean> list) {
        super.updateView(list);
    }

    @Override
    public void changeClass(TeachClassBean.DataBean dataBean) {

    }
}
