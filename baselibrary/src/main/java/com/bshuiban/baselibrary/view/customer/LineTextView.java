package com.bshuiban.baselibrary.view.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;

/**
 * Created by xinheng on 2018/5/17.<br/>
 * describe：线条的TextView
 */
@SuppressLint("AppCompatCustomView")
public class LineTextView extends TextView {
    private int lineColor=getResources().getColor(R.color.guide_start_btn);
    private int lineWidth=5;
    private int lineType=0;
    private Paint mPaint;
    private static final int Line_Left=0;
    private static final int Line_Bottom=1;
    public LineTextView(Context context) {
        this(context,null);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineTextView, defStyleAttr, 0);
        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.LineTextView_lineColor) {
                lineColor = typedArray.getColor(index, lineColor);

            } else if (index == R.styleable.LineTextView_lineWidth) {
                lineWidth = typedArray.getDimensionPixelSize(index, lineWidth);

            } else if (index == R.styleable.LineTextView_lineType) {
                lineType = typedArray.getInt(index, lineType);
            } else {
            }
        }
        typedArray.recycle();
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight()+lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(lineColor>0) {
            mPaint.setColor(lineColor);
            if (lineType == Line_Bottom) {
                canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), mPaint);
            } else {
                canvas.drawLine(0, 0, 0, getMeasuredHeight(), mPaint);
            }
        }
    }
    public void setSelectColor(int selectTextColor,int lineColor){
        this.lineColor=lineColor;
        setTextColor(selectTextColor);
    }
}
