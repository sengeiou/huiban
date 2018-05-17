package com.bshuiban.baselibrary.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.GeneralSituationContract;
import com.bshuiban.baselibrary.model.GeneralBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.GeneralSituationPresent;
import com.bumptech.glide.Glide;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：概况
 */
public class GeneralSituationFragment extends BaseFragment<GeneralSituationPresent> implements GeneralSituationContract.View{

    private TextView tv_text;
    private TextView tv_show;
    private ImageView iv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent=new GeneralSituationPresent(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_situation, container, false);
        init(view);
        tPresent.askInterNetForData();
        return view;
    }

    private void init(View view) {
        iv = view.findViewById(R.id.iv);
        tv_text = view.findViewById(R.id.tv_text);
        tv_show = view.findViewById(R.id.tv_show);

    }
    private void updateTextSet(){
        if(tv_text.getLineCount()>3){
            tv_text.setMaxLines(3);
            tv_text.setEllipsize(TextUtils.TruncateAt.END);
            tv_show.setOnClickListener(v -> {
                TextView tv=(TextView)v;
                int lineCount = tv_text.getLineCount();
                if(lineCount<=3){
                    tv_text.setMaxLines(20);
                    tv.setText("收起");
                }else{
                    tv_text.setMaxLines(3);
                    tv.setText("展开");
                }
            });
        }
    }
    @Override
    public void updateView(GeneralBean.DataBean data) {
        String ipImgUrl = data.getIpImgUrl();
        if(TextUtils.isEmpty(ipImgUrl)){
            iv.setImageResource(R.drawable.ic_menu_camera);
        }else{
            Glide.with(this).load(ipImgUrl).into(iv);
        }
        tv_text.setText(data.getSummary());
        updateTextSet();
    }

    @Override
    public String getUserId() {
        return User.getInstance().getUserId();
    }

    @Override
    public String getClassId() {
        return User.getInstance().getSchoolId();
    }

    @Override
    public void fail(String error) {
        toast(error);
    }


    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
