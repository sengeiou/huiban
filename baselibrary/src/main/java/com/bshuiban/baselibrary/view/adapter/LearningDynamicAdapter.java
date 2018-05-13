package com.bshuiban.baselibrary.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.LearningDynamicBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：学士动态适配器
 */
public class LearningDynamicAdapter extends RefreshLoadAdapter<LearningDynamicBean.DataBean,LearningDynamicAdapter.LearningDynamicHolder> {
    private Context mContext;
    public LearningDynamicAdapter(Context context){
        mContext=context;
    }
    @Override
    public LearningDynamicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_learning_dynamic_item, parent, false);
        return new LearningDynamicHolder(view);
    }

    RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_menu_camera).circleCrop();
    @Override
    public void onBindViewHolder(LearningDynamicHolder holder, int position) {
        LearningDynamicBean.DataBean bean = mList.get(position);
        Glide.with(mContext).load(bean.getImgUrl()).apply(requestOptions).into(holder.iv);
        holder.tv_text.setText(cleanString(bean.getContent()));
        holder.tv_date.setText(cleanString(bean.getAddtime()));
    }

    public class LearningDynamicHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv_text;
        private TextView tv_date;
        public LearningDynamicHolder(View itemView) {
            super(itemView);
            iv=(ImageView) itemView.findViewById(R.id.iv);
            tv_text=(TextView) itemView.findViewById(R.id.tv_text);
            tv_date=(TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
