package com.bshuiban.baselibrary.view.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.DensityUtil;
import com.bshuiban.baselibrary.utils.TextUtils;

import java.io.File;

/**
 * Created by xinheng on 2018/7/5.<br/>
 * describe：含有选中状态的view，类似多个button，一个选中
 */
public class SelectStatueView extends View {
    /**
     * 选中的背景颜色
     */
    private int selectedBgColor = Color.RED;
    private int unSelectedBgColor = Color.GRAY;
    /**
     * 边框颜色
     */
    private int strokeColor = Color.GRAY;
    /**
     * 边角弧度
     */
    private int radius = 10;
    /**
     * 字体大小
     */
    private int textSize = 20;
    /**
     * 选中的字体颜色
     */
    private int textSelectedColor = Color.WHITE;
    private int textUnSelectedColor = Color.BLACK;
    /**
     * 字体距边框的级距离
     */
    private int paddingLength = 20;
    private String[] texts;
    private Paint mPaint;
    private int dp1;
    private int[] textX;
    private int selectIndex;

    public SelectStatueView(Context context) {
        this(context, null);
    }

    public SelectStatueView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectStatueView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectStatueView, defStyleAttr, 0);
        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.SelectStatueView_title_size) {
                textSize = typedArray.getDimensionPixelSize(index, textSize);
            } else if (index == R.styleable.SelectStatueView_padding_length) {
                paddingLength = typedArray.getDimensionPixelSize(index, paddingLength);
            } else if (index == R.styleable.SelectStatueView_select_bg_color) {
                selectedBgColor = typedArray.getColor(index, selectedBgColor);
            } else if (index == R.styleable.SelectStatueView_unselect_bg_color) {
                unSelectedBgColor = typedArray.getColor(index, unSelectedBgColor);
            } else if (index == R.styleable.SelectStatueView_select_text_color) {
                textSelectedColor = typedArray.getColor(index, textSelectedColor);
            } else if (index == R.styleable.SelectStatueView_unselect_text_color) {
                textUnSelectedColor = typedArray.getColor(index, textUnSelectedColor);
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        dp1 = (int) DensityUtil.dip2px(context, 1);
    }

    public void setTexts(String... texts) {
        this.texts = texts;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (texts == null || texts.length == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        //TODO 自适应，其他的没有考虑，复用时注意
        int length = texts.length;
        textX = new int[length];
        //int width = paddingLength * length * 2 + (length + 1) * dp1;
        int width = paddingLength + dp1;
        mPaint.setTextSize(textSize);
        for (int i = 0; i < length; i++) {
            float textWidth = TextUtils.getTextWidth(texts[i], mPaint) + paddingLength;
            width += textWidth;
            textX[i] = width;
            width += dp1;
        }
        int height = TextUtils.getTextHeightR(texts[0], mPaint) + getPaddingTop() + getPaddingBottom() + dp1 + dp1;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(unSelectedBgColor);
        mPaint.setStyle(Paint.Style.FILL);
        @SuppressLint("DrawAllocation") RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(rectF,radius,radius,mPaint);//填充
        mPaint.setStrokeWidth(dp1);
        float dp1_2 = dp1 / 2f;
        rectF.top=dp1_2;
        rectF.left=dp1_2;
        rectF.right=getMeasuredWidth()-dp1_2;
        rectF.bottom=getMeasuredHeight()-dp1_2;
        mPaint.setColor(strokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectF,radius,radius,mPaint);//边框
        if(texts==null||texts.length==0){
            return;
        }
        mPaint.setStyle(Paint.Style.FILL);
        float textBaseLine = TextUtils.getTextBaseLine((getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / 2f + getPaddingTop(), mPaint);
        mPaint.setTextAlign(Paint.Align.CENTER);
        float startX,startX1;
        for (int i = 0; i < textSize; i++) {
            //startX=i==0?paddingLength:textX
            if(i==0){
                startX=(textX[i])/2f;
            }else {
                startX=(textX[i-1]+textX[i])/2f;
                startX1 = textX[i - 1] + dp1_2;
                mPaint.setColor(strokeColor);
                canvas.drawLine(startX1,getPaddingTop(),startX1,getMeasuredHeight()-getPaddingBottom(),mPaint);
            }

            if(selectIndex==i) {
                mPaint.setColor(textSelectedColor);
            }else {
                mPaint.setColor(textUnSelectedColor);
            }

            canvas.drawText(texts[i],startX,textBaseLine,mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            int index = getIndex(x, textX);
            if(index>-1&&null!=onItemClickListener){
                onItemClickListener.itenClick(index,texts[index]);
                this.selectIndex=index;
                invalidate();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
    private int getIndex(float x,int []array){
        int i = array.length / 2;
        if(x==array[i]){
            return i;
        }else if(x>array[i]){//i 右侧
            return getIndex(x,i+1,array.length,array);
        }else{//左侧
            return getIndexLeft(x,0,i-1,array);
        }
    }
    private int getIndex(float x,int start,int end,int []array){
        for (int j = start; j < end; j++) {
            if(array[j]>x){
                return j;
            }
        }
        return -1;
    }
    private int getIndexLeft(float x,int start,int end,int []array){
        for (int j = end; j >=start; j++) {
            if(array[j]<x){
                return j+1;
            }
        }
        return -1;
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void itenClick(int index, String text);
    }
}
