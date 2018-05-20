package com.bshuiban.baselibrary.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.MessageBean;
import com.bshuiban.baselibrary.utils.TextUtils;

import java.util.List;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public class ReplyDialog extends MoveDialog {

    private ImageView iv;
    private TextView tv_name, tv_gradle_class, tv_time, tv_text, tv_delete;
    private RecyclerView recycleView;

    public ReplyDialog(@NonNull Context context) {
        super(context);
        setContentView(getReplyDialogView());
    }

    private View getReplyDialogView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_reply_list, null);
        recycleView = view.findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        iv = view.findViewById(R.id.iv);
        tv_name = view.findViewById(R.id.tv_name);
        tv_gradle_class = view.findViewById(R.id.tv_gradle_class);
        tv_time = view.findViewById(R.id.tv_time);
        tv_text = view.findViewById(R.id.tv_text);
        tv_delete = view.findViewById(R.id.tv_delete);
        tv_delete.setVisibility(View.GONE);
        return view;
    }
    public void setViewData(MessageBean.DataBean dataBean){
        if(null==dataBean){
            return;
        }
        setText(tv_name,dataBean.getSendName());
        //setText(tv_gradle_class,dataBean.);
        setText(tv_time,dataBean.getAddTime());
        setText(tv_text,dataBean.getContent());
        List<MessageBean.DataBean.SlistBean> slist = dataBean.getSlist();
        if(null!=slist&&slist.size()>0){
            ReplayAdapter replayAdapter=new ReplayAdapter(slist);
            recycleView.setAdapter(replayAdapter);
        }
    }
    private void setText(TextView tv,String s){
        tv.setText(TextUtils.cleanNull(s));
    }
    class ReplayAdapter extends RecyclerView.Adapter<ReplayAdapter.MyViewHolder>{
        private List<MessageBean.DataBean.SlistBean> mSlistBeans;
        public ReplayAdapter(List<MessageBean.DataBean.SlistBean> slistBeans){
            mSlistBeans=slistBeans;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.layout_liu_yan_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            MessageBean.DataBean.SlistBean slistBean = mSlistBeans.get(position);
            setText(holder.tv_name,slistBean.getSendName());
            setText(holder.tv_time,slistBean.getAddTime());
            setText(holder.tv_text,slistBean.getContent());
        }

        @Override
        public int getItemCount() {
            return mSlistBeans==null?0:mSlistBeans.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView iv;
            TextView tv_name,tv_text,tv_delete,tv_time;
            public MyViewHolder(View view) {
                super(view);
                tv_name=view.findViewById(R.id.tv_name);
                tv_time=view.findViewById(R.id.tv_time);
                tv_text=view.findViewById(R.id.tv_text);
                tv_delete=view.findViewById(R.id.tv_delete);
                iv=findViewById(R.id.iv);
            }
        }
    }
}
