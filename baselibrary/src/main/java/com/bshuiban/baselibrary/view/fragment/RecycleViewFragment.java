package com.bshuiban.baselibrary.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.SpaceItemDecoration;
import com.bshuiban.baselibrary.view.adapter.RefreshLoadAdapter;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.view.pulltorefresh.BaseRefreshListener;
import com.bshuiban.baselibrary.view.pulltorefresh.PullToRefreshLayout;

import java.util.List;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：含有一个垂直方向的recyclerView，包含刷新与加载更多
 *
 * @param <T> list集合数据类型
 * @param <A> RecycleView adapter
 * @param <P> Present
 */
public abstract class RecycleViewFragment<T, A extends RefreshLoadAdapter, P extends BasePresent> extends BaseFragment<P> {
    protected RecyclerView recyclerView;
    protected PullToRefreshLayout pullToRefreshLayout;

    /**
     * 加载数量
     */
    protected int limit = 10;
    /**
     * 起始位置
     */
    protected int start = 0;
    protected A adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initPresent();
        super.onCreate(savedInstanceState);
    }
    /**
     * 初始化P层
     */
    protected abstract void initPresent();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourcesId(), container, false);
        recyclerView = getRecycleView(view);
        initView(view);
        initRecycleView();
        startPresent();
        return view;
    }

    /**
     * 获取布局文件id
     */
    public abstract int getLayoutResourcesId();

    /**
     * 若需要改变id，重写它
     *
     * @param view
     * @return
     */
    protected RecyclerView getRecycleView(View view) {
        return view.findViewById(R.id.recycle);
    }

    protected void initView(View view) {
        pullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.pullToRefreshView);
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {

            @Override
            public void refresh() {
                start = 0;
                startPresent();
            }

            @Override
            public void loadMore() {
                if(hasMoreDate()){
                    start+=limit;
                    startPresent();
                }
            }
        });
    }

    ;

    protected abstract A getAdapter();


    protected abstract void startPresent();

    protected void updateView(List<T> list) {
        dismissFresh();
        if (null != list) {
            if (start != 0) {
                adapter.updateList(list);
            } else {
                adapter.setList(list);
            }
        }
    }

    protected void dismissFresh() {
        if(null!=pullToRefreshLayout){
            pullToRefreshLayout.finishRefresh();
            pullToRefreshLayout.finishLoadMore();
        }
    }

    protected void initRecycleView() {
        if (null == recyclerView) {
            throw new RuntimeException("recyclerView=null,please set getRecycleView result is not null");
        }
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layout);
        adapter = getAdapter();
        if (null != adapter) {
            recyclerView.setAdapter(adapter);
        }
//        SpaceItemDecoration spaceItemDecoration=new SpaceItemDecoration(getActivity(),RecyclerView.VERTICAL,20, Color.GRAY);
    }

    protected void initIndexAndLimit() {
        start = 0;
        limit = 10;
    }

    /**
     * 设置没有更多数据
     */
    protected void setHasNoMoreDate() {
        limit = 0;
    }

    /**
     * 是否有更多数据
     */
    protected boolean hasMoreDate() {
        if (0 < limit) {
            return true;
        } else {
            toast("没有更多数据了");
            return false;
        }
    }
}
