package com.bshuiban.baselibrary.view.adapter;

import android.arch.lifecycle.GeneratedAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by xinheng on 2018/5/23.<br/>
 * describe：
 */
public abstract class GeneralSituationAdapter<T> extends RecyclerView.Adapter<GeneralSituationAdapter.GeneralSituationHolder> {
    protected List<T> mList;
    private Context mContext;
    private boolean tag;
    public GeneralSituationAdapter(){
        tag= User.getInstance().isParents();
    }
    public void setContext(Context context) {
        mContext = context;
    }

    public void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GeneralSituationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GeneralSituationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_general_situation_item, parent, false));
    }

    RequestOptions requestOptions = new RequestOptions()
            .error(R.mipmap.default_head)
            .circleCrop();

    @Override
    public void onBindViewHolder(@NonNull GeneralSituationAdapter.GeneralSituationHolder holder, int position) {
        Glide.with(mContext).load(getImagePath(position)).apply(requestOptions).into(holder.iv);
        holder.tv_name.setText(TextUtils.cleanNull(getName(position)));
        holder.tv_subject_name.setText(getSubjectName(position));
        holder.iv_guan_zhu.setTag(position);
        holder.iv_liu_yan.setTag(position);
        if(tag){
            holder.iv_liu_yan.setVisibility(View.GONE);
            holder.iv_guan_zhu.setVisibility(View.GONE);
        }else {
            if (getGuanzhu(position)) {
                holder.iv_liu_yan.setVisibility(View.VISIBLE);
                holder.iv_guan_zhu.setImageResource(R.mipmap.guanzhu);
            } else {
                holder.iv_liu_yan.setVisibility(View.INVISIBLE);
                holder.iv_guan_zhu.setImageResource(R.mipmap.add_attention);
                holder.iv_guan_zhu.setVisibility(View.VISIBLE);
            }
        }
    }

    protected abstract String getImagePath(int position);

    protected abstract String getName(int position);

    protected abstract String getSubjectName(int position);
    protected abstract boolean getGuanzhu(int position);
    //protected abstract Object getChangeMsg(int position);
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    protected class GeneralSituationHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_name;
        TextView tv_subject_name;
        ImageView iv_guan_zhu, iv_liu_yan;

        public GeneralSituationHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.iv);
            tv_name = view.findViewById(R.id.tv_name);
            tv_subject_name = view.findViewById(R.id.tv_subject_name);
            iv_guan_zhu = view.findViewById(R.id.iv_guan_zhu);
            iv_liu_yan = view.findViewById(R.id.iv_liu_yan);
            iv_guan_zhu.setOnClickListener(onClickListener);
            iv_liu_yan.setOnClickListener(onClickListener);
        }
    }

    private View.OnClickListener onClickListener = v -> {
        int id = v.getId();
        Object tag = v.getTag();
        int position;
        if (tag instanceof Integer) {
            position = (int) tag;
        } else {
            return;
        }
        if (id == R.id.iv_guan_zhu) {
            toNextPageGuanZhu(position);
        } else {
            toNextPageLiuYan(position);
        }
    };

    protected abstract void toNextPageGuanZhu(int position);

    protected abstract void toNextPageLiuYan(int position);
}
