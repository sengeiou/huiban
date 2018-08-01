package com.bshuiban.baselibrary.utils;

import com.bshuiban.baselibrary.model.TeachClassBean;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by xinheng on 2018/7/16.<br/>
 * describe：班级切换 监听类
 */
public class ClassChange {
    private TeachClassBean.DataBean dataBean;
    private ArrayList<OnChangeListener> mlistListener;
    public ClassChange(){
        mlistListener=new ArrayList();
    }

    public void setDataBean(TeachClassBean.DataBean dataBean) {
        if(dataBean!=this.dataBean){
            this.dataBean=dataBean;
            for (int i = 0; i < mlistListener.size(); i++) {
                OnChangeListener onChangeListener = mlistListener.get(i);
                onChangeListener.changeClass(dataBean);
            }
        }

    }
    public void setOnChangeListener(OnChangeListener onChangeListener){
        if(!isHad(onChangeListener)) {
            mlistListener.add(onChangeListener);
        }
    }
    private boolean isHad(OnChangeListener onChangeListener){
        Iterator<OnChangeListener> iterator = mlistListener.iterator();
        while (iterator.hasNext()){
            if(iterator.next()==onChangeListener){
                return true;
            }
        }
        return false;
    }
    public void clear(){
        mlistListener.clear();
        mlistListener=null;
        dataBean=null;
    }
    public interface OnChangeListener{
        void changeClass(TeachClassBean.DataBean dataBean);
    }
}
