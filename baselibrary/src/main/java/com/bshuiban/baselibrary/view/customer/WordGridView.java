package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.StuLearnReportBean;
import com.bshuiban.baselibrary.utils.ViewData;

import java.util.List;

/**
 * Created by xin_heng on 2017/10/17 0017.
 * 表格
 */

public class WordGridView extends View {
    private final String TAG = "WordGridView";
    private int nav_color, child_color;
    private float nav_size, child_size;
    private Paint mPaint;
    private Paint mPaintLine;
    private String[] subjects;
    private String[] childIngs = new String[6];
    private List<StuLearnReportBean.DataBean.ContrastBean> contrastBeans;
    int bord_color;
    private float y_space;
    private float x_space;

    public WordGridView(Context context) {
        this(context, null);
    }

    public WordGridView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WordGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WordGridView,defStyleAttr,0);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int index = a.getIndex(i);
            if (index == R.styleable.WordGridView_nav_size) {
                nav_size = a.getDimensionPixelSize(index, -1);
            } else if (index == R.styleable.WordGridView_child_size) {
                child_size = a.getDimensionPixelSize(index, -1);
            } else if (index == R.styleable.WordGridView_nav_color) {
                nav_color = a.getColor(index, -1);
            } else if (index == R.styleable.WordGridView_child_color) {
                child_color = a.getColor(index, -1);
            }
        }
        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaintLine = new Paint();
        mPaintLine.setStrokeWidth(1);
        mPaintLine.setAntiAlias(true);
        p.setColor(0xFFFFCC44);
        p.setAntiAlias(true);
        p1.setColor(Color.WHITE);
        p1.setStyle(Paint.Style.FILL);
        p1.setAntiAlias(true);

        bord_color = getResources().getColor(R.color.line_bord);
    }

    public void setContrastBeans(List<StuLearnReportBean.DataBean.ContrastBean> contrastBeans) {
        if (contrastBeans != null && contrastBeans.size() > 0) {
            subjects = new String[contrastBeans.size()];
            for (int i = 0; i < contrastBeans.size(); i++) {
                StuLearnReportBean.DataBean.ContrastBean bean = contrastBeans.get(i);
                subjects[i] = bean.getSubjectName();
            }
        } else {
            subjects = null;
        }
        this.contrastBeans = contrastBeans;
        if (getWidth() > 0) {
            invalidate();
            requestLayout();
        }
    }

    public List<StuLearnReportBean.DataBean.ContrastBean> getContrastBeans() {
        return contrastBeans;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        y_space = width * 120f / 1581;
        x_space = width / 6f;
        int i = 0;
        if (subjects != null) {
            i = subjects.length;
        }
        int measuredHeight = (int) (y_space * (2 + i) + 4);
        Log.e(TAG, "measuredHeight" + measuredHeight + ", " + (subjects != null ? subjects.length : 0));
        setMeasuredDimension(width, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG));
        mPaintLine.setColor(Color.WHITE);
        mPaintLine.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(1, 1, getMeasuredWidth()-1, getMeasuredHeight()-1);
        canvas.drawRoundRect(rect, ViewData.radius, ViewData.radius, mPaintLine);
        //边框
        mPaintLine.setColor(bord_color);
        mPaintLine.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rect, ViewData.radius, ViewData.radius, mPaintLine);
        if (subjects == null || subjects.length == 0) {
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(16);
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("暂无数据", getWidth() / 2, getHeight() / 2, mPaint);
            return;
        }
        int length_subject = subjects.length;

        //头部
        float y_head = y_space * 2;
        mPaint.setColor(Color.parseColor("#F8F8F8"));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(2, 2, getMeasuredWidth()-2 , y_head + 12), ViewData.radius, ViewData.radius, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(2, y_head, getMeasuredWidth()-2 , y_head + 12, mPaint);
        mPaint.setColor(bord_color);
        canvas.drawLine(0, y_head, getMeasuredWidth() - 1, y_head, mPaint);
        Log.e(TAG, "onDraw: nav_size="+nav_size +", "+child_size);
        if (nav_size > 0) {
            mPaint.setTextSize(nav_size);
        } else {
            mPaint.setTextSize(getDefaultTextSize(getContext()));
        }
        if (nav_color > 0) {
            mPaint.setColor(nav_color);
        } else {
            mPaint.setColor(Color.BLACK);
        }
        float baseLine1 = com.bshuiban.baselibrary.utils.TextUtils.getTextBaseLine(y_head / 2, mPaint);
        mPaint.setTextAlign(Paint.Align.CENTER);
        float center_x = x_space / 2;
        float center_xs[] = new float[6];
        for (int i = 0; i < 6; i++) {
            center_xs[i] = x_space * i + center_x;
        }
//        center_xs[0]=center_x;
        mPaintLine.setStyle(Paint.Style.FILL);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("科目", center_x, baseLine1, mPaint);
        canvas.drawLine(x_space, 0, x_space, getMeasuredHeight(), mPaintLine);
//        center_xs[1]=x_space+center_x;
        canvas.drawText("我的掌握度", center_xs[1], baseLine1, mPaint);
        canvas.drawLine(x_space * 2, 0, x_space * 2, getMeasuredHeight(), mPaintLine);
        float baseLine3 = com.bshuiban.baselibrary.utils.TextUtils.getTextBaseLine(y_space / 2, mPaint);
        canvas.drawText("班级", x_space * 3, baseLine3, mPaint);
        canvas.drawLine(x_space * 2, y_space, x_space * 6, y_space, mPaintLine);
        canvas.drawLine(x_space * 4, 0, x_space * 4, getMeasuredHeight(), mPaintLine);
        canvas.drawLine(x_space * 3, y_space, x_space * 3, getMeasuredHeight(), mPaintLine);
        canvas.drawText("平均掌握度", center_xs[2], baseLine3 + y_space, mPaint);
        canvas.drawText("最高掌握度", center_xs[3], baseLine3 + y_space, mPaint);

        float baseLine4 = com.bshuiban.baselibrary.utils.TextUtils.getTextBaseLine(y_space / 2, mPaint);
        canvas.drawText("年级", x_space * 5, baseLine4, mPaint);
        //canvas.drawLine(x_space*3,y_space,x_space*5,y_space,mPaintLine);
        canvas.drawLine(x_space * 6, 0, x_space * 6, getMeasuredHeight(), mPaintLine);
        canvas.drawLine(x_space * 5, y_space, x_space * 5, getMeasuredHeight(), mPaintLine);
        canvas.drawText("平均掌握度", center_xs[4], baseLine4 + y_space, mPaint);
        canvas.drawText("最高掌握度", center_xs[5], baseLine4 + y_space, mPaint);

        canvas.drawText("得分评价", x_space * 7, baseLine1, mPaint);
        RectF rectF = new RectF();
        rectF.left = x_space * 6;
        rectF.right = rectF.left + x_space * 2;
        int color = mPaint.getColor();
        if (child_size > 0) {
            mPaint.setTextSize(child_size);
        }
        if (child_color > 0) {
            mPaint.setColor(child_color);
        } else {
            mPaint.setColor(Color.parseColor("#898989"));
        }
        for (int i = 0; i < length_subject; i++) {
            childIngs[0] = subjects[i];
            StuLearnReportBean.DataBean.ContrastBean contrastBean = contrastBeans.get(i);
            childIngs[1] = contrastBean.getRate();
            childIngs[2] = contrastBean.getClassAvg();//班级平均正确率
            childIngs[3] = contrastBean.getClassTop();//班级最高
            childIngs[4] = contrastBean.getGradeAvg();//年级平均正确率
            childIngs[5] = contrastBean.getGradeTop();
            //childIngs[6] = childIngs[1];
            //float word7  = 0.8f;//5颗星 20% 0.2
            float baseLine = baseLine3 + y_head + y_space * i;
            for (int j = 0; j < childIngs.length; j++) {
                if (j < center_xs.length) {
                    String childIng = childIngs[j];
                    if("-1".equals(childIng)){
                        childIng="- -";
                    }else {
                        if (TextUtils.isEmpty(childIng)) {
                            childIng = " ";
                        }
                    }
                    childIng = j == 0 ? childIng : (childIng + "%");
                    canvas.drawText(childIng, center_xs[j], baseLine, mPaint);
                } else {//评分
                    rectF.top = y_head + y_space * i;
                    rectF.bottom = rectF.top + y_space;
                    float score = 0;
                    try {
                        Log.e(TAG, "childIngs[6]=" + childIngs[6]);
                        score = Float.parseFloat(childIngs[6]) / 100f;
                        //score = new Random().nextInt(100)*0.01f;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //drawStar(score, 5, rectF, canvas);
                }
            }
            if (i < length_subject - 1) {
                float y_line = y_head + y_space * (i + 1);
                canvas.drawLine(1, y_line, getMeasuredWidth() - 1, y_line, mPaintLine);
            }
        }
        mPaint.setColor(color);
    }

    private float getDefaultTextSize(Context context) {
        return context.getResources().getDimension(R.dimen.dp_8);
    }

    /**
     * 画五角星
     *
     * @param num    进度
     * @param sum    星星总个数
     * @param rectF  所有星星所在范围
     * @param canvas
     */
    private void drawStar(float num, int sum, RectF rectF, Canvas canvas) {
        if (num > 1) {
            num = 1;
        }
        if (num < 0) {
            num = 0;
        }
        float width_f = 0.1f;//单个星的宽度/rectF的宽
        float space_f = (1 - width_f * sum) / (3 + sum);
        Log.i(TAG, "star f width=" + width_f + ", space=" + space_f + ", num=" + num);
        float width = rectF.width() * width_f;
        float space = rectF.width() * space_f;
        float width_space = width + space;
        if (width > rectF.height()) {
            width = rectF.height() - 2;
            space = (rectF.width() - width * sum) / (3 + sum);
        }
        //Log.i(TAG, "star i width=" + width + ", space=" + space);
        if (width < 20) {
            Log.e(TAG, "表格高宽设置错误，star width=" + width);
        }
        //拆分
        //float star_=1f/sum;
        float star_num_f = num * sum;
        int star_num_i = (int) star_num_f;
        Log.e(TAG, "star_num_i=" + star_num_i + ", star_num_f=" + star_num_f);
        float starX = space * 2 + rectF.left;
        float startY = (rectF.height() - width) / 2 + rectF.top;
        //全部画完
        for (int i = 0; i < sum; i++) {
            float progress;
            if (star_num_i > 0 && i <= star_num_i - 1) {
                progress = 1f;
            } else {
                progress = star_num_f - star_num_i;
                Log.e(TAG, "star_num_i+1=" + progress);
                if (i == star_num_i && progress > 0f) {
                    progress = 0.51f;
                } else {
                    progress = 0f;
                }
            }
            Log.e(TAG, "progress=" + progress);
            drawStar(starX + width_space * i, width, startY, progress, canvas);
        }
        //仅画使用的
        /*for (int i = 0; i < star_num_i; i++) {
            drawStar(starX + width_space * i, width, startY, 1, canvas);
        }
        float subtract = star_num_f - star_num_i;
        Log.i(TAG, "start小数=" + subtract);
        if(subtract>0) {
            drawStar(starX + width_space * star_num_i, width, startY, subtract, canvas);
        }*/
    }

    Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);// 抗锯齿 星星
    Paint p1 = new Paint(Paint.ANTI_ALIAS_FLAG);// 抗锯齿 星星 空心

    /**
     * 画单个五角星
     *
     * @param startX   x起始坐标
     * @param width    星所在正方形的边长
     * @param startY   y起始坐标
     * @param progress 星星阴影部分（进度）
     * @param canvas
     */
    private void drawStar(float startX, float width, float startY, float progress, Canvas canvas) {
        float rate = progress;
        //float width = endX-startX;
        float r = width / 2;
        float translateX = startX + r;
        float translateY = startY + r;

        Path path = getPath(r + 2);
        canvas.save();
        canvas.translate(translateX, translateY);
        p.setStyle(rate < 1 ? Paint.Style.STROKE : Paint.Style.FILL);
        //p.setStrokeWidth(2f);
        //p1.setStrokeWidth(2f);
        p.setAntiAlias(true);
        canvas.drawPath(path, p);
        if (rate < 1) {
            canvas.drawPath(getPath(r - 3), p1);
        }
        canvas.restore();
        if (rate < 1f) {
            int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
            canvas.translate(translateX, translateY);
            p.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, p);
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            RectF rectF = new RectF(-r + 2 * r * rate, -r, r, r);
            canvas.drawRect(rectF, p);
            p.setXfermode(null);
            canvas.restoreToCount(layerId);
        }
    }

    private Path getPath(float r) {
        Path path = new Path();
        path.moveTo(0, -r);//A
        path.lineTo(r * sin(36), r * cos(36));//C
        path.lineTo(-r * sin(72), -r * cos(72));//E
        path.lineTo(r * sin(72), -r * cos(72));//B
        path.lineTo(-r * sin(36), r * cos(36));//D
        path.close();
        return path;
    }

    float cos(int num) {
        return (float) Math.cos(num * Math.PI / 180);
    }

    float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }
}
