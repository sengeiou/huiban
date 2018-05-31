package com.bshuiban.teacher.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.utils.ZoominPagerTransFormer;
import com.bshuiban.baselibrary.view.activity.BaseActivity;
import com.bshuiban.teacher.R;
import com.bshuiban.teacher.contract.PrepareLessonInfContract;
import com.bshuiban.teacher.model.PrepareLessonBean;
import com.bshuiban.teacher.present.PrepareLessonInfPresent;
import com.bshuiban.teacher.view.adapter.PrepareLessonInfAdapter;
import com.bshuiban.teacher.view.webView.webActivity.StudentAnswerInfWebActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PrepareLessonInfActivity extends BaseActivity<PrepareLessonInfPresent> implements PrepareLessonInfContract.View, ViewPager.OnPageChangeListener {
    public static final String PREID = "preId";
    /**
     * 备课标识
     */
    private String preId;
    /**
     * 课前1，课中2，课后3
     */
    private int wtype;
    private PrepareLessonBean[] lessonBeans;
    private ViewPager mVp;
    private LinearLayout mLlDots;
    private ImageView mIvRedDot;
    private ArrayList<View> mImageViewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_lesson_inf);
        Intent intent = getIntent();
        preId = intent.getStringExtra(PREID);
        init();
    }

    private View getPagerView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_prepare_lesson_inf_item, null);
        return view;
    }

    private String getTextContent(int tag) {
        switch (tag) {
            case 0:
                return "课前预习";
            case 1:
                return "课中报告";
            default:
                return "课后作业";
        }
    }

    private void setViewPagerData(View view, PrepareLessonBean prepareLessonBean) {
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_text = view.findViewById(R.id.tv_text);
        PrepareLessonBean.DataBean data = prepareLessonBean.getData();
        tv_title.setText(TextUtils.cleanNull(data.getTitle()));
        tv_text.setText(TextUtils.cleanNull(data.getInfo()));

        RecyclerView recycleView = view.findViewById(R.id.recycleView);
        PrepareLessonInfAdapter adapterList=new PrepareLessonInfAdapter();
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapterList.setOnItemClickListener(listener);
        adapterList.setList(data.getClassArr());
        recycleView.setAdapter(adapterList);
    }
    private PrepareLessonInfAdapter.OnItemClickListener listener = position -> {
        List<PrepareLessonBean.DataBean.ClassArrBean> classArr = lessonBeans[wtype - 1].getData().getClassArr();
        PrepareLessonBean.DataBean.ClassArrBean classArrBean = classArr.get(position);
        int workId = classArrBean.getWorkId();
        int classId = classArrBean.getClassId();
        String className = classArrBean.getClassName();
        Intent intent = new Intent(getApplicationContext(), StudentAnswerInfWebActivity.class)
                .putExtra("classId", classId)
                .putExtra("preparationId", preId)
                .putExtra("process", wtype)
                .putExtra("workId", workId)
                .putExtra("className",className);
        startActivity(intent);
    };
    @Override
    public void updateLessonInf(PrepareLessonBean prepareLessonBean) {
        int i = wtype - 1;
        lessonBeans[i] = prepareLessonBean;
        setViewPagerData(mImageViewList.get(i),prepareLessonBean);
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        toast(error);
    }

    private void init() {
        mVp = findViewById(R.id.vp_student_homework);
        mIvRedDot = findViewById(R.id.iv_guide_red_dot);
        mLlDots = findViewById(R.id.ll_guide_gray_dots);
        mImageViewList = new ArrayList<>();
        mVp.setPageMargin(0);
        mVp.setOffscreenPageLimit(3);
        wtype = 1;
        lessonBeans = new PrepareLessonBean[3];
        mImageViewList = new ArrayList<>(lessonBeans.length);
        for (int i = 0; i < lessonBeans.length; i++) {
            View pagerView = getPagerView();
            TextView tv_type = pagerView.findViewById(R.id.tv_type);
            tv_type.setText(getTextContent(i));
            mImageViewList.add(pagerView);
        }

        mVp.setOnPageChangeListener(this);
        mVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViewList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View imageView = mImageViewList.get(position);
                return imageView;
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        ZoominPagerTransFormer mPageTransformer = new ZoominPagerTransFormer();
        mVp.setPageTransformer(true, mPageTransformer);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 小红点移动的距离是多少
        int distance = mLlDots.getChildAt(1).getLeft()
                - mLlDots.getChildAt(0).getLeft();
        // 怎样让小红点移动, 修改小红点的marginLeft
        FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) mIvRedDot
                .getLayoutParams();
        layoutParams.leftMargin = (int) (distance * positionOffset) + position * distance;
        // 必须设置LayoutParams, 才能生效
        mIvRedDot.setLayoutParams(layoutParams);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
