package com.bshuiban.baselibrary.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author：weidanyan
 * Email：1022664273@qq.com
 * Description：This is SpaceItemDecorationUtils
 * Time: 2018/4/12
 */
public class SpaceItemDecorationUtils extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecorationUtils(int space){
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        //不是第一个的格子都设一个左边和底部的间距
        outRect.bottom=space;
    }


}
