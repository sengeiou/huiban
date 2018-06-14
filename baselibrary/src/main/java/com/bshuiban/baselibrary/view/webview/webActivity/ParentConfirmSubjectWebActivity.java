package com.bshuiban.baselibrary.view.webview.webActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.ParentConfirmSubjectContract;
import com.bshuiban.baselibrary.present.ParentConfirmSubjectPresent;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.UserTypeHtml;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：家长确认后- 学科列表
 */
public abstract class ParentConfirmSubjectWebActivity<T extends ParentConfirmSubjectPresent> extends BaseWebActivity<T> implements ParentConfirmSubjectContract.View{

    private int subjectId;
    private int nowWtype=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=getPresent();
        subjectId = getIntent().getIntExtra("subjectId", -1);
        loadFileHtml("subject");
        registerWebViewH5Interface(new ParentConfirmSubjectHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadSubjectList(subjectId,nowWtype);
    }

    @Override
    public void updateView(int type, String json) {
        nowWtype=type;
        loadJavascriptMethod("worknotice",json);
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

    protected abstract T getPresent();
    protected class ParentConfirmSubjectHtml extends UserTypeHtml{
        /**
         *
         * @param type ,课前1，课后3
         */
        @JavascriptInterface
        public void changeItem(int type){
            if(nowWtype!=type){
                tPresent.loadSubjectList(subjectId,type);
            }
        }
    }
}
