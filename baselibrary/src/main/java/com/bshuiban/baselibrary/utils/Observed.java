package com.bshuiban.baselibrary.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by xinheng on 18-7-17.
 * 被观察者
 */
public class Observed<B> {
    private final String TAG=getClass().getSimpleName();
    private ArrayBlockingQueue<Observer> mObserverList;
    private boolean next=true;
    public Observed(){
        mObserverList = new ArrayBlockingQueue<>(100);
    }
    public boolean isEffictive(){
        return mObserverList.size()!=0;
    }
    public void onNext(boolean update,B bean){
        if(next) {
            if (null != mObserverList) {
                Iterator<Observer> iterator = mObserverList.iterator();
                while (iterator.hasNext()){
                    Observer next = iterator.next();
                    if(next.filter(bean)){
                        next.dealWithNotice(update,bean);
                    }
                }
            } else {
                throw new RuntimeException("此组已销毁");
            }
        }else{
            Log.e(TAG, "onNext: 已经关闭" );
        }
    }
    public void onComplete(){
        next=false;
    }
    public Observed filter(Filter<B> filter){
        this.filter=filter;
        return this;
    }
    /**
     * 注册/绑定
     * @param observer
     */
    public void subscribe(Observer<B> observer){
        if(null!=observer&&!isHad(observer)){
            mObserverList.add(observer);
        }
    }

    /**
     * 解除注册/绑定
     * @param observer
     */
    public void unsubscribe(Observer observer){
        if(null!=observer) {
            mObserverList.remove(observer);
        }
    }
    protected boolean isHad(Observer t){
        Iterator<Observer> iterator = mObserverList.iterator();
        while (iterator.hasNext()){
            if(t==iterator.next()){
                return true;
            }
        }
        return false;
    }
    public void clear(){
        mObserverList.clear();
        mObserverList=null;
    }
    private Filter<B> filter;

    public interface Filter<B>{
        boolean call(B bean);
    }
}
