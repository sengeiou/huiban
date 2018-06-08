package com.bshuiban.baselibrary.view.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：
 */
public class SortHomewrokAdapter extends RecyclerView.Adapter<SortHomewrokAdapter.SortHomeworkHolder>{
    private int mPosition=0;
    private int count;
    private int dp1;
    private int colorSelected;
    private int colorUnselected;
    private int colorUnselectedBg;
    public void setCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SortHomeworkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(dp1==0) {
            dp1 = (int) TypedValue.applyDimension(1, 1, parent.getResources().getDisplayMetrics());
            colorSelected=parent.getResources().getColor(R.color.guide_start_btn);
            colorUnselected=parent.getResources().getColor(R.color.tab_unselect);
            colorUnselectedBg=parent.getResources().getColor(R.color.line_bord);
        }
        int dimension = (int) parent.getResources().getDimension(R.dimen.dp_30);
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(dimension, dimension));
        textView.setGravity(Gravity.CENTER);
        return new SortHomeworkHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull SortHomeworkHolder holder, int position) {
        holder.textView.setText(String.valueOf(position+1));
        holder.textView.setTag(position);
        if(mPosition ==position){
            holder.textView.setTextColor(Color.WHITE);
            holder.textView.setBackground(getGradientDrawable(colorSelected,colorSelected));
        }else{
            holder.textView.setTextColor(colorUnselected);
            holder.textView.setBackground(getGradientDrawable(colorUnselectedBg,colorUnselected));
        }
    }
    private Drawable getGradientDrawable(int colorBG, int colorSlide){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setColor(colorBG);
        drawable.setCornerRadius(80);
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setStroke(dp1,colorSlide);
        return drawable;
    }
    @Override
    public int getItemCount() {
        return count;
    }

    class SortHomeworkHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public SortHomeworkHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView;
            textView.setOnClickListener(v->{
                if(null!=onItemClickListener){
                    int tag = (int) v.getTag();
                    if(mPosition!=tag) {
                        int position=mPosition;
                        mPosition=tag;
                        notifyItemChanged(position);
                        notifyItemChanged(mPosition);
                        onItemClickListener.itemClick(tag);
                    }
                }
            });
        }
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void itemClick(int position);
    }
}
