package com.bshuiban.baselibrary.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.ClassActivityBean;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：
 */
public class ClassActivityAdapter extends RefreshLoadAdapter<ClassActivityBean.DataBean,ClassActivityAdapter.ClassActivityHolder> {
    @Override
    public ClassActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassActivityHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ClassActivityHolder holder, int position) {
        ClassActivityBean.DataBean bean = mList.get(position);
        holder.tv_text.setText(cleanString(bean.getContent()));
        holder.tv_date.setText(cleanString(bean.getSendName())+" "+cleanString(bean.getToNow()));
    }

    class ClassActivityHolder extends RecyclerView.ViewHolder{
        private TextView tv_text;
        private TextView tv_date;
        public ClassActivityHolder(View itemView) {
            super(itemView);
            tv_text=itemView.findViewById(R.id.tv_text);
            tv_date=itemView.findViewById(R.id.tv_date);
        }
    }
}
