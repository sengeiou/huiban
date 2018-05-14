package com.bshuiban.baselibrary.view.webview.webFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bshuiban.baselibrary.contract.HomePageContract;
import com.bshuiban.baselibrary.present.HomePageParent;

/**
 * Created by xinheng on 2018/5/14.<br/>
 * describe：首页
 */
public class HomePageFragment extends InteractionBaseWebViewFragment<HomePageParent> implements HomePageContract.View {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new HomePageParent(this);
        tPresent.askInternet("",getJsonString());
    }
    @Override
    public void update(Bundle bundle) {

    }

    @Override
    public void updateView() {

    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {

    }
}
