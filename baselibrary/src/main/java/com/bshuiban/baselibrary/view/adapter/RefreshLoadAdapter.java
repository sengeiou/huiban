package com.bshuiban.baselibrary.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.bshuiban.baselibrary.model.HomeworkBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：
 */
public abstract class RefreshLoadAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    /**
     * 数据集合 类型T
     */
    protected List<T> mList;

    /**
     * 设置list集合
     * @param list
     */
    public void setList(List<T> list){
        this.mList=list;
        notifyDataSetChanged();
    }
    public boolean isEffictive(){
        return HomeworkBean.isEffictive(mList);
    }
    /**
     * 更新mList集合
     * @param list
     */
    public void updateList(List<T> list){
        if(null==list||list.size()==0){
            return;
        }
        if(null==mList){
            setList(list);
        }else {
            int sizeOld = mList.size();
            int size = list.size();
            mList.addAll(list);
            notifyItemRangeChanged(sizeOld,size);
        }
    }
    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }
    protected String cleanString(String s){
        if(null==s){
            return "";
        }
        return s;
    }
}
