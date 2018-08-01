package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;

public class BottomBarTab extends LinearLayout {
    private ImageView mIcon;
    private Context mContext;
    private TextView mTextView;
    private int mTabPosition = -1;
    private int icon;
    private static boolean ifshow = false;

    public BottomBarTab(Context context, @DrawableRes int icon, String title) {
        this(context, null, icon,  title);
    }


    public BottomBarTab(Context context, AttributeSet attrs, int icon, String title) {
        this(context, attrs, 0, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, String title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, title);
    }

    private void init(Context context, int icon, String title) {
        mContext = context;
        this.icon =icon;

        setOrientation(LinearLayout.VERTICAL);
        mIcon = new ImageView(context);
        int px12 = getResources().getDimensionPixelSize(R.dimen.dp_12);
        //int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(px12*2, px12*2);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) TypedValue.applyDimension(1, 2.5f,getResources().getDisplayMetrics());
        mIcon.setImageResource(icon);
        mIcon.setLayoutParams(params);

        // mIcon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        LayoutParams textViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textViewParams.gravity = Gravity.CENTER_HORIZONTAL;
        // textViewParams.addRule(ALIGN_PARENT_BOTTOM);
        textViewParams.topMargin =  (int) TypedValue.applyDimension(1, 2.5f,getResources().getDisplayMetrics());
        textViewParams.bottomMargin = (int) TypedValue.applyDimension(1, 2.5f,getResources().getDisplayMetrics());
        mTextView = new TextView(context);
        mTextView.setText(title);
        //mTextView.setTextSize(ContextUtils.dip2px(context, 3.2f));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, px12);
        mTextView.setLayoutParams(textViewParams);
        mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        addView(mIcon);
        addView(mTextView);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.guide_start_btn));
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.guide_start_btn));

        } else {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_unselect));
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        }
    }


    public BottomBarTab setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
        return this;
    }

    public int getTabPosition() {
        return mTabPosition;
    }
}
