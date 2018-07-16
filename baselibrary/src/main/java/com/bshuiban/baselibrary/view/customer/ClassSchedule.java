package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.ClassScheduleBean;
import com.bshuiban.baselibrary.utils.TextUtils;

import java.util.List;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：班级课表
 */
public class ClassSchedule extends View {
    /**
     * 头部信息字体大小
     */
    private int headTextSize = (int) getResources().getDimension(R.dimen.dp_11);
    /**
     * 上下午字体大小
     */
    private int dateTextSize = (int) getResources().getDimension(R.dimen.dp_13);
    /**
     * 数字字体大小
     */
    private int numTextSize = 24;
    /**
     * 表格内字体大小
     */
    private int textSize = (int) getResources().getDimension(R.dimen.dp_10);
    /**
     * 线条颜色
     */
    private int lineColor = Color.GRAY;
    private int headTextColor = Color.BLACK;
    private int textColor = Color.BLACK;
    /**
     * 休息背景色
     */
    private int restBgColor = ContextCompat.getColor(getContext(), R.color.line_bord);
    private int dateTextColor = Color.WHITE;
    private int dateBgColors[] = {ContextCompat.getColor(getContext(), R.color.guide_start_btn), Color.parseColor("#F5A623"), Color.parseColor("#1D4B81")};
    /**
     * 表格高度
     */
    private int gridHeight = (int) getResources().getDimension(R.dimen.dp_33);
    /**
     * 头部表格高度
     */
    private int gridHeadHeight = (int) getResources().getDimension(R.dimen.dp_43);
    private Paint mPaint, paintLine;
    private float everyLength;
    private boolean teachTag;
    private List<List<ClassScheduleBean.DataBean>> data;

    public ClassSchedule(Context context) {
        this(context, null);
    }

    public ClassSchedule(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassSchedule(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        paintLine = new Paint(mPaint);
        paintLine.setColor(lineColor);
        dp1 = TypedValue.applyDimension(1, 1, getResources().getDisplayMetrics());
        paintLine.setStrokeWidth(dp1);
    }

    public void setTeachTag(boolean teachTag) {
        this.teachTag = teachTag;
        //gridHeight = (int) getResources().getDimension(R.dimen.dp_66);
    }
    private int measureHeight;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if(measureHeight==0) {
            measureHeight = getPaddingTop() + getPaddingBottom() + gridHeadHeight + gridHeight * 12;
        }
        int height=measureHeight;
        switch (mode) {
            case MeasureSpec.EXACTLY: {
                height = getMaxHeight(heightMeasureSpec, height);
            }
            break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED: {
                height = getMaxHeight(heightMeasureSpec, height);
            }
            break;
        }
        setMeasuredDimension(width, height);
    }

    private int getMaxHeight(int heightMeasureSpec, int height) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("TAG", "getMaxHeight: "+size+", "+height );
        if (size > measureHeight) {
            height = size;
            int realHeight = size - getPaddingTop() - getPaddingBottom();
            float v = gridHeadHeight * 1f / gridHeight;
            float gridHeightf = realHeight / (v + 12);
            gridHeadHeight = (int) (gridHeightf * v);
            gridHeight = (int) gridHeightf;
        }
        return height;
    }

    float dp1;

    @Override
    protected void onDraw(Canvas canvas) {
        everyLength = getMeasuredWidth() / 9f;//每一个小格的长度
        float everyLengthHalf = everyLength / 2f;//每一个小格的长度的一半
        paintLine.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paintLine);
        //头部
        mPaint.setTextSize(headTextSize);
        mPaint.setColor(headTextColor);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        //canvas.drawText("星期",everyLength*2, TextUtils.getTextBaseLine(TextUtils.getTextHeightR("星",mPaint)/2,mPaint));
        float everyLength2 = everyLength * 2;
        TextUtils.drawText("星期", everyLength2 - 10, 10, mPaint, canvas);
        mPaint.setTextAlign(Paint.Align.CENTER);
        TextUtils.drawTextBottom("科目", everyLengthHalf, gridHeadHeight - 10, mPaint, canvas);
        float textHeadBaseLine = TextUtils.getTextBaseLine(gridHeadHeight / 2f, mPaint);
        String week[] = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        float xCenter[] = new float[week.length + 2];
        for (int i = 0; i < week.length; i++) {
            float v = everyLength2 + everyLength * i;
            xCenter[i + 2] = v + everyLengthHalf;
            canvas.drawText(week[i], xCenter[i + 2], textHeadBaseLine, mPaint);
            canvas.drawLine(v, getPaddingTop(), v, getMeasuredHeight() - getPaddingBottom(), paintLine);//竖线
        }
        xCenter[0] = everyLengthHalf;
        xCenter[1] = everyLength + everyLengthHalf;
        paintLine.setStyle(Paint.Style.FILL);
        List<List<ClassScheduleBean.DataBean>> subListAm = null;
        List<List<ClassScheduleBean.DataBean>> subListPm = null;
        List<List<ClassScheduleBean.DataBean>> subList = null;
        if (null != data) {
            if (data.size() > 3) {
                subListAm = data.subList(0, 4);
            }
            if (data.size() > 7) {
                subListPm = data.subList(4, 8);
            }
            if (data.size() > 8) {
                subList = data.subList(8, data.size());
            }
        }
        //上午
        float amBottom = drawDateArea("上午", dateBgColors[0], xCenter, gridHeadHeight, subListAm, canvas);
        //午休
        float amRestBottom = rest("午休", canvas, amBottom);
        //下午
        float pmBottom = drawDateArea("下午", dateBgColors[1], xCenter, amRestBottom, subListPm, canvas);
        //晚休
        float pmRestBottom = rest("晚休", canvas, pmBottom);
        //晚自习
        float night = drawDateArea("晚自习", dateBgColors[2], xCenter, pmRestBottom, subList, canvas);
        float startY = night - dp1 / 2;
        canvas.drawLine(0, startY,getMeasuredWidth(),startY,paintLine);
    }

    private float rest(String text, Canvas canvas, float amBottom) {
        canvas.drawLine(0, amBottom+dp1/2f, getMeasuredWidth(), amBottom+dp1/2f, paintLine);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(restBgColor);
        canvas.drawRect(0, amBottom+dp1, getMeasuredWidth(), amBottom + gridHeight+dp1, mPaint);
        mPaint.setTextSize(headTextSize);
        mPaint.setColor(headTextColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        TextUtils.drawTextCenter(text, getMeasuredWidth() / 2f, amBottom + gridHeight / 2f, mPaint, canvas);
        return amBottom + gridHeight+dp1;
    }

    private float drawDateArea(String text, int colorBg, float[] xCenter, float top, List<List<ClassScheduleBean.DataBean>> texts, Canvas canvas) {
        int COUNT;
        if (text.length() == 2) {
            COUNT = 4;
        } else {
            COUNT = 2;
        }
        //上下午背景区域
        mPaint.setColor(colorBg);
        mPaint.setStyle(Paint.Style.FILL);
        int areaHeight = this.gridHeight * COUNT;
        canvas.drawRect(0, top-dp1, everyLength, areaHeight + top, mPaint);
        //上下午文字
        //mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(dateTextSize);
        mPaint.setColor(dateTextColor);
        int everyTextHeight = TextUtils.getTextHeightR("上", mPaint);
        final int paddingText = 10;//字体上下间隔
        final int textHeight = everyTextHeight * text.length() + (text.length() - 1) * paddingText;
        float textBaseLine = TextUtils.getTextBaseLine(everyTextHeight, mPaint);
        float centerY = areaHeight / 2f + top;//中心Y坐标;
        float firstY = centerY - textHeight / 2f;//第一个字体Y坐标
        for (int i = 0; i < text.length(); i++) {
            canvas.drawText(String.valueOf(text.charAt(i)), xCenter[0], firstY + textBaseLine + (everyTextHeight + paddingText) * i, mPaint);
        }
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        float baseLine = TextUtils.getTextBaseLine(this.gridHeight / 2f, mPaint);
        boolean tag = texts == null || texts.size() == 0;//数据无效
        List<ClassScheduleBean.DataBean> dataBeans = null;
        ClassScheduleBean.DataBean dataBean;
        //float x;
        TextPaint textPaint = new TextPaint();
        textPaint.set(mPaint);
        for (int i = 0; i < COUNT; i++) {
            if (!tag && texts.size() > i) {
                dataBeans = texts.get(i);
            } else {
                dataBeans = null;
            }
            canvas.drawLine(everyLength, top, getMeasuredWidth(), top, paintLine);
            int size = 0;//每周几节课。。。暂且不用2018年6月28日13:47:02
            if (null != dataBeans) {
                //x = 2 * everyLength;
                for (int j = 2; j < xCenter.length; j++) {
                    if (dataBeans.size() > j) {
                        dataBean = dataBeans.get(j);
                        if (null != dataBean) {
                            String subjectName = dataBean.getSubjectName();
                            //dataBean.get
                            if (teachTag) {
                                String sub = TextUtils.cleanNull(dataBean.getSubName());
                                if (sub.length() > 0) {
                                    sub = "\n(" + sub + ")";
                                }
                                subjectName = TextUtils.cleanNull(dataBean.getClassName()) + sub;
                            }
                            //Log.e("TAG", "drawDateArea: "+subjectName );
                            if (!android.text.TextUtils.isEmpty(subjectName)) {
                                //canvas.drawText(subjectName, xCenter[j], baseLine + top, mPaint);
//                                StaticLayout layout = new StaticLayout(subjectName, textPaint, (int)everyLength, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
//                                canvas.save();
//                                canvas.translate(xCenter[j],top);
//                                layout.draw(canvas);
//                                canvas.restore();
                                //TextPaint tp = new TextPaint();textPaint.setStyle(Paint.Style.FILL);textPaint.setTextSize(50);
                                Point point = new Point((int) (xCenter[j] - everyLength / 2), (int) top);
                                textCenter(subjectName, textPaint, canvas, point, (int) everyLength, Layout.Alignment.ALIGN_NORMAL, 1f, 0, false);
                                size++;
                            }
                        }
                    }
                    // x=+everyLength;
                }
            }
            canvas.drawText(String.valueOf(i + 1), xCenter[1], baseLine + top, mPaint);
            top += this.gridHeight;
        }
        return top;
    }

    private void textCenter(String string, TextPaint textPaint, Canvas canvas, Point point, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        StaticLayout staticLayout = new StaticLayout(string, textPaint, width, align, spacingmult, spacingadd, includepad);
        canvas.save();
        canvas.translate(staticLayout.getWidth() / 2 + point.x, (gridHeight - staticLayout.getHeight()) / 2 + point.y);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    public void setData(List<List<ClassScheduleBean.DataBean>> data) {
        this.data = data;
        invalidate();
    }
}
