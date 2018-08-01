package com.bshuiban.baselibrary.utils;

/**
 * Created by xinheng on 2018/7/19.<br/>
 * describe：
 */
public abstract class OnObserver<B> implements Observer<B>{


    @Override
    public boolean filter(B bean) {
        return true;
    }
}
