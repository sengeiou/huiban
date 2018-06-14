package com.bshuiban.baselibrary.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.internet.UrlManage;
import com.bshuiban.baselibrary.model.ClassActivityBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：
 */
public class ClassActivityAdapter extends RefreshLoadAdapter<ClassActivityBean.DataBean,ClassActivityAdapter.ClassActivityHolder> {
    private Context mContext;
    public ClassActivityAdapter(Context context){
        mContext=context;
    }
    @Override
    public ClassActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassActivityHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ClassActivityHolder holder, int position) {
        ClassActivityBean.DataBean bean = mList.get(position);
        String text = cleanString(bean.getContent());
        dealWithText(holder.tv_text,text);
        //holder.tv_text.setText(text);
        holder.tv_date.setText(cleanString(bean.getSendName())+" "+cleanString(bean.getToNow()));
    }
    private void dealWithText(TextView textView,String string) {
        MyImageGetter imgGetter=new MyImageGetter(textView,string);
        if(string.contains("img")) {
            Html.fromHtml(string, imgGetter, null);
        }else{
            textView.setText(Html.fromHtml(string, imgGetter, null));
        }
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
    class MyImageGetter implements Html.ImageGetter {
        Drawable drawable = null;
        private TextView textView;
        private String text;
        public MyImageGetter(TextView textView,String s) {
            this.text=s;
            this.textView=textView;
        }

        public Drawable getDrawable(String source) {
            if(drawable==null) {
                source = UrlManage.getInstance().BASE_URL + source;
                Log.e("TAG", "getDrawable: " + source);
                Glide.with(mContext).asDrawable().load(source).into(new ViewTarget<TextView, Drawable>(textView) {

                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        drawable=resource;
                        drawable.setBounds(0,0,drawable.getIntrinsicWidth()+20,drawable.getIntrinsicHeight()+20);
                        this.view.setText(Html.fromHtml(text, MyImageGetter.this, null));
                    }
                });
            }
            Log.e("TAG", "getDrawable: dr"+(drawable!=null) );
            return drawable;
        }
    }
}
