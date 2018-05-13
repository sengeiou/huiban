package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：
 */
public interface BaseView {
    /**
     * 启动等待窗
     */
    void startDialog();

    /**
     * 取消弹窗
     */
    void dismissDialog();
    void fail(String error);
}
