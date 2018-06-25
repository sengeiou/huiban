package com.bshuiban.teacher.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import com.bshuiban.baselibrary.model.LogUtils;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.utils.ZoominPagerTransFormer;
import com.bshuiban.baselibrary.view.activity.BaseActivity;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bshuiban.teacher.R;
import com.bshuiban.teacher.contract.PrepareLessonInfContract;
import com.bshuiban.teacher.model.PrepareLessonBean;
import com.bshuiban.teacher.present.PrepareLessonInfPresent;
import com.bshuiban.teacher.view.adapter.PrepareLessonInfAdapter;
import com.bshuiban.teacher.view.webView.webActivity.StudentAnswerInfWebActivity;
import com.bshuiban.teacher.view.webView.webActivity.MiddleReportInfWebActivity;

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
    private int wtype=1;
    private PrepareLessonBean[] lessonBeans;
    private ViewPager mVp;
    private LinearLayout mLlDots;
    private ImageView mIvRedDot;
    private ArrayList<View> mImageViewList;
    private int tagIntent=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_lesson_inf);
        Intent intent = getIntent();
        preId = intent.getStringExtra(PREID);
        LogUtils.e(LogUtils.TAG,"preId="+preId);
        init();
        tPresent=new PrepareLessonInfPresent(this);
        tPresent.loadPrepareLessonInf(tagIntent,preId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    private View getPagerView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_prepare_lesson_inf_item, mLlDots,false);
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
        recycleView.setFocusable(false);
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
        Intent intent = new Intent()
                .putExtra("classId", classId)
                .putExtra("preparationId", preId)
                .putExtra("process", wtype)
                .putExtra("workId", workId)
                .putExtra("className", className);;
        if(wtype!=2) {
            intent.setClass(getApplicationContext(), StudentAnswerInfWebActivity.class);
        }else{
            intent.setClass(getApplicationContext(),MiddleReportInfWebActivity.class);
        }
        startActivity(intent);
    };
    @Override
    public void updateLessonInf(PrepareLessonBean prepareLessonBean) {
        int i = tagIntent - 1;
        lessonBeans[i] = prepareLessonBean;
        setViewPagerData(mImageViewList.get(i),prepareLessonBean);
        if(i+1<lessonBeans.length&&null==lessonBeans[i+1]){
            tagIntent+=1;
            tPresent.loadPrepareLessonInf(tagIntent,preId);
        }else{
            mVp.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return mImageViewList.size();
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    View imageView = mImageViewList.get(position);
                    container.addView(imageView);
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
        }
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
        TitleView titleView = findViewById(R.id.titleView);
        titleView.setOnClickListener(v -> finish());
        mIvRedDot = findViewById(R.id.iv_guide_red_dot);
        mLlDots = findViewById(R.id.ll_guide_gray_dots);
        mImageViewList = new ArrayList<>();
        mVp.setPageMargin((int) getResources().getDimension(R.dimen.dp_15));
        wtype = 1;
        lessonBeans = new PrepareLessonBean[3];
        mImageViewList = new ArrayList<>(lessonBeans.length);
        for (int i = 0; i < 3; i++) {
            View pagerView = getPagerView();
            TextView tv_type = pagerView.findViewById(R.id.tv_type);
            tv_type.setText(getTextContent(i));
            if(i==1){
                tv_type.setBackground(ContextCompat.getDrawable(this,R.drawable.circle_red1_bg));
            }
            mImageViewList.add(pagerView);
        }
        mVp.setOffscreenPageLimit(3);


        mVp.setOnPageChangeListener(this);

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
        LogUtils.e(LogUtils.TAG,"onPageSelected: position="+position);
        wtype=position+1;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
