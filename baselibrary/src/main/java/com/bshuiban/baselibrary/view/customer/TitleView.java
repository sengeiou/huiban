package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.bshuiban.baselibrary.R;

import java.io.File;

/**
 * Created by xinheng on 2018/1/31.<br/>
 * describe：标题 -  默认先图后文字
 */

public class TitleView extends View {
    private boolean middleShow;
    private boolean have_line;
    private String title_text;
    private int title_text_color = Color.BLACK;
    private int title_text_size = 17;
    private String right_text;
    private int right_text_color = Color.BLACK;
    private int right_text_bg = -1;

    private int line_color = Color.GRAY;
    private int right_text_size = 17;
    private Drawables drawables;
    private Paint paint;
    private int drawablePadding = (int) getResources().getDimension(R.dimen.dp_9);
    private Rect left_rect, right_rect,center_rect;
    private String TAG = getClass().getSimpleName();
    private boolean left_click_intercept = true;
    private Paint mPaintMiddle;

    /**
     * 图片距离边框的距离 上下
     */
    private final int IMG_PADDING = 10;

    static class Drawables {
        Drawable mDrawableCenter, mDrawableLeft, mDrawableRight;
        final Rect mCompoundRect = new Rect();
    }

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleView, defStyleAttr, 0);
        final int N = array.getIndexCount();
        Drawable drawableLeft = null, drawableRight = null, drawableCenter = null;
        for (int i = 0; i < N; i++) {
            int index = array.getIndex(i);
            if (index == R.styleable.TitleView_title_text) {
                title_text = array.getString(index);

            } else if (index == R.styleable.TitleView_right_text) {
                right_text = array.getString(index);
            } else if (index == R.styleable.TitleView_title_size) {
                //title_text_size = array.getDimensionPixelSize(index, title_text_size);
                //Log.e(TAG, "TitleView: "+title_text_size+", "+getResources().getDimensionPixelSize(R.dimen.dp_15) );
                title_text_size=getResources().getDimensionPixelSize(R.dimen.dp_16);
            } else if (index == R.styleable.TitleView_right_size) {
                right_text_size = array.getDimensionPixelSize(index, right_text_size);
            } else if (index == R.styleable.TitleView_title_color) {
                title_text_color = array.getColor(index, title_text_color);

            } else if (index == R.styleable.TitleView_right_color) {
                right_text_color = array.getColor(index, right_text_color);

            } else if (index == R.styleable.TitleView_line_color) {
                line_color = array.getColor(index, line_color);

            } else if (index == R.styleable.TitleView_have_line) {
                have_line = array.getBoolean(index, have_line);

            } else if (index == R.styleable.TitleView_src_left) {
                drawableLeft = array.getDrawable(index);

            } else if (index == R.styleable.TitleView_src_center) {
                drawableCenter = array.getDrawable(index);

            } else if (index == R.styleable.TitleView_src_right) {
                drawableRight = array.getDrawable(index);

            }else if(index == R.styleable.TitleView_middle_show){
                middleShow = array.getBoolean(index,middleShow);
            }
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        int leftP = getPaddingLeft();
        if (leftP == 0) {
            leftP = (int) TypedValue.applyDimension(1, 10, getResources().getDisplayMetrics());
        }
        if(middleShow){
            mPaintMiddle=new Paint();
            mPaintMiddle.setDither(true);
            mPaintMiddle.setAntiAlias(true);
            mPaintMiddle.setColor(Color.WHITE);
            mPaintMiddle.setStyle(Paint.Style.FILL);
        }
        setPadding(leftP, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableCenter, drawableRight);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable drawableLeft, Drawable drawableCenter, Drawable drawableRight) {
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft);
        setCompoundDrawablesWithIntrinsicBounds(drawableCenter);
        setCompoundDrawablesWithIntrinsicBounds(drawableRight);
        setCompoundDrawables(drawableLeft, drawableCenter, drawableRight);
    }

    private void setCompoundDrawables(Drawable drawableLeft, Drawable drawableCenter, Drawable drawableRight) {
        final boolean isDrawables = drawableLeft != null || drawableCenter != null || drawableRight != null;
        if (isDrawables) {
            if (drawables != null) {
                if (drawables.mDrawableLeft != null)
                    drawables.mDrawableLeft.setCallback(null);
                drawables.mDrawableLeft = null;
                if (drawables.mDrawableCenter != null)
                    drawables.mDrawableCenter.setCallback(null);
                drawables.mDrawableCenter = null;
                if (drawables.mDrawableRight != null)
                    drawables.mDrawableRight.setCallback(null);
                drawables.mDrawableRight = null;
            } else {
                if (drawables == null) {
                    drawables = new Drawables();
                }
                if (drawables.mDrawableLeft != drawableLeft && drawables.mDrawableLeft != null) {
                    drawables.mDrawableLeft.setCallback(null);
                }
                drawables.mDrawableLeft = drawableLeft;

                if (drawables.mDrawableCenter != drawableCenter && drawables.mDrawableCenter != null) {
                    drawables.mDrawableCenter.setCallback(null);
                }
                drawables.mDrawableCenter = drawableCenter;

                if (drawables.mDrawableRight != drawableRight && drawables.mDrawableRight != null) {
                    drawables.mDrawableRight.setCallback(null);
                }
                drawables.mDrawableRight = drawableRight;
            }
            final Rect compoundRect = drawables.mCompoundRect;
            int[] state = getDrawableState();
            setDrawableParameter(drawableLeft, compoundRect, state);
            setDrawableParameter(drawableCenter, compoundRect, state);
            setDrawableParameter(drawableRight, compoundRect, state);
        }else {
            if (drawables == null) {
                drawables = new Drawables();
            }
        }
    }

    private void setDrawableParameter(Drawable drawable, Rect compoundRect, int[] state) {
        if (drawable != null) {
            drawable.setState(state);
            drawable.copyBounds(compoundRect);
            drawable.setCallback(this);
        }
    }

    private void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }

    public void setTitle_text(String title_text) {
        if (title_text != null && !title_text.equals(this.title_text)) {
            this.title_text = title_text;
            if (getMeasuredWidth() > 0) {
                invalidate();
            }
        }
    }
    public boolean isRightTextEffic(){
        return right_text!=null;
    }
    public void setRight_text(String right_text, int color, int right_text_size) {
        if (right_text == null && this.right_text != null) {
            this.right_text = null;
            invalidate();
            return;
        }
        if (right_text != null && !right_text.equals(this.right_text)) {
            this.right_text = right_text;
            this.right_text_bg = color;
            this.right_text_size = right_text_size;
            if (getMeasuredWidth() > 0) {
                invalidate();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLeft(canvas);
        drawCenter(canvas);
        drawRight(canvas);
        drawLine(canvas);
    }

    private void drawLeft(Canvas canvas) {
        if (drawables.mDrawableLeft != null) {
            int saveCount = canvas.getSaveCount();
            canvas.save();
            Rect bounds = drawables.mDrawableLeft.getBounds();
            //bounds.set(bounds.left,bounds.top,bounds.left+(Math.min(getMeasuredWidth(),bounds.width())),bounds.top+Math.min(getMeasuredHeight(),bounds.height()));
            reSetRect(bounds);
            if (left_rect == null) left_rect = new Rect();
            left_rect.set(0, 0, bounds.width() + getPaddingLeft(), getMeasuredHeight());
            canvas.translate(getPaddingLeft(), (getMeasuredHeight() - bounds.height()) / 2f);
            drawables.mDrawableLeft.draw(canvas);
            canvas.restoreToCount(saveCount);
        }
    }

    private void reSetRect(Rect bounds, int height_) {
        int height_rect = bounds.height();
        int height_really = height_;
        if (height_really < height_rect) {
            int width = (int) (height_really * 1f / height_rect * bounds.width());
            int height = height_really;
            bounds.set(bounds.left, bounds.top, bounds.left + width, bounds.top + height);
        }
    }

    /**
     * 重新设置区域，等比例缩放
     *
     * @param bounds drawable 区域
     */
    private void reSetRect(Rect bounds) {
        int height_rect = bounds.height();
        int height_really = getMeasuredHeight() - IMG_PADDING * 2;
        if (height_really < height_rect) {
            int width = (int) (height_really * 1f / height_rect * bounds.width());
            int height = height_really;
            bounds.set(bounds.left, bounds.top, bounds.left + width, bounds.top + height);
        }
    }

    private void drawRight(Canvas canvas) {
        float text_width = 0f;
        float text_height = 0f;
        if (!TextUtils.isEmpty(right_text)) {
            paint.setTextSize(right_text_size);
            text_width = com.bshuiban.baselibrary.utils.TextUtils.getTextWidth(right_text, paint);
            text_height = com.bshuiban.baselibrary.utils.TextUtils.getTextHeightR(right_text, paint);
        }
        if (drawables.mDrawableRight != null) {
            int saveCount = canvas.getSaveCount();
            canvas.save();
            Rect bounds = drawables.mDrawableRight.getBounds();
            //reSetRect(bounds);
            reSetRect(bounds, (int) getResources().getDimension(R.dimen.dp_14));
            if (right_rect == null) right_rect = new Rect();
            int width_drawable = bounds.width();
            float width_sum = width_drawable + drawablePadding + text_width + getPaddingRight();
            right_rect.set((int) (getMeasuredWidth() - width_sum), 0, getMeasuredWidth(), getMeasuredHeight());

            float dx = getMeasuredWidth() - width_sum;
            canvas.translate(dx, (getMeasuredHeight() - bounds.height()) / 2f);
            drawables.mDrawableRight.draw(canvas);
            canvas.restoreToCount(saveCount);
        } else {
            right_rect = null;
        }
        if (text_width > 0) {
            float dimension = getResources().getDimension(R.dimen.dp_3);
            float top = (getMeasuredHeight() - text_height) / 2f - dimension;
            RectF rect = new RectF(getMeasuredWidth() - text_width - getPaddingRight() - dimension * 6,
                    top,
                    getMeasuredWidth() - getPaddingRight(),
                    top + text_height + dimension * 2);
            if (right_rect == null) right_rect = new Rect();
            right_rect.set((int) rect.left - 5, 0, getMeasuredWidth(), getMeasuredHeight());
            if (right_text_bg != -1) {
                paint.setColor(right_text_bg);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(rect, 4, 4, paint);
                right_text_color = Color.WHITE;
            }
            paint.setColor(right_text_color);
            canvas.drawText(right_text, getMeasuredWidth() - text_width - getPaddingRight() - dimension * 3, com.bshuiban.baselibrary.utils.TextUtils.getTextBaseLine(getMeasuredHeight() / 2f, paint), paint);
        } else {
            if (drawables.mDrawableRight == null)
                right_rect = null;
        }
    }

    private void drawCenter(Canvas canvas) {
        float text_width = 0f;
        if (!TextUtils.isEmpty(title_text)) {
            paint.setTextSize(title_text_size);
            paint.setColor(title_text_color);
            text_width = com.bshuiban.baselibrary.utils.TextUtils.getTextWidth(title_text, paint);
        }
        if (!middleShow&&drawables.mDrawableCenter != null) {
            int saveCount = canvas.getSaveCount();
            canvas.save();
            Rect bounds = drawables.mDrawableCenter.getBounds();
            reSetRect(bounds);
            int width_drawable = bounds.width();
            float width_sum = width_drawable + drawablePadding + text_width;
            float dx = (getMeasuredWidth() - width_sum) / 2f;
            canvas.translate(dx, (getMeasuredHeight() - bounds.height()) / 2f);
            drawables.mDrawableCenter.draw(canvas);
            canvas.restoreToCount(saveCount);
            if (text_width > 0) {
                if (center_rect == null) center_rect = new Rect();
                center_rect.set((int) dx - 5, 0, (int) (dx - 5+bounds.width()+text_width), getMeasuredHeight());
                canvas.drawText(title_text, dx + width_drawable + drawablePadding, com.bshuiban.baselibrary.utils.TextUtils.getTextBaseLine(getMeasuredHeight() / 2f, paint), paint);
            }
        } else {
            if (text_width > 0) {
                float x = (getMeasuredWidth() - text_width) / 2f;
                if (center_rect == null) center_rect = new Rect();
                center_rect.set((int) x - 5, 0, (int) (x-5+text_width), getMeasuredHeight());//10*24
                canvas.drawText(title_text, x, com.bshuiban.baselibrary.utils.TextUtils.getTextBaseLine(getMeasuredHeight() / 2f, paint), paint);
                if(middleShow){
                    int heightR = com.bshuiban.baselibrary.utils.TextUtils.getTextHeightR(title_text, paint);
                    int widthR = heightR*10/24;//三角形 高=宽
                    Path path = new Path();
                    float left = x + text_width + 15;
                    float top = (getMeasuredHeight() - heightR) / 2f;
                    float middleX = left + widthR / 2f;
                    path.moveTo(middleX, top);//起始点
                    float bottom1 = top + heightR / 2f;
                    path.lineTo(left, bottom1);
                    float right = left + widthR;
                    center_rect.right= (int) (center_rect.right+right)+1;
                    path.lineTo(right,bottom1);
                    canvas.drawPath(path,mPaintMiddle);
                    path.close();
                    Path path1 = new Path();
                    float top1=bottom1+(heightR-widthR)/2f;
                    path1.moveTo(left,top1);
                    path1.lineTo(right,top1);
                    path1.lineTo(middleX,top1+widthR);
                    canvas.drawPath(path1,mPaintMiddle);
                    path1.close();
                }
            }
        }
    }
    private void drawLine(Canvas canvas) {
        if (have_line) {
            int strokeWidth = (int) TypedValue.applyDimension(1, 1, getResources().getDisplayMetrics());
            paint.setColor(line_color);
            paint.setStrokeWidth(strokeWidth);
            //paint.setStyle(Paint.Style.FILL);
            float startY = getMeasuredHeight() - strokeWidth;
            //TODO 勿改 适配 1024屏E人E本
            canvas.drawRect(0, startY, getMeasuredWidth(), getMeasuredHeight(), paint);
            //canvas.drawLine(0, startY, getMeasuredWidth(), startY, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                return onTouchDown(event);
            case MotionEvent.ACTION_UP:
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private boolean onTouchDown(MotionEvent event) {
        boolean tag;
        float x = event.getX();
        float y = event.getY();
        tag = isContain(left_rect, x, y);
        if (tag) {
            if (listener != null) {
                listener.leftClick(this);
                return true;
            }
            if (left_click_intercept && this.l != null) {
                l.onClick(this);
                return true;
            }
        }
        if (tag) {
            return false;
        }
        if (isContain(right_rect, x, y) && listener != null) {
            listener.rightClick(this);
            return true;
        }
        if (isContain(center_rect, x, y) && listener != null) {
            listener.centerClick(this);
            return true;
        }

        return false;
    }

    private boolean isContain(Rect r, float x, float y) {
        if (r != null) {
            return r.contains((int) x, (int) y);
        }
        return false;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        //super.setOnTouchListener(l); 禁用
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        this.l = l;
    }

    public void setLeft_click_intercept(boolean left_click_intercept) {
        this.left_click_intercept = left_click_intercept;
    }

    private OnClickListener listener;
    private View.OnClickListener l;

    public void setOnClickListener(@Nullable OnClickListener l) {
        listener = l;
    }
    public abstract static class OnClickListener implements IOnClickListener{
        @Override
        public void centerClick(View v) {
        }
    }
    public interface IOnClickListener {
        void leftClick(View v);

        //void centerClick(View v);
        void rightClick(View v);

        void centerClick(View v);
    }
}
