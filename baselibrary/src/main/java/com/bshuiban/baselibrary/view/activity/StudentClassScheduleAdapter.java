package com.bshuiban.baselibrary.view.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.StudentClassClassScheduleBean;

import java.util.List;

/**
 * Author：weidanyan
 * Email：1022664273@qq.com
 * Description：This is StudentClassLearningDynamicsAdapter
 * Time: 2018/4/12
 */
public class StudentClassScheduleAdapter extends RecyclerView.Adapter<StudentClassScheduleAdapter.ItemViewHolder> {
    Context context;
    private OnItemClickListener onItemClickListener;
    List<List<StudentClassClassScheduleBean.DataBean>> mData;

    public StudentClassScheduleAdapter(Context context, List<List<StudentClassClassScheduleBean.DataBean>> list) {
        this.mData = list;
        this.context = context;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View itemView, int postion);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemViewHolder holder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_student_class_schedule, viewGroup, false), onItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tv_position.setText(String.valueOf(position+1));
        if(mData.get(position) == null || mData.get(position).get(0).getSubjectName() == null || "".equals(mData.get(position).get(0).getSubjectName())){
            holder.tv_monday.setText("-");
        }
        holder.tv_monday.setText(mData.get(position).get(0).getSubjectName()+"\n"+mData.get(position).get(0).getTeacherName());

        if(mData.get(position) == null || mData.get(position).get(1).getSubjectName() == null || "".equals(mData.get(position).get(1).getSubjectName())){
            holder.tv_tuesday.setText("-");
        }
        holder.tv_tuesday.setText(mData.get(position).get(1).getSubjectName()+"\n"+mData.get(position).get(1).getTeacherName());

        if(mData.get(position) == null || mData.get(position).get(2).getSubjectName() == null || "".equals(mData.get(position).get(2).getSubjectName())){
            holder.tv_wednesday.setText("-");
        }
        holder.tv_wednesday.setText(mData.get(position).get(2).getSubjectName()+"\n"+mData.get(position).get(2).getTeacherName());

        if(mData.get(position) == null || mData.get(position).get(3).getSubjectName() == null || "".equals(mData.get(position).get(3).getSubjectName())){
            holder.tv_thursday.setText("-");
        }
        holder.tv_thursday.setText(mData.get(position).get(3).getSubjectName()+"\n"+mData.get(position).get(3).getTeacherName());

        if(mData.get(position) == null || mData.get(position).get(4).getSubjectName() == null || "".equals(mData.get(position).get(4).getSubjectName())){
            holder.tv_friday.setText("-");
        }
        holder.tv_friday.setText(mData.get(position).get(4).getSubjectName()+"\n"+mData.get(position).get(4).getTeacherName());

        if(mData.get(position) == null || mData.get(position).get(5).getSubjectName() == null || "".equals(mData.get(position).get(5).getSubjectName())){
            holder.tv_saturday.setText("-");
        }
        holder.tv_saturday.setText(mData.get(position).get(5).getSubjectName()+"\n"+mData.get(position).get(5).getTeacherName());

        if(mData.get(position) == null || mData.get(position).get(6).getSubjectName() == null || "".equals(mData.get(position).get(6).getSubjectName())){
            holder.tv_sunday.setText("-");
        }
        holder.tv_sunday.setText(mData.get(position).get(6).getSubjectName()+"\n"+mData.get(position).get(6).getTeacherName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_position;
        TextView tv_monday;
        TextView tv_tuesday;
        TextView tv_wednesday;
        TextView tv_thursday;
        TextView tv_friday;
        TextView tv_saturday;
        TextView tv_sunday;

        public ItemViewHolder(View itemView, StudentClassScheduleAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_position = itemView.findViewById(R.id.tv_position);
            tv_monday = itemView.findViewById(R.id.tv_monday);
            tv_tuesday = itemView.findViewById(R.id.tv_tuesday);
            tv_wednesday = itemView.findViewById(R.id.tv_wednesday);
            tv_thursday = itemView.findViewById(R.id.tv_thursday);
            tv_friday = itemView.findViewById(R.id.tv_friday);
            tv_saturday = itemView.findViewById(R.id.tv_saturday);
            tv_sunday = itemView.findViewById(R.id.tv_sunday);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }
}