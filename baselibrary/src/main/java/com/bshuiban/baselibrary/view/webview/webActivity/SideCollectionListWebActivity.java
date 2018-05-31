package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import com.bshuiban.baselibrary.contract.SideCollectionListContract;
import com.bshuiban.baselibrary.present.SideCollectionListPresent;

public class SideCollectionListWebActivity extends BaseWebActivity<SideCollectionListPresent> implements SideCollectionListContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new SideCollectionListPresent(this);
        loadFileHtml("myFavorite");
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadCollectionListData();
    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("rend",(json));
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
}
