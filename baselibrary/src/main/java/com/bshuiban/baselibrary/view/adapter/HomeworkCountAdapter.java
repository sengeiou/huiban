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
import com.bshuiban.baselibrary.model.HomeworkBean;

import java.util.List;

/**
 * Created by xinheng on 2018/6/1.<br/>
 * describe：
 */
public class HomeworkCountAdapter extends RecyclerView.Adapter<HomeworkCountAdapter.HomeworkCountHolder> {
    private int mPosition;
    private List<HomeworkBean> mList;
    private int dp1;
    private int colorSelected;
    private int colorUnselected;
    private int colorUnselectedBg;
    public void setList(List<HomeworkBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeworkCountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView itemView = new TextView(parent.getContext());
        int dimension = (int) parent.getResources().getDimension(R.dimen.dp_30);
        if(dp1==0){
            dp1= (int) TypedValue.applyDimension(1,1,parent.getResources().getDisplayMetrics());
            colorSelected=parent.getResources().getColor(R.color.guide_start_btn);
            colorUnselected=parent.getResources().getColor(R.color.tab_unselect);
            colorUnselectedBg=parent.getResources().getColor(R.color.line_bord);
        }
        itemView.setLayoutParams(new ViewGroup.LayoutParams(dimension,dimension));
        itemView.setGravity(Gravity.CENTER);
        return new HomeworkCountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkCountAdapter.HomeworkCountHolder holder, int position) {
        holder.textView.setText(String.valueOf(position+1));
        HomeworkBean tag = mList.get(position);
        holder.textView.setTag(position);
        setTextViewDrawable(holder.textView,tag.getCorrect(),tag.getComplete());
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public void notifyItemChangedPosition() {
        notifyItemChanged(mPosition);
    }

    class HomeworkCountHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public HomeworkCountHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView;
            textView.setOnClickListener(v->{
                Object tag = v.getTag();
                if(null!=onItemClickListener&&tag instanceof Integer){
                    int position = (int) tag;
                    mPosition=position;
                    onItemClickListener.itemClick(mList.get(position));
                }
            });
        }
    }
    private void setTextViewDrawable(TextView textView,boolean correct,boolean complete){
       int colorBG,colorSlide,colorText;
       if(correct){
           colorBG=colorSelected;
           colorSlide=colorSelected;
           colorText=Color.WHITE;
       }else{
           if(complete){
               colorBG=Color.WHITE;
               colorSlide=colorSelected;
               colorText=colorSelected;
           }else{
               colorBG=colorUnselectedBg;
               colorSlide=colorUnselected;
               colorText=colorUnselected;
           }
       }
       textView.setTextColor(colorText);
       textView.setBackground(getGradientDrawable(colorBG,colorSlide));
    }
    private Drawable getGradientDrawable(int colorBG,int colorSlide){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setColor(colorBG);
        drawable.setCornerRadius(80);
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setStroke(dp1,colorSlide);
        return drawable;
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void itemClick(HomeworkBean homeworkBean);
    }
}
