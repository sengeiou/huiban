package com.bshuiban.baselibrary.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Author：weidanyan
 * Email：1022664273@qq.com
 * Description：This is ZoominPagerTransFormer
 * Time: 2018/4/19
 */
public class ZoominPagerTransFormer implements ViewPager.PageTransformer {
    View page ;
    @Override
    public void transformPage(View page, float position) {
        this.page = page;//0.08
        if (position < -1) { /* [-Infinity,-1)*/
            /*页面已经在屏幕左侧且不可视*/
            page.setScaleX((float) (1 + position * 0.06));
            page.setScaleY((float) (1 + position * 0.06));
        } else if (position <= 0) { /* [-1,0]*/
            /*页面从左侧进入或者向左侧滑出的状态*/
            page.setScaleX((float) (1 + position * 0.06));
            page.setScaleY((float) (1 + position * 0.06));
        } else if (position <= 1) {/* (0,1]*/
            /*页面从右侧进入或者向右侧滑出的状态*/
            page.setScaleX((float) (1-  position * 0.06));
            page.setScaleY((float) (1 - position * 0.06));
        } else if (position > 1) {
            /*页面已经在屏幕右侧且不可视*/
            page.setScaleX((float) (1-  position * 0.06));
            page.setScaleY((float) (1 - position * 0.06));
        }
        page.setRotation(6*position);
    }

}

