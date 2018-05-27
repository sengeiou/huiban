package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.ViewData;

/**
 * Created by xin_heng on 2017/10/13 0013.
 * 排名
 */

public class RectRankingInf extends View {
    private final String TAG = "RectRankingInf";
    private int middle_color=Color.WHITE, right_color=Color.WHITE;
    private float left_first_size, left_second_size, right_size;
    private int bg_color = -1;

    private String subject_name;//学科
    private String subject_name_rata;//学科掌握度
    private String class_ranking;//班级排名
    private String class_progress;//班级进步
    private String grade_ranking;//年级排名

    int rate = ViewData.radius;//背景弧度


    private Paint paintCenter;
    private Paint paintLeft;
    private Paint paintRight;
    private Paint paint;
    private boolean aBoolean;
    private float aFloat;
    private float aMiddlePadding;
    private boolean isAverage = true;

    public RectRankingInf(Context context) {
        this(context, null, 0);
    }

    public RectRankingInf(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setMiddle_color(int middle_color) {
        this.middle_color = middle_color;
    }

    public void setRight_color(int right_color) {
        this.right_color = right_color;
    }

    public void setLeft_first_size(float left_first_size) {
        this.left_first_size = left_first_size;
    }

    public void setLeft_second_size(float left_second_size) {
        this.left_second_size = left_second_size;
    }

    public void setAverage(boolean tag) {
        this.isAverage = tag;
    }

    public void setRight_size(float right_size) {
        this.right_size = right_size;
    }

    public RectRankingInf(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RectRankingInf);
        final int N = a.getIndexCount();
        //bg_color = a.getColor(R.styleable.RectRankingInf_rBackGround,-1);
        aBoolean = a.getBoolean(R.styleable.RectRankingInf_rAll, true);
        for (int i = 0; i < N; i++) {
            int index = a.getIndex(i);
            //int attr = appearance.getIndex(i);
            if (index == R.styleable.RectRankingInf_rBackGround) {
                bg_color = a.getColor(index, -1);
            } else if (index == R.styleable.RectRankingInf_rAll) {
                aBoolean = a.getBoolean(index, true);
            } else if (index == R.styleable.RectRankingInf_rWeight) {
                aFloat = a.getFloat(index, 0);
            } else if (index == R.styleable.RectRankingInf_rMiddlePadding) {
                aMiddlePadding = a.getFloat(index, 0);
            } else if (index == R.styleable.RectRankingInf_left_first_size) {
                left_first_size = a.getDimensionPixelSize(index, -1);
            } else if (index == R.styleable.RectRankingInf_left_second_size) {
                left_second_size = a.getDimensionPixelSize(index, -1);
            } else if (index == R.styleable.RectRankingInf_right_size) {
                right_size = a.getDimensionPixelSize(index, -1);
            } else if (index == R.styleable.RectRankingInf_middle_color) {
                middle_color = a.getColor(index, -1);
            } else if (index == R.styleable.RectRankingInf_right_color) {
                right_color = a.getColor(index, -1);
            }
        }
        a.recycle();
        init();
    }

    public RectRankingInf setaMiddlePadding(float middlePadding) {
        aMiddlePadding = middlePadding;
        return this;
    }

    private void init() {
        /*
         * 1.背景颜色
         * 2.背景边角弧度
         * 3.字体大小 左 右
         * 4.字体颜色 左 中 右
         */
//        subject_name = "语文";
//        subject_name_rata = "80";
//        class_ranking = "10";
//        class_progress = "20";
//        grade_ranking = "650";
        int bg = Color.parseColor("#FFD700");//左边背景颜色
        int textSizeLeft = 25;
        int textSizeRight = 20;
        int textColorLeft = Color.WHITE;
        int textColorCenter = Color.WHITE;
        int textColorRight = Color.WHITE;
        paint = new Paint();
        paint.setColor(bg);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
        paintLeft = getPaint(textSizeLeft, textColorLeft);
        paintCenter = getPaint(textSizeRight, textColorCenter);
        paintRight = getPaint(textSizeRight, textColorRight);
    }

    public void setBackgroundLeftColor(int color) {
//        paint.setColor(color);
//        if (getWidth() > 0) {
//            invalidate();
//        }
        bg_color=color;
        if (getWidth() > 0) {
            invalidate();
        }
    }

    public RectRankingInf setSubject_name(String subject_name) {
        this.subject_name = subject_name;
        return this;
    }

    public RectRankingInf setSubject_name_rata(String subject_name_rata) {
        this.subject_name_rata = subject_name_rata;
        return this;
    }

    public RectRankingInf setClass_ranking(String class_ranking) {
        //class_ranking="1";
        this.class_ranking = class_ranking;
        return this;
    }

    public RectRankingInf setClass_progress(String class_progress) {
        //class_progress="-10";
        this.class_progress = class_progress;
        return this;
    }

    public RectRankingInf setGrade_ranking(String grade_ranking) {
        //grade_ranking="2363";
        this.grade_ranking = grade_ranking;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Log.e(TAG,"size widthMeasureSpec="+MeasureSpec.getSize(widthMeasureSpec)+", heightMeasureSpec="+MeasureSpec.getSize(heightMeasureSpec));
        if (aFloat > 0f) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width, (int) (width * aFloat));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Log.e(TAG, "背景=" + bg_color);
        if (bg_color != -1 || !aBoolean) {
            drawBackGroundBord(canvas);
           /* Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(bg_color != -1 ? bg_color : Color.WHITE);
            RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRoundRect(rectF, 10, 10, paint);*/
        }
        if (TextUtils.isEmpty(subject_name_rata)) {
            paintLeft.setTextAlign(Paint.Align.CENTER);
            paintLeft.setTextSize(20);
            paintLeft.setColor(Color.BLACK);
            canvas.drawText("暂无数据", getMeasuredWidth() / 2, getHeight() / 2, paintLeft);
            return;
        }
        //canvas.drawColor(Color.GREEN);
        int height = getMeasuredHeight();
        //int width=getMeasuredWidth();
        //int left_width= (int) (width/3f);
        int left_width;
        if (aFloat > 0) {
            left_width = (int) (getMeasuredWidth() * aFloat);
        } else {
            left_width = height * 13 / 14;
        }
        //int right_width=width-left_width;
        Log.e(TAG, "left_width=" + left_width + ", height=" + height);
        //圆角矩形背景
        //RectF rectF = new RectF(0, 0, left_width, height);
        //canvas.drawRoundRect(rectF, rate, rate, paint);
        //横线
        final int line_width = 2;//横线条边宽度
        final int line_padding = 10;
        float startY = (height - line_width) / 2f;
        //Log.e(TAG,"startY="+startY+", height="+height);
        //背景上文字
        Rect rect = new Rect();
        paintLeft.setTextAlign(Paint.Align.CENTER);
        final float left_x_center = left_width / 2f;
        String text;
        if("-1".endsWith(subject_name_rata)){
            text="- -";
        }else{
            text = subject_name_rata + "%";
        }
        if (aBoolean) {
            if (left_first_size > 0) {
                paintLeft.setTextSize(left_first_size);
            }
            if (TextUtils.isEmpty(subject_name)) {
                subject_name = " ";
            }
            Paint paintLine = new Paint(paint);
            paintLine.setColor(Color.WHITE);
            paintLine.setStrokeWidth(line_width);
            canvas.drawLine(line_padding, startY, left_width - line_padding, startY, paintLine);
            paintLeft.getTextBounds(subject_name, 0, subject_name.length(), rect);
            final float baseLineY = getTextBaseLine((height - line_width) / 4f, paintLeft.getFontMetrics());
            if (left_x_center < 0 || baseLineY < 0) {
                Log.e(TAG, "左边字体大小设置错误");
            }
            //Log.i(TAG,"width="+rect.width()+", height="+rect.height()+", "+left_x_center+", "+baseLineY);
            canvas.drawText(subject_name, left_x_center, baseLineY, paintLeft);
            canvas.drawText(text, left_x_center, startY + line_width + baseLineY, paintLeft);
        } else {
            if (left_first_size > 0) {
                paintLeft.setTextSize(left_first_size);
            }
            float textHeight = com.bshuiban.baselibrary.utils.TextUtils.getTextHeightR("80%", paintLeft);
            Paint paint = new Paint(paintCenter);
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(2);
            paint.setTextAlign(Paint.Align.CENTER);
            if (left_second_size > 0) {
                paint.setTextSize(left_second_size);
            }
            float textHeight1 = com.bshuiban.baselibrary.utils.TextUtils.getTextHeightR("我的掌握度", paint);
            float top = (getMeasuredHeight() - (textHeight + 20 + textHeight1)) / 2;
            paintLeft.setColor(Color.WHITE);
            canvas.drawText(text, left_x_center, com.bshuiban.baselibrary.utils.TextUtils.getTextBaseLine(top + textHeight / 2, paintLeft), paintLeft);
            canvas.drawText("我的掌握度", left_x_center, com.bshuiban.baselibrary.utils.TextUtils.getTextBaseLine(top + textHeight + 20 + textHeight1 / 2, paint), paint);
        }
        //中间
        String textCenter = "班级排名：";
        if (right_size > 0) {
            paintCenter.setTextSize(right_size);
            paintRight.setTextSize(right_size);
        }
        if (middle_color > 0) {
            paintCenter.setColor(middle_color);
        }
        if (right_color > 0) {
            paintRight.setColor(right_color);
        }
        float space_pading_ = 15;
        if (isAverage) {
            space_pading_ = 0;
        }
        paintCenter.getTextBounds(textCenter, 0, textCenter.length(), rect);
        Paint.FontMetrics fontMetrics = paintCenter.getFontMetrics();
        final float test_space_y = (height) / 6f;
        float space_padding = aMiddlePadding > 0f ? getWidth() * aMiddlePadding : 10;
        int text_center_x = (int) (left_width + space_padding);
        String maxString = getMaxLength(class_progress, class_ranking, grade_ranking);
        float maxLength = com.bshuiban.baselibrary.utils.TextUtils.getTextWidth(maxString + "名", paintRight);
        float text_right_x = text_center_x + rect.width() + space_padding / 2 + maxLength;
        Log.e(TAG, "maxLength=" + maxString + ", text_right_x=" + text_right_x + ", text_center_x=" + text_center_x + ", left_width" + left_width);
        paintRight.setTextAlign(Paint.Align.RIGHT);
        float baseLine_1 = getTextBaseLine(test_space_y, fontMetrics) + space_pading_;
        canvas.drawText(textCenter, text_center_x, baseLine_1, paintCenter);
        canvas.drawText(class_ranking + "名", text_right_x, baseLine_1, paintRight);

        float baseLine_2 = getTextBaseLine(test_space_y * 3, fontMetrics);
        canvas.drawText("班级进步：", text_center_x, baseLine_2, paintCenter);
        canvas.drawText(class_progress + "名", text_right_x, baseLine_2, paintRight);

        float baseLine_3 = getTextBaseLine(test_space_y * 5, fontMetrics) - space_pading_;
        canvas.drawText("年级排名：", text_center_x, baseLine_3, paintCenter);
        canvas.drawText(grade_ranking + "名", text_right_x, baseLine_3, paintRight);

    }

    private float getTextBaseLine(float center, Paint.FontMetrics fm) {
        //return center + (fm.bottom - fm.top)/2 - fm.bottom;
        return center - fm.bottom / 2 - fm.top / 2;
    }

    private String getMaxLength(String... values) {
        int max = 0;
        String maxValue = "";
        for (String value : values) {
            if (value.length() > max) {
                max = value.length();
                maxValue = value;
            }
        }
        return maxValue;
    }

    private Paint getPaint(int textSize, int textColor) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setColor(textColor);
        return paint;
    }

    private void drawBackGroundBord(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(bg_color);
        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(rectF, ViewData.radius, ViewData.radius, paint);
        paint.setColor(getResources().getColor(R.color.line_bord));
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectF, ViewData.radius, ViewData.radius, paint);
    }
}
