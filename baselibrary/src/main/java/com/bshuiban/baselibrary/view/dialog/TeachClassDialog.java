package com.bshuiban.baselibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.TeachClassBean;
import com.bshuiban.baselibrary.utils.DialogUtils;
import com.bshuiban.baselibrary.view.customer.CircleSelectView;

import java.util.List;

/**
 * Created by xinheng on 2018/7/11.<br/>
 * describe：所教班级
 */
public class TeachClassDialog {
    private Dialog dialog;
    private Context mContext;
    private LinearLayout llParent;
    private View tvSend;

    public TeachClassDialog(Context context){
        mContext=context;
        dialog=new Dialog(context);
        initDialog();
    }
    private void initDialog(){
        dialog.setContentView(R.layout.layout_teach_class_item);
        llParent = dialog.findViewById(R.id.llParent);
        tvSend = dialog.findViewById(R.id.tvSend);
    }
    public void setTexts(List<TeachClassBean.DataBean> list){
        llParent.removeAllViews();
        if(HomeworkBean.isEffictive(list)){
            for (int i = 0; i < list.size(); i++) {
                TeachClassBean.DataBean dataBean = list.get(i);
                CircleSelectView childView = getChildView(dataBean.getClassName());
                childView.setTag(dataBean.getClassId());
                llParent.addView(childView);
            }
        }
    }
    public void setTexts(TeachClassBean.DataBean... ss){
        llParent.removeAllViews();
        if(null==ss){
            return;
        }
        for (int i = 0; i < ss.length; i++) {
            TeachClassBean.DataBean dataBean = ss[i];
            CircleSelectView childView = getChildView(dataBean.getClassName());
            childView.setTag(dataBean.getClassId());
            llParent.addView(childView);
        }
    }
    public void setSendClickListener(View.OnClickListener onClickListener){
        tvSend.setOnClickListener(onClickListener);
    }
    private CircleSelectView getChildView(String s){
        CircleSelectView circleSelectView = new CircleSelectView(mContext);
        int dp10 = getDp(R.dimen.dp_10);
        int dp3 = getDp(R.dimen.dp_6);
        int dp8 = getDp(R.dimen.dp_8);
        circleSelectView.setParmar(dp3, dp10,getColor(R.color.gray1),getColor(R.color.guide_start_btn),s,getDp(R.dimen.dp_16));
        circleSelectView.setPadding(dp10*2,dp8,dp10,dp8);
        circleSelectView.setOnClickListener(this::onClick);
        return circleSelectView;
    }
    private void onClick(View v){
        if(v instanceof CircleSelectView){
            ((CircleSelectView) v).toggle();
        }
    }
    private int getDp(int id){
        return mContext.getResources().getDimensionPixelSize(id);
    }
    private int getColor(int id){
        return ContextCompat.getColor(mContext,id);
    }

    public String getSelectClass() {
        int childCount = llParent.getChildCount();
        StringBuffer buffer=new StringBuffer();
        for (int i = 0; i < childCount; i++) {
            View childAt = llParent.getChildAt(i);
            if(((CircleSelectView)childAt).isSelect()){
               buffer.append(childAt.getTag());
               if(i<childCount-1) {
                   buffer.append(",");
               }
            }
        }
        return buffer.toString();
    }
    public void show(){
        dialog.show();
        DialogUtils.setDialogWithMatcherScreen(dialog, Gravity.BOTTOM);
    }
    public boolean isShow(){
        return dialog.isShowing();
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
