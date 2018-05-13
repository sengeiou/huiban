package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.bshuiban.baselibrary.utils.TextUtils;

/**
 * Created by xinheng on 2018/5/5.<br/>
 * describe：每道试题的结果
 */
public class ExamCircleResult extends View {
    /**答案正确*/
    public static final byte RIGHT_STATE=0;
    /**答案错误*/
    public static final byte WRONG_STATE=1;
    /**未作答*/
    public static final byte NO_WORK_STATE=2;
    /**半对*/
    public static final byte HALF_RIGHT_STATE=2;

    private Paint mPaint;
    /**正确颜色*/
    private int rightColor= Color.BLUE;
    private int rightHalfColor= Color.YELLOW;
    /**错误颜色*/
    private int wrongColor=Color.RED;
    /**未答题颜色*/
    private int noWorkBgColor=Color.LTGRAY;
    /**默认字体颜色*/
    private int textColor=Color.BLACK;
    private int textSize=20;
    private String text="1";
    private float/* xCircle,yCircle,*/ radius;
    private float dp1;
    /**缩放比例*/
    private float ratio;

    //private int
    public ExamCircleResult(Context context) {
        this(context,null);
    }

    public ExamCircleResult(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExamCircleResult(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size,size);
        ratio = size / 74f;
        radius = ratio *31;
        dp1= TypedValue.applyDimension(1,1,getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //背景圆 75*75
        mPaint.setColor(rightColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp1);
        canvas.drawCircle(radius,radius,radius-dp1,mPaint);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(textSize*ratio);
        TextUtils.drawTextCenter(text,radius,radius,mPaint,canvas);
        drawRight(canvas);
        drawWrong(canvas);
        drawHalfRight(canvas);
    }
    private void drawRight(Canvas canvas){
        mPaint.setColor(rightColor);
        float strokeWidth = dp1 * 6;
        mPaint.setStrokeWidth(strokeWidth);
        //(36,57),(46,71),(74,46) 74*74
        float firstPontX=ratio*36;
        float firstPontY=ratio*57;
        float secondPointX = ratio*46;
        float secondPointY=ratio*71-strokeWidth;
        float thirdPointX = ratio*74-strokeWidth;
        float thirdPointY=secondPointX;
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(firstPontX,firstPontY,secondPointX,secondPointY,mPaint);
        canvas.drawLine(secondPointX,secondPointY,thirdPointX,thirdPointY,mPaint);
    }
    private void drawHalfRight(Canvas canvas){
        float strokeWidth = dp1 * 6;
        mPaint.setColor(rightHalfColor);
        mPaint.setStrokeWidth(strokeWidth);
        //(36,57),(46,71),(74,46) 74*74
        float firstPointX=ratio*27;
        float firstPointY=ratio*56;
        float secondPointX = ratio*40;
        float secondPointY=ratio*68;
        float thirdPointX = secondPointY;
        float thirdPointY=secondPointX;
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(firstPointX,firstPointY,secondPointX,secondPointY,mPaint);
        canvas.drawLine(secondPointX,secondPointY,thirdPointX,thirdPointY,mPaint);
//        float forthPointX=(firstPointX+thirdPointX)/2;
//        float forthPointY=(firstPointY+thirdPointY)/2;
//        float fifthPointX=secondPointX+thirdPointX-forthPointX;
//        float fifthPointY=secondPointY+thirdPointY-forthPointY;
//        canvas.drawLine(forthPointX,forthPointY,fifthPointX,fifthPointY,mPaint);
        float forthPointX=secondPointX;
        float forthPointY=secondPointX;
        canvas.drawLine(forthPointX,forthPointY,thirdPointX,thirdPointX,mPaint);
    }
    private void drawWrong(Canvas canvas){
        float strokeWidth = dp1 * 6;
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(wrongColor);
        //(34,46),(74,46),(44,74),(74,74), 74*74
        float firstPointX=ratio*40;
        float firstPointY=ratio*40;
        float secondPointX = ratio*68;
        float secondPointY=firstPointY;
        float thirdPointX = firstPointY;
        float thirdPointY=secondPointX;
        float forthPointX=secondPointX;
        float forthPointY=secondPointX;
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(firstPointX,firstPointY,forthPointX,forthPointY,mPaint);
        canvas.drawLine(secondPointX,secondPointY,thirdPointX,thirdPointY,mPaint);
    }

}
