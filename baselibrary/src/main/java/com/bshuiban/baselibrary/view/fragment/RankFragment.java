package com.bshuiban.baselibrary.view.fragment;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.StuLearnReportBean;
import com.bshuiban.baselibrary.view.customer.RectRankingInf;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam4;
    private String mParam2,mParam3,mParam5,mParam6;
    //private int ranColor;
    private float leftSize;
    private float rightSize;
    private RectRankingInf rectRankingInf;
    private TextView tv_subject_name;
    private TextView tv_rate;
    private TextView tv_class_ranking;
    private TextView tv_gradle_ranking;
    private TextView tv_class_progress;
    private View view;


    public RankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankFragment newInstance(String param1, String param2,String param3,int param4,String param5,String param6) {
        RankFragment fragment = new RankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putInt(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getInt(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rank, container, false);
//        rectRankingInf = view.findViewById(R.id.rectRank);
//        //rectRankingInf.setBackgroundLeftColor(ranColor);
//        leftSize=getResources().getDimension(R.dimen.dp_15);
//        rightSize=getResources().getDimension(R.dimen.dp_10);
//        rectRankingInf.setLeft_first_size(leftSize);
//        rectRankingInf.setLeft_second_size(leftSize);
//        rectRankingInf.setRight_size(rightSize);
//        rectRankingInf.setAverage(true);
//        setRectRank();
        tv_subject_name = view.findViewById(R.id.tv_subject_name);
        tv_rate = view.findViewById(R.id.tv_rate);

        tv_class_ranking = view.findViewById(R.id.tv_class_ranking);
        tv_gradle_ranking = view.findViewById(R.id.tv_gradle_ranking);
        tv_class_progress = view.findViewById(R.id.tv_class_progress);
        setRectRank();
        return view;
    }
    private void setRectRank(){
//        StuLearnReportBean.DataBean.RankBean rankBean;
//        rectRankingInf.setSubject_name(rankBean.getSubjectName())
//                .setSubject_name_rata(rankBean.getRate())
//                .setClass_ranking(rankBean.getCrank())
//                .setClass_progress(rankBean.getProgress() + "")
//                .setGrade_ranking(rankBean.getGrank());
     //   rectRankingInf.setBackgroundLeftColor(Color.parseColor(rankBean.getColor().replace(";", "")));
//        rectRankingInf.setSubject_name(mParam1)
//                .setSubject_name_rata(mParam2)
//                .setClass_ranking(mParam3)
//                .setClass_progress(mParam4 + "")
//                .setGrade_ranking(mParam5);
//        rectRankingInf.setBackgroundLeftColor(Color.parseColor(mParam6.replace(";", "")));
        GradientDrawable drawable=new GradientDrawable();
        drawable.setColor(Color.parseColor(mParam6.replace(";", "")));
        drawable.setCornerRadius(getResources().getDimension(R.dimen.dp_5));
        view.setBackground(drawable);
        tv_subject_name.setText(mParam1);
        String s ;
        if("-1".equals(mParam2)){
            s="- -";
        }else {
            s=mParam2+"%";
        }
        tv_rate.setText(s);
        tv_class_ranking.setText("班级排名："+mParam3);
        tv_class_progress.setText("班级进步："+mParam4);
        tv_gradle_ranking.setText("年级排名："+mParam5);
    }
}
