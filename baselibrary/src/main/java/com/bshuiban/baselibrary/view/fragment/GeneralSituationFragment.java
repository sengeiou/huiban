package com.bshuiban.baselibrary.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bshuiban.baselibrary.view.adapter.GeneralSituationAdapter;
import com.bshuiban.baselibrary.view.customer.LineTextView;
import com.bshuiban.baselibrary.view.webview.webActivity.LiuYanMsgListActivity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：概况
 */
public class GeneralSituationFragment extends BaseFragment<GeneralSituationPresent> implements GeneralSituationContract.View{

    private TextView tv_text,tv_show;
    private LineTextView tv_teacher,tv_student;
    private ImageView iv;
    private RecyclerView recycle;
    private boolean isTeacher=true;
    private int position;

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
        classId=User.getInstance().getClassId();
        tPresent.askInterNetForData();
        return view;
    }
    private int color;
    private int color1;
    private String classId;
    private void init(View view) {
        iv = view.findViewById(R.id.iv);
        tv_text = view.findViewById(R.id.tv_text);
        tv_show = view.findViewById(R.id.tv_show);
        tv_teacher = view.findViewById(R.id.tv_teacher);
        tv_student = view.findViewById(R.id.tv_student);
        recycle = view.findViewById(R.id.recycle);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        teaInfoBeanGeneralSituationAdapter.setContext(getActivity());
        stuInfoBeanGeneralSituationAdapter.setContext(getActivity());
        recycle.setAdapter(teaInfoBeanGeneralSituationAdapter);
        color=getResources().getColor(R.color.guide_start_btn);
        color1=getResources().getColor(R.color.tab_unselect);
        tv_teacher.setOnClickListener(onClickListener);
        tv_student.setOnClickListener(onClickListener);
        onClickListener.onClick(tv_teacher);
    }
    private View.OnClickListener onClickListener= this::onClick;

    private void updateTextSet(){
        if(tv_text.getLineCount()>3){
            tv_text.setMaxLines(3);
            tv_text.setEllipsize(TextUtils.TruncateAt.END);
            tv_show.setOnClickListener(onClickListener);
        }else{
            tv_show.setVisibility(View.GONE);
        }
    }
    @Override
    public void updateView(GeneralBean.DataBean data) {
        String ipImgUrl = data.getIpImgUrl();
        if(TextUtils.isEmpty(ipImgUrl)){
            iv.setImageResource(R.mipmap.default_class);
        }else{
            Glide.with(getActivity()).load(ipImgUrl).into(iv);
        }
        tv_text.setText(data.getSummary());
        updateTextSet();
        List<GeneralBean.DataBean.TeaInfoBean> teaInfo = data.getTeaInfo();
        List<GeneralBean.DataBean.StuInfoBean> stuInfo = data.getStuInfo();
        teaInfoBeanGeneralSituationAdapter.setList(teaInfo);
        stuInfoBeanGeneralSituationAdapter.setList(stuInfo);
    }

    @Override
    public String getUserId() {
        return User.getInstance().getUserId();
    }

    @Override
    public String getClassId() {
        return classId;
    }

    @Override
    public void guanZhuResult(boolean tag) {
        if(isTeacher){
            teaInfoBean.setIsAtten(tag?1:0);
            teaInfoBeanGeneralSituationAdapter.notifyItemChanged(this.position);
        }else {
            stuInfoBean.setIsAtten(tag?1:0);
            stuInfoBeanGeneralSituationAdapter.notifyItemChanged(this.position);
        }
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

    private GeneralBean.DataBean.TeaInfoBean teaInfoBean;
    private GeneralSituationAdapter<GeneralBean.DataBean.TeaInfoBean> teaInfoBeanGeneralSituationAdapter=new GeneralSituationAdapter<GeneralBean.DataBean.TeaInfoBean>() {
        @Override
        protected String getImagePath(int position) {
            return mList.get(position).getImgUrl();
        }

        @Override
        protected String getName(int position) {
            return mList.get(position).getName();
        }

        @Override
        protected String getSubjectName(int position) {
            return com.bshuiban.baselibrary.utils.TextUtils.cleanNull(mList.get(position).getSubjectName())+"老师";
        }

        @Override
        protected boolean getGuanzhu(int position) {
            return mList.get(position).getIsAtten()==1;//是否关注(0否,1是)
        }

        @Override
        protected void toNextPageGuanZhu(int position) {
            GeneralSituationFragment.this.position=position;
            teaInfoBean = mList.get(position);
            int teacherId = teaInfoBean.getTeacherId();
            tPresent.guanZhu(teaInfoBean.getIsAtten()==0,teacherId);
        }

        @Override
        protected void toNextPageLiuYan(int position) {
            GeneralBean.DataBean.TeaInfoBean teaInfoBean = mList.get(position);
            String name = teaInfoBean.getName();
            int teacherId = teaInfoBean.getTeacherId();
            startActivity(new Intent(getActivity(), LiuYanMsgListActivity.class)
                    .putExtra("name",name)
                    .putExtra("userId",teacherId+""));
        }
    };
    private GeneralBean.DataBean.StuInfoBean stuInfoBean;
    private GeneralSituationAdapter<GeneralBean.DataBean.StuInfoBean> stuInfoBeanGeneralSituationAdapter=new GeneralSituationAdapter<GeneralBean.DataBean.StuInfoBean>() {
        @Override
        protected String getImagePath(int position) {
            return mList.get(position).getImgUrl();
        }

        @Override
        protected String getName(int position) {
            return mList.get(position).getName();
        }

        @Override
        protected String getSubjectName(int position) {
            return com.bshuiban.baselibrary.utils.TextUtils.cleanNull(mList.get(position).getStuSubStrAll());
        }

        @Override
        protected boolean getGuanzhu(int position) {
            return mList.get(position).getIsAtten()==1;//是否关注(0否,1是)
        }

        @Override
        protected void toNextPageGuanZhu(int position) {
            GeneralSituationFragment.this.position=position;
            stuInfoBean = mList.get(position);
            int studentId = stuInfoBean.getStudentId();
            tPresent.guanZhu(stuInfoBean.getIsAtten()==0,studentId);
        }

        @Override
        protected void toNextPageLiuYan(int position) {
            GeneralBean.DataBean.StuInfoBean stuInfoBean = mList.get(position);
            String name = stuInfoBean.getName();
            int studentId = stuInfoBean.getStudentId();
            startActivity(new Intent(getActivity(), LiuYanMsgListActivity.class)
            .putExtra("name",name)
            .putExtra("userId",studentId+""));
        }

    };

    private void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_teacher) {
            tv_teacher.setSelectColor(Color.BLACK, color);
            tv_student.setSelectColor(color1, -1);
            recycle.setAdapter(teaInfoBeanGeneralSituationAdapter);
            isTeacher=true;
        } else if (i == R.id.tv_student) {
            tv_student.setSelectColor(Color.BLACK, color);
            tv_teacher.setSelectColor(color1, -1);
            recycle.setAdapter(stuInfoBeanGeneralSituationAdapter);
            isTeacher=false;
        } else if (i == R.id.tv_show) {
            int lineCount = tv_text.getLineCount();
            if (lineCount <= 3) {
                tv_text.setMaxLines(20);
                tv_show.setText("收起");
            } else {
                tv_text.setMaxLines(3);
                tv_show.setText("展开");
            }
        }
    }

    public void update(String classId) {
        this.classId=classId;
        tPresent.askInterNetForData();
    }
}
