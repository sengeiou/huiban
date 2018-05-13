package com.bshuiban.baselibrary.view.fragment;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.view.adapter.ClassActivityAdapter;
import com.bshuiban.baselibrary.contract.ClassActivityContract;
import com.bshuiban.baselibrary.model.ClassActivityBean;
import com.bshuiban.baselibrary.present.ClassActivityPresent;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：班级活动页面
 */
public class ClassActivityFragment extends RecycleViewFragment<ClassActivityBean,ClassActivityAdapter,ClassActivityPresent> implements ClassActivityContract.View {

    @Override
    public void updateViewForData() {

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
    protected void initPresent() {
        tPresent=new ClassActivityPresent(this);
    }

    @Override
    public int getLayoutResourcesId() {
        return R.layout.layout_recycle_item;
    }

    @Override
    protected ClassActivityAdapter getAdapter() {
        return new ClassActivityAdapter();
    }

    @Override
    protected void startPresent() {
        tPresent.askInternetForClassActivityData("", start,limit);
    }
}
