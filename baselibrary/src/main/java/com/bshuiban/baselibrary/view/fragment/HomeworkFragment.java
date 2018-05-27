package com.bshuiban.baselibrary.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.ZoominPagerTransFormer;
import com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity;
import com.bshuiban.baselibrary.view.webview.webFragment.InteractionBaseWebViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeworkFragment extends InteractionBaseWebViewFragment implements ViewPager.OnPageChangeListener{
    private ViewPager mVp;
    private List<ImageView> mImageViewList;
    private int[] mDrawable;
    private ImageView mIvRedDot;
    private LinearLayout mLlDots;

    public HomeworkFragment() {
        // Required empty public constructor
    }


    @Override
    public void update(Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homework, container, false);
        init(view);
        return view;

    }

    private void init(View view) {
        mVp = view.findViewById(R.id.vp_student_homework);
        mIvRedDot = view.findViewById(R.id.iv_guide_red_dot);
        mLlDots = view.findViewById(R.id.ll_guide_gray_dots);
        mImageViewList = new ArrayList<>();
        mVp.setPageMargin(0);
        mVp.setOffscreenPageLimit(3);
        mDrawable = new int[]{R.mipmap.iv_student_homework_pre,R.mipmap.iv_classroom_records,R.mipmap.iv_student_homework};

        for (int i = 0; i < mDrawable.length; i++) {
            ImageView imageView = new ImageView(getActivity().getApplicationContext());
            imageView.setImageResource(mDrawable[i]);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(80, 80));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViewList.add(imageView);
        }

        mVp.setOnPageChangeListener(this);
        mVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViewList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                ImageView imageView = mImageViewList.get(position);
                // ImageLoaderUtil.LoadImage(getActivity(), mDrawable[position],imageView);
                container.addView(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), HomeworkListWebActivity.class);
                        intent.putExtra(HomeworkListWebActivity.HOME_TYPE,position+1);
                        startActivity(intent);
                    }
                });
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
}
