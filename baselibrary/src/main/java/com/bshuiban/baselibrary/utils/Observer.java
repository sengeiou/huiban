package com.bshuiban.baselibrary.utils;

/**
 * Created by xinheng on 18-7-17.
 * 观察者
 */
public interface Observer<B> {
    /**
     * 处理消息
     * @param isUpdate　更新
     * @param bean　附加数据
     */
    void dealWithNotice(boolean isUpdate, B bean);
    boolean filter(B bean);
}

