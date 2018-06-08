package com.bshuiban.baselibrary.view.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;

/**
 * Created by xinheng on 2018/3/12.<br/>
 * describe：
 */

@SuppressLint("AppCompatCustomView")
public class TreeChildView extends TextView {

    public TreeChildView(Context context) {
        this(context,null);
    }

    public TreeChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs,0);
    }

    public TreeChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setGravity(Gravity.CENTER_VERTICAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TreeChildView, defStyleAttr, 0);
        int N=typedArray.getIndexCount();
        for (int i=0;i<N;i++){
            int index = typedArray.getIndex(i);
            if(index==R.styleable.TreeChildView_colorPoint){
                colorPoint=typedArray.getColor(index,colorPoint);
            }else if(index==R.styleable.TreeChildView_radius){
                radius=typedArray.getDimensionPixelOffset(index,radius);
            }else if(index==R.styleable.TreeChildView_spaceCirclePoint){
                spaceCirclePoint=typedArray.getDimensionPixelOffset(index,spaceCirclePoint);
            }else if(index==R.styleable.TreeChildView_spaceImgRight){
                spaceImgRight=typedArray.getDimensionPixelOffset(index,spaceImgRight);
            }
        }
        typedArray.recycle();
        setPadding(getPaddingLeft(),getPaddingTop(),getPaddingRight(),getPaddingBottom());
    }

    /**圆点最左边距字体最右边的距离*/
    private int spaceCirclePoint=20;
    private int radius=3;
    private int colorPoint= Color.BLACK;
    /**右边图片距控件上方或下方的距离*/
    private int spaceImgRight=10;
    private final int MinSpace=10;
    public void setSpaceImgRight(int spaceImgRight) {
        this.spaceImgRight = spaceImgRight;
    }

    public void setSpaceCirclePoint(int spaceCirclePoint) {
        this.spaceCirclePoint = spaceCirclePoint;
    }

    public void setColorPoint(int colorPoint) {
        this.colorPoint = colorPoint;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left+spaceCirclePoint, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(right!=null){
            //字体高度
            int heightText = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
            int i = heightText + spaceImgRight * 2;
            int width= (int) (heightText*1f/right.getIntrinsicHeight()*right.getIntrinsicWidth());
            right.setBounds(0,getPaddingTop(),width,getPaddingTop()+heightText);
            setCompoundDrawables(null,null,right,null);
            if(i >getMeasuredHeight()){
                setMeasuredDimension(getMeasuredWidth()+width,i);
            }else{
                setMeasuredDimension(getMeasuredWidth()+width,getMeasuredHeight());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colorPoint);
        canvas.drawCircle(getPaddingLeft()-spaceCirclePoint+radius+1,getMeasuredHeight()/2f,radius,paint);
    }
    private Drawable right;
    public void setCompoundDrawables(@Nullable Drawable right) {
        if(right!=null) {
            right.setBounds(0, 0, 25, 25);
        }
        setCompoundDrawables(null,null,right,null);
//        this.right=right;
    }

}
