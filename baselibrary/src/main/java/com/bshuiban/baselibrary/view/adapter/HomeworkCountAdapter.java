package com.bshuiban.baselibrary.view.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.LogUtils;
import com.bshuiban.baselibrary.view.customer.ScaleTextView;

import java.util.List;

/**
 * Created by xinheng on 2018/6/1.<br/>
 * describe：
 */
public class HomeworkCountAdapter extends RecyclerView.Adapter<HomeworkCountAdapter.HomeworkCountHolder> {
    private final int type;
    private int mPosition = 0;
    private List<HomeworkBean> mList;
    private int dp1;
    private int colorSelected;
    private int colorUnselected;

    public HomeworkCountAdapter(int type) {
        this.type = type;
    }

    public void setList(List<HomeworkBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public List<HomeworkBean> getList() {
        return mList;
    }

    private boolean getStatue(HomeworkBean bean, int type) {
        switch (type) {
            case 0://学生做题
                return bean.getComplete();
            case 1://老师批改
                return bean.getCorrect();
            default:
                return false;
        }
    }

    @NonNull
    @Override
    public HomeworkCountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ScaleTextView itemView = new ScaleTextView(parent.getContext());
        int dimension = (int) parent.getResources().getDimension(R.dimen.dp_30);
        if (dp1 == 0) {
            dp1 = (int) TypedValue.applyDimension(1, 1, parent.getResources().getDisplayMetrics());
            colorSelected = parent.getResources().getColor(R.color.guide_start_btn);
            colorUnselected = parent.getResources().getColor(R.color.tab_unselect);
        }
        itemView.setLayoutParams(new ViewGroup.LayoutParams(dimension, dimension));
        itemView.setGravity(Gravity.CENTER);
        return new HomeworkCountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkCountAdapter.HomeworkCountHolder holder, int position) {
        holder.textView.setText(String.valueOf(position + 1));
        HomeworkBean tag = mList.get(position);
        holder.textView.setTag(position);
        if (mPosition == position) {
            setTextViewDrawable(holder.textView, getStatue(tag, type),true);
            holder.textView.setScallTag(true);
        } else {
            setTextViewDrawable(holder.textView, getStatue(tag, type),false);
            holder.textView.setScallTag(false);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void notifyItemChangedPosition() {
        notifyItemChanged(mPosition);
    }

    class HomeworkCountHolder extends RecyclerView.ViewHolder {
        ScaleTextView textView;

        public HomeworkCountHolder(View itemView) {
            super(itemView);
            textView = (ScaleTextView) itemView;
            textView.setOnClickListener(v -> {
                Object tag = v.getTag();
                if (tag instanceof Integer && mPosition != (int) tag) {
                    if (null != onItemClickListener) {
                        int position = mPosition;
                        mPosition = (int) tag;
                        LogUtils.e("TAG", "SortHomeworkHolder: " + position + ", selected=" + mPosition);
                        onItemClickListener.itemClick(mList.get(mPosition));
                        notifyItemChanged(position);
                        notifyItemChanged(mPosition);
                    }
                    //setScaleLarge(v);
//                    if (null != lastView) {
//                        setScaleSmall(lastView);
//                        lastView = v;
//                    }
                }
            });
        }
    }

    private void setTextViewDrawable(TextView textView, boolean statue, boolean isSelected) {
        int colorBG, colorSlide, colorText;
        if (isSelected) {//选中
            if (statue) {
                colorBG = colorSelected;
                colorSlide = colorSelected;
                colorText = Color.WHITE;
            } else {
                colorBG = Color.WHITE;
                colorSlide = colorSelected;
                colorText = colorSelected;
            }
        } else {//未选中
            if (statue) {
                colorBG = colorSelected;
                colorSlide = colorSelected;
                colorText = Color.WHITE;
            } else {
                colorBG = Color.WHITE;
                colorSlide = colorUnselected;
                colorText = colorUnselected;
            }
        }
        textView.setTextColor(colorText);
        textView.setBackground(getGradientDrawable(colorBG, colorSlide));
    }

    private Drawable getGradientDrawable(int colorBG, int colorSlide) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(colorBG);
        drawable.setCornerRadius(80);
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setStroke(dp1, colorSlide);
        return drawable;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void itemClick(HomeworkBean homeworkBean);
    }
}
