package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

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
    private int headTextSize = 24;
    /**
     * 上下午字体大小
     */
    private int dateTextSize = 24;
    /**
     * 数字字体大小
     */
    private int numTextSize = 24;
    /**
     * 表格内字体大小
     */
    private int textSize = 20;
    /**
     * 线条颜色
     */
    private int lineColor = Color.GRAY;
    private int headTextColor = Color.BLACK;
    private int textColor = Color.BLACK;
    /**
     * 休息背景色
     */
    private int restBgColor = Color.GRAY;
    private int dateTextColor = Color.WHITE;
    private int dateBgColors[] = {Color.GREEN, Color.YELLOW, Color.BLUE};
    /**
     * 表格高度
     */
    private int gridHeight = 60;
    /**
     * 头部表格高度
     */
    private int gridHeadHeight = 50;
    private Paint mPaint, paintLine;
    private float everyLength;
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getPaddingTop() + getPaddingBottom() + gridHeadHeight + gridHeight * 12;
        setMeasuredDimension(width,height);
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
        TextUtils.drawText("星期", everyLength2, 0, mPaint, canvas);
        mPaint.setTextAlign(Paint.Align.CENTER);
        TextUtils.drawTextBottom("科目", everyLengthHalf, gridHeadHeight, mPaint, canvas);
        float textHeadBaseLine = TextUtils.getTextBaseLine(gridHeadHeight / 2f, mPaint);
        String week[] = {"周一", "周二", "周三", "周四", "周五", "周五", "周六", "周日"};
        float xCenter[] = new float[week.length + 2];
        for (int i = 0; i < week.length; i++) {
            float v = everyLength2 + everyLength * i;
            xCenter[i + 2] = v + everyLengthHalf;
            canvas.drawText(week[i], xCenter[i + 2], textHeadBaseLine, mPaint);
            canvas.drawLine(v ,getPaddingTop(),v,getMeasuredHeight()-getPaddingBottom(),paintLine);
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
        drawDateArea("晚自习", dateBgColors[2], xCenter, pmRestBottom, subList, canvas);
    }

    private float rest(String text, Canvas canvas, float amBottom) {
        canvas.drawLine(0, amBottom, getMeasuredWidth(), amBottom, paintLine);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(restBgColor);
        canvas.drawRect(0, amBottom, getMeasuredWidth(), gridHeight, mPaint);
        mPaint.setTextSize(headTextSize);
        mPaint.setColor(headTextColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        TextUtils.drawTextCenter(text, getMeasuredWidth() / 2f, amBottom + gridHeight / 2f, mPaint, canvas);
        return amBottom + gridHeight;
    }

    private float drawDateArea(String text, int colorBg, float[] xCenter, float top, List<List<ClassScheduleBean.DataBean>> texts, Canvas canvas) {
        //上下午背景区域
        mPaint.setColor(colorBg);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, top, everyLength, gridHeight * 4 + top, mPaint);
        //上下午文字
        //mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(dateTextSize);
        mPaint.setColor(dateTextColor);
        int everyTextHeight = TextUtils.getTextHeightR("上", mPaint);
        final int paddingText = 10;//字体上下间隔
        int textHeight = everyTextHeight * text.length() + (text.length() - 1) * paddingText;
        float textBaseLine = TextUtils.getTextBaseLine(everyTextHeight, mPaint);
        float centerY = gridHeight * 2 + top;//中心Y坐标;
        float firstY = centerY - textHeight / 2f;//第一个字体Y坐标
        for (int i = 0; i < text.length(); i++) {
            canvas.drawText(String.valueOf(text.charAt(i)), xCenter[0], firstY + textBaseLine + (everyTextHeight + paddingText) * i, mPaint);
        }
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        float baseLine = TextUtils.getTextBaseLine(gridHeight / 2f, mPaint) + top;
        float startY = 0;
        int height;
        int COUNT = 0;
        boolean tag;
        if (null != texts) {
            COUNT = texts.size();
            tag = true;
        } else {
            if (null != text && text.length() == 2) {
                COUNT = 4;
            }
            tag = false;
        }
        List<ClassScheduleBean.DataBean> dataBeans = null;
        ClassScheduleBean.DataBean dataBean;
        for (int i = 0; i < COUNT; i++) {
            height = gridHeight * i;
            baseLine += height;
            if (tag) {
                dataBeans = texts.get(i);
            }
            for (int j = 1; j < xCenter.length; j++) {
                if (null != dataBeans && dataBeans.size() > j) {
                    dataBean = dataBeans.get(j);
                    if (null != dataBean && null != dataBean.getSubjectName()) {
                        canvas.drawText(dataBean.getSubjectName(), xCenter[j], baseLine, mPaint);
                    }
                }
            }
            startY = top + height;
            canvas.drawLine(everyLength, startY, getMeasuredWidth(), startY, paintLine);
        }
        return startY + gridHeight;
    }

    public void setData(List<List<ClassScheduleBean.DataBean>> data) {
        this.data = data;
        invalidate();
    }
}
