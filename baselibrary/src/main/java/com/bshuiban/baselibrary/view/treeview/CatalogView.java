package com.bshuiban.baselibrary.view.treeview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bshuiban.baselibrary.R;

/**
 * Created by Admin on 2018/8/14.
 * 目录子view 含标志图
 */

public class CatalogView extends View {
    private String text;
    private int textSize = 14;
    private int textColor = Color.BLACK;
    private int srcWidth = 20;
    private Drawable drawableSrc;
    private Paint mPaint;
    private RectF rightRect;
    private Drawable drawableOpen;
    private Drawable drawableClose;

    public CatalogView(Context context) {
        this(context, null);
    }

    public CatalogView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CatalogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CatalogView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            /*if (index == R.styleable.CatalogView_catalog_src) {
                drawableSrc = typedArray.getDrawable(index);

            } else */
            if (index == R.styleable.CatalogView_catalog_src_width) {
                srcWidth = typedArray.getDimensionPixelSize(index, srcWidth);

            } else if (index == R.styleable.CatalogView_catalog_text) {
                text = typedArray.getString(index);

            } else if (index == R.styleable.CatalogView_catalog_text_size) {
                textSize = typedArray.getDimensionPixelSize(index, textSize);

            } else if (index == R.styleable.CatalogView_catalog_text_color) {
                textColor = typedArray.getColor(index, textColor);

            }
        }

        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (null == text) {
            text = " ";
        }
        mPaint.setTextSize(textSize);
        //Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        int textHeight = rect.height();
        float textWidth = mPaint.measureText(text);
        int max = Math.max(textHeight, srcWidth);
        int measuredHeight = max + getPaddingTop() + getPaddingBottom();
        int measureWidth = (int) (textWidth + srcWidth + getPaddingLeft() + getPaddingRight());
        setMeasuredDimension(getMeasureViewLength(widthMeasureSpec, measureWidth), getMeasureViewLength(heightMeasureSpec, measuredHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float y = getMeasuredHeight() / 2 - fontMetrics.bottom / 2f - fontMetrics.top / 2f;
        canvas.drawText(text, getPaddingLeft(), y, mPaint);
        if (null != drawableSrc) {
            Rect bounds = drawableSrc.getBounds();
            if (null == bounds) {
                bounds = new Rect();
            }
            float v = (getMeasuredHeight() - srcWidth) / 2f;
            int right = getMeasuredWidth() - getPaddingRight();
            bounds.set(right - srcWidth, (int) v, right, (int) v + srcWidth);
            drawableSrc.setBounds(bounds);
            drawableSrc.draw(canvas);
            if (null == rightRect) {
                rightRect = new RectF();
            }
            rightRect.set(bounds.left - 10, 0, getMeasuredHeight(), getMeasuredWidth());
        }
    }

    public void setText(String text) {
        this.text = text;
        if (getWidth() > 0) {
            invalidate();
        }
    }

    /**
     * 设置图片状态
     * @param srcStatue true-->开启状态，false-->关闭状态
     */
    public void setDrawableStatue(boolean srcStatue) {
        drawableSrc = srcStatue ? drawableOpen : drawableClose;
        if (getWidth() > 0) {
            invalidate();
        }
    }

    public void setDrawableSrc(int idResourceOpen, int idResourceClose) {
        drawableOpen = ContextCompat.getDrawable(getContext(), idResourceOpen);
        drawableClose = ContextCompat.getDrawable(getContext(), idResourceClose);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (null != onClickListener) {
                float x = event.getX();
                float y = event.getY();
                if (null != rightRect && rightRect.contains(x, y)) {
                    onClickListener.rightItemClick(this);
                } else {
                    onClickListener.itemClick(this);
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public final void setOnClickListener(@Nullable OnClickListener l) {
        //禁止
    }

    /**
     * 计算控件大小
     *
     * @param measureSpec   父类值
     * @param measureLength 计算的值
     * @return
     */
    private int getMeasureViewLength(int measureSpec, int measureLength) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                size = Math.min(size, measureLength);
                break;
            case MeasureSpec.EXACTLY:

                break;
            case MeasureSpec.UNSPECIFIED:
                size = measureLength;
                break;
        }
        return size;
    }

    private ClickListenerInterface onClickListener;

    public void setOnClickListener(ClickListenerInterface onClickListener) {
        this.onClickListener = onClickListener;
    }
}
