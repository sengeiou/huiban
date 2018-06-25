package com.bshuiban.parents.view.webView.webActivity;

import com.bshuiban.baselibrary.view.webview.webActivity.ParentConfirmWebActivity;
import com.bshuiban.parents.contract.ParentsHomeContract;
import com.bshuiban.parents.modul.ParentsUser;
import com.bshuiban.parents.present.ParentConfirmPresent;

/**
 * Created by xinheng on 2018/6/6.<br/>
 * describe：
 */
public class PatentsParentConfirmWebActivity extends ParentConfirmWebActivity<ParentConfirmPresent> implements ParentsHomeContract.View {
    @Override
    protected ParentConfirmPresent getPresent() {
        return new ParentConfirmPresent(this);
    }

    @Override
    protected Class<?> getActivityClass() {
        return ParentsParentConfirmSubjectWebActivity.class;
    }

    @Override
    public void updateSlideView(ParentsUser.DataBean data) {

    }
}
