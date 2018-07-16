package com.bshuiban.baselibrary.view.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.TextUtils;

/**
 * Created by xinheng on 2018/7/5.<br/>
 * describe：圆形 倾斜45度，渐进色 进度条
 */
public class CircleColorProgressView extends View {
    /**
     * 环的宽度
     */
    private float circleRingWidth;
    private int startColor = Color.BLUE;
    private int endColor = Color.RED;
    private int defaultColor = Color.YELLOW;
    private int textColor = Color.BLACK;
    private int textSize = getResources().getDimensionPixelSize(R.dimen.dp_30);
    private int textSizeNext = getResources().getDimensionPixelSize(R.dimen.dp_15);
    /**
     * 两行文本的间隔
     */
    private int paddingText = getResources().getDimensionPixelSize(R.dimen.dp_15);
    private Paint mPaintText;
    private Paint mPaintTextNext;
    private Paint mPaint,mStartPaint,mEndPaint,mPaintDefault;
    private float progress=0.8f;//百分比 < 1
    public CircleColorProgressView(Context context) {
        this(context, null);
    }

    public CircleColorProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleColorProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleColorProgressView, defStyleAttr, 0);
        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.CircleColorProgressView_default_color) {
                defaultColor = typedArray.getColor(index, defaultColor);

            } else if (index == R.styleable.CircleColorProgressView_start_color) {
                startColor = typedArray.getColor(index, startColor);

            } else if (index == R.styleable.CircleColorProgressView_end_color) {
                endColor = typedArray.getColor(index, endColor);

            } else if (index == R.styleable.CircleColorProgressView_text_next_size) {
                textSizeNext = typedArray.getDimensionPixelSize(index, textSizeNext);

            } else if (index == R.styleable.CircleColorProgressView_text_num_size) {
                textSize = typedArray.getDimensionPixelSize(index, textSize);
            }
        }
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaintText = new Paint(mPaint);
        mPaintDefault = new Paint(mPaint);
        mPaintTextNext = new Paint(mPaint);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintTextNext.setStyle(Paint.Style.FILL);
        mStartPaint=new Paint(mPaint);
        mEndPaint=new Paint(mPaint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int circleD = Math.min(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());//圆直径
        float circleR = circleD / 2f;//半径
        if (circleRingWidth == 0) {
            circleRingWidth = circleD / 10f;
        }
        Log.e("TAG", "onDraw: " + circleRingWidth);
        //mPaint.setStyle(Paint.Style.STROKE);
        mPaintDefault.setStrokeWidth(circleRingWidth);
        mPaintDefault.setColor(defaultColor);
        //圆的位置
        RectF rectF = new RectF();
        float width_2 = getMeasuredWidth() / 2f;
        float height_2 = getMeasuredHeight() / 2f;
        rectF.left = width_2 - circleR;
        rectF.right = width_2 + circleR;
        rectF.top = height_2 - circleR;
        rectF.bottom = height_2 + circleR;
        float v = circleRingWidth / 2f;
        RectF rectFCircle = new RectF(rectF.left + v, rectF.top + v, rectF.right - v, rectF.bottom - v);
        canvas.drawArc(rectFCircle, 0, 360, false, mPaintDefault);
        mPaintText.setTextSize(textSize);
        mPaintText.setColor(textColor);
        int textHeight1 = TextUtils.getTextHeightR("70%", mPaintText);
        mPaintTextNext.setTextSize(textSizeNext);
        mPaintTextNext.setColor(textColor);
        int textHeight2 = TextUtils.getTextHeightR("我的掌握度", mPaintTextNext);
        int heightText = textHeight1 + textHeight2 + paddingText;
        float startY = height_2 - heightText / 2f;
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintTextNext.setTextAlign(Paint.Align.CENTER);
//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(rectF,paint);
        float textBaseLine = TextUtils.getTextBaseLine(startY + textHeight1 / 2f, mPaintText);
        String s=progress>-1?((progress)+"%"):"- -";
        canvas.drawText(s, width_2, textBaseLine, mPaintText);
        textBaseLine = TextUtils.getTextBaseLine(startY + textHeight1 + paddingText + textHeight2 / 2f, mPaintTextNext);
        canvas.drawText("我的掌握度", width_2, textBaseLine, mPaintTextNext);
        if(progress<=0){
            return;
        }
        float circleD1 = rectF.height();
        float ringLength = (float) ((circleD1 - circleD1 * Math.sin(Math.toRadians(45))) / 2f);
        @SuppressLint("DrawAllocation")//圆内切矩形
        LinearGradient linearGradient = new LinearGradient(rectFCircle.right-ringLength, rectFCircle.top+ringLength, rectFCircle.left+ringLength, rectFCircle.bottom-ringLength, startColor, endColor, Shader.TileMode.CLAMP);
        //paint.setShader(linearGradient);
        mPaint.setShader(linearGradient);

        final int ANGLE = 180;
        final float ANGLE_2 = ANGLE/2f;
        float progressAngle = progress/100f * 360;
        if(progressAngle>360){
            progressAngle=360;
        }
        mPaint.setStrokeWidth(circleRingWidth);
        if(progressAngle>ANGLE){//超出
            mStartPaint.setColor(startColor);
            mStartPaint.setStrokeWidth(circleRingWidth);
            mStartPaint.setStrokeCap(Paint.Cap.ROUND);
            mEndPaint.setColor(endColor);
            mEndPaint.setStrokeWidth(circleRingWidth);
            mEndPaint.setStrokeCap(Paint.Cap.ROUND);
            float startAngle = 45 - ANGLE_2;
            float topAngle = progressAngle / 2f - ANGLE_2;
            Log.e("TAG", "onDraw: "+startAngle+", "+ANGLE );
            canvas.drawArc(rectFCircle,startAngle,-topAngle,false,mStartPaint);
            Log.e("TAG", "onDraw: "+(startAngle+ANGLE) +", "+startAngle);
            canvas.drawArc(rectFCircle,startAngle+ANGLE,topAngle,false,mEndPaint);
            canvas.drawArc(rectFCircle, startAngle, ANGLE, false, mPaint);
        }else {
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawArc(rectFCircle, 45-progressAngle/2f, progressAngle, false, mPaint);
        }
    }
}
