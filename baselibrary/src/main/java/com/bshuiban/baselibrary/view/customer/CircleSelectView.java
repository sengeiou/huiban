package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bshuiban.baselibrary.utils.DensityUtil;
import com.bshuiban.baselibrary.utils.TextUtils;

import java.io.File;

/**
 * Created by xinheng on 2018/7/11.<br/>
 * describe：
 */
public class CircleSelectView extends View {
    private final float dp1;
    /**
     * 圆形半径
     */
    private int circleRadius=10;
    private int defaultColor=Color.GRAY;
    private int selectColor= Color.BLUE;
    private int textSize=20;
    private Paint mPaintText,mPaintCircle;
    private boolean isSelect;
    private String text;
    /**
     * 圆与字的间距
     */
    private int circlePaddingText;
    public CircleSelectView(Context context) {
        this(context,null);
    }

    public CircleSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintText =new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setDither(true);
        mPaintCircle=new Paint(mPaintText);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        dp1 = DensityUtil.dip2px(context, 1);
        mPaintCircle.setStrokeWidth(dp1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        mPaintText.setTextSize(textSize);
        int textHeight=0;
        if(!android.text.TextUtils.isEmpty(text)) {
            textHeight = TextUtils.getTextHeightR("班", mPaintText);
        }
        int height=getPaddingTop()+getPaddingBottom()+Math.max(textHeight,circleRadius*2);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //空心圆
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setColor(defaultColor);
        float cy = getMeasuredHeight() / 2f;
        int cx = getPaddingLeft() + circleRadius;
        canvas.drawCircle(cx, cy,circleRadius,mPaintCircle);
        //实心圆
        if(isSelect){
            mPaintCircle.setColor(selectColor);
            mPaintCircle.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx, cy,circleRadius-dp1,mPaintCircle);
        }
        //文字
        if(!android.text.TextUtils.isEmpty(text)){
            mPaintText.setTextSize(textSize);
            mPaintText.setColor(defaultColor);
            float textBaseLine = TextUtils.getTextBaseLine(cy, mPaintText);
            canvas.drawText(text,cx+circleRadius+circlePaddingText,textBaseLine,mPaintText);
        }
    }
    public void setParmar(int circleRadiusDP,int circlePaddingTextDP,int defaultColor,int selectColor,String text,int textSizeDP){
        this.circleRadius=circleRadiusDP;
        this.circlePaddingText=circlePaddingTextDP;
        this.textSize=textSizeDP;
        this.defaultColor= defaultColor;
        this.selectColor= selectColor;
        this.text= text;
    }

    public void toggle() {
        isSelect = !isSelect;
        invalidate();
    }

    public boolean isSelect() {
        return isSelect;
    }
}
