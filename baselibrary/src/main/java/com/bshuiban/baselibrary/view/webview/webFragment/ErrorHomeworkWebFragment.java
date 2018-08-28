package com.bshuiban.baselibrary.view.webview.webFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;

import com.bshuiban.baselibrary.contract.ErrorHomeworkAnalysisContract;
import com.bshuiban.baselibrary.contract.ErrorHomeworkContract;
import com.bshuiban.baselibrary.present.ErrorHomeworkPresent;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webActivity.ConsolidationWebActivity;
import com.bshuiban.baselibrary.view.webview.webActivity.ErrorFilterActivity;
import com.bshuiban.baselibrary.view.webview.webActivity.ErrorHomeworkAnalysisActivity;

/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：错题本
 */
public class ErrorHomeworkWebFragment extends InteractionBaseWebViewFragment<ErrorHomeworkPresent> implements ErrorHomeworkContract.View {
    private static final int ErrorHomework=100;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new ErrorHomeworkPresent(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        loadFileHtml("wrong");
        ErrorHomeworkHtml html=new ErrorHomeworkHtml();
        html.setOnListener(new MessageList.OnMessageListListener(){
            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }

            @Override
            public void refresh() {
                tPresent.refresh();
            }
        });
        registerWebViewH5Interface(html);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, "onHiddenChanged: "+hidden );
        if(!hidden){
            tPresent.refresh();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadErrorHomeworkData();
    }

    @Override
    public void updateList(String json) {//[]
        loadJavascriptMethod("getMsg",json);
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        Log.e(TAG, "fail: "+error );
        toast(error);
    }

    @Override
    public void update(Bundle bundle) {

    }

    @Override
    public void toErrorHomeworkAnalysisPage(String json) {
        startActivity(new Intent(getActivity(), ErrorHomeworkAnalysisActivity.class).putExtra("text",json));
    }
   /* @Override
    public void toConsolidationWebActivity(String json){
        startActivity(new Intent(getActivity(),ErrorHomeworkAnalysisActivity.class).putExtra("json",json));
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        switch (requestCode){
            case ErrorHomework:
                if(i!=null){
                    int mSubjectId = i.getIntExtra("mSubjectId",-1);
                    int mVersionId = i.getIntExtra("mVersionId",-1);
                    int mFasId = i.getIntExtra("mFasId",-1);
                    int mChapBranId = i.getIntExtra("mChapBranId",-1);
                    int mSeriBrandId = i.getIntExtra("mSeriBrandId",-1);
                    int stageId = i.getIntExtra("stageId",-1);
                    String organs = i.getStringExtra("organs");
                    tPresent.clearArray();
                    tPresent.setSelectInf(mSubjectId,mVersionId,mFasId,mChapBranId,mSeriBrandId,stageId,organs);
                }
                break;

        }
    }

    class ErrorHomeworkHtml extends MessageList{
        /**
         * 错题本列表点击
         * @param index
         */
        @JavascriptInterface
        public void itemClick(int index){
            getActivity().runOnUiThread(()->{
                tPresent.itemClick(index);
            });
        }
        @JavascriptInterface
        public void clickFilter(){
            getActivity().runOnUiThread(()->{
                startActivityForResult(new Intent(getActivity(), ErrorFilterActivity.class),ErrorHomework);
            });
        }
    }
}
