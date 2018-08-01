package com.bshuiban.baselibrary.utils;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by xinheng on 18-7-17.
 * 存储被观察者map
 *
 */
public class ObserveModeGroupList {
    private final String TAG = getClass().getSimpleName();
    private boolean mTag=true;

    private static ObserveModeGroupList instance;

    public static ObserveModeGroupList getInstance() {
        if (null == instance) {
            synchronized (ObserveModeGroupList.class) {
                if (null == instance) {
                    instance = new ObserveModeGroupList();
                }
            }
        }
        return instance;
    }

    private HashMap<String, Observed> hashMapObserved;
    private Observed observed;

    private ObserveModeGroupList() {
        hashMapObserved = new HashMap<>();
        observed = new Observed();
    }

    public void setTag(boolean mTag) {
        this.mTag = mTag;
    }

    /**
     * 注册/绑定
     * @param cls
     * @param observer
     * @param <B>
     * @return
     */
    public <B> Observer register(Class<B> cls,Observer<B> observer) {
        checkNull(cls);
        checkNull(observer);
        return register(cls.getPackage().getName(),observer);
    }

    private Observer register(String classPackageName,Observer observer) {
        checkNull(classPackageName);
        checkNull(observer);
        createObserved(classPackageName).subscribe(observer);
        return observer;
    }

    /**
     * 解除绑定
     * @param cls　类对象
     * @param observer　观察者
     */
    public void unregister(Class<?> cls,Observer observer) {
        checkNull(cls);
        unregister(cls.getPackage().getName(),observer);
    }

    private void unregister(String classPackageName,Observer observer) {
        checkNull(classPackageName);
        Observed remove = hashMapObserved.get(classPackageName);
        if(null!=remove){
            remove.unsubscribe(observer);
            if(!remove.isEffictive()){
                remove.clear();
                hashMapObserved.remove(classPackageName);
            }
        }
    }

    private void recycle(){
        Set<Map.Entry<String, Observed>> entries = hashMapObserved.entrySet();
        Iterator<Map.Entry<String, Observed>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Observed> next = iterator.next();
            Observed value = next.getValue();
            if(!value.isEffictive()){
                iterator.remove();
            }
        }
    }

    /**
     * 发送信息
     * @param update　是否更新
     * @param bean　信息类
     * @param <B>　类类型
     */
    public <B> void postNotice(boolean update,B bean) {
        checkNull(bean);
        String name = bean.getClass().getPackage().getName();
        Observed<B> observed = hashMapObserved.get(name);
        if(null==observed){
            log( "postNotice","应该先创建与　"+name+"　关联的被观察者" );
            return;
        }
        observed.onNext(update,bean);
    }

    /**
     * 构造被观察者
     * @param cls 类对象
     * @param <B>　类型
     * @return
     */
    public <B> Observed<B> createObserved(Class<B> cls) {
        checkNull(cls);

        String name = cls.getClass().getPackage().getName();
        return (Observed<B>)createObserved(name);
    }
    public <B> Observed createObserved(String classPackageName) {
        checkNull(classPackageName);

        Observed observed = hashMapObserved.get(classPackageName);
        if(null==observed){
            observed = new Observed<B>();
            hashMapObserved.put(classPackageName, observed);
        }
        return observed;
    }
    private void log(String methodName,String content){
        if(mTag){
            Log.e(TAG, "methodName: "+content);
        }
    }
    private void checkNull(Object o){
        if(null==o){
            throw new RuntimeException("此对象为null");
        }
    }
}
