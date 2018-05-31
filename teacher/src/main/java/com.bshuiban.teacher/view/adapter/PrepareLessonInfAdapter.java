package com.bshuiban.teacher.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.teacher.R;
import com.bshuiban.teacher.model.PrepareLessonBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：
 */
public class PrepareLessonInfAdapter extends RecyclerView.Adapter<PrepareLessonInfAdapter.PrepareLessonInfHolder>{
    private List<PrepareLessonBean.DataBean.ClassArrBean> mList;
    public void setList(List<PrepareLessonBean.DataBean.ClassArrBean> list) {
        mList=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PrepareLessonInfHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PrepareLessonInfHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_prepare_lesson_inf_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PrepareLessonInfHolder holder, int position) {
        holder.textView.setText(TextUtils.cleanNull(mList.get(position).getClassName()));
        holder.textView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    class PrepareLessonInfHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public PrepareLessonInfHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            textView.setOnClickListener(v -> {
                if(null!=listener) {
                    int tag = (int) v.getTag();
                    listener.itemClick(tag);
                }
            });
        }
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void itemClick(int position);
    }
}
