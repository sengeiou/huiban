package com.bshuiban.baselibrary.view.fragment;


import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.adapter.ClassActivityAdapter;
import com.bshuiban.baselibrary.contract.ClassActivityContract;
import com.bshuiban.baselibrary.model.ClassActivityBean;
import com.bshuiban.baselibrary.present.ClassActivityPresent;

import java.util.List;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：班级活动页面
 */
public class ClassActivityFragment extends RecycleViewFragment<ClassActivityBean.DataBean,ClassActivityAdapter,ClassActivityPresent> implements ClassActivityContract.View {
    @Override
    public void updateViewForData(List<ClassActivityBean.DataBean> data) {
        super.updateView(data);
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
        dismissFresh();
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
        return new ClassActivityAdapter(getActivity());
    }

    @Override
    protected void startPresent() {
        String classId = User.getInstance().getClassId();
        tPresent.askInternetForClassActivityData(classId, start,limit);
    }

    public void update(String classId) {
        start=0;
        tPresent.askInternetForClassActivityData(classId, start,limit);
    }
}
