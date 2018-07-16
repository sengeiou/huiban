package com.bshuiban.baselibrary.view.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bshuiban.baselibrary.R;

/**
 * Created by xinheng on 2018/7/4.<br/>
 * describe：渐进色 进度条
 */
public class LineView extends View {
    private int default_color=Color.GRAY;
    private int colors[]=new int[2];
    private Paint mPaint;
    private float progress;
    public LineView(Context context) {
        this(context,null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineView, defStyleAttr, 0);
        int N = typedArray.getIndexCount();
        int start_color=Color.RED;
        int end_color=Color.YELLOW;
        for (int i = 0; i < N; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.LineView_default_color) {
                default_color = typedArray.getColor(index, default_color);
            } else if (index == R.styleable.LineView_start_color) {
                start_color = typedArray.getColor(index,start_color );
            } else if (index == R.styleable.LineView_end_color) {
                end_color = typedArray.getColor(index, end_color);
            }
        }
        typedArray.recycle();
        colors[0]=start_color;
        colors[1]=end_color;
        mPaint=new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
        int height=getMeasuredHeight()-getPaddingTop()-getPaddingBottom();
        float startY = getMeasuredHeight() / 2f;
        mPaint.setStrokeWidth(height);
        mPaint.setShader(null);
        mPaint.setColor(default_color);
        canvas.drawLine(getPaddingLeft(),startY, getMeasuredWidth()-getPaddingRight(),startY,mPaint);
        @SuppressLint("DrawAllocation") LinearGradient shader = new LinearGradient(getPaddingLeft(), getPaddingTop(), progress*width+getPaddingLeft(), getPaddingTop(), colors, null, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        canvas.drawLine(getPaddingLeft(), startY, progress*width+getPaddingLeft(), startY,mPaint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
