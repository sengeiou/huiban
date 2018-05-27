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
import android.util.TypedValue;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.ViewData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xin_heng on 2017/10/17 0017.
 */

public class BarsCharView extends View {
    private final String TAG = "MY_BarChart";
    private final int space_y = 10;//y轴 字体距y轴的距离
    private final int space_x = 10;//x轴 字体距x轴的距离
    private int min_size = -1;
    private int top_inf_color, x_text_color, y_text_color;
    private float title_size, x_text_size, y_text_size, top_inf_size;
    private float h_w;
    private String mTitleLeft;
    private String[] xArray;
    private String[] yArray = {"0", "20", "40", "60", "80", "100"};
    //private List<Entry> list;
    private List<List<Entry>> list_all;
    private Paint paint;
    private Paint paintRectF;

    private float barWidthSpace = 0.1f;//大矩形的间隙/2 相对于width
    private float barSpace = 0.02f;//子矩形的间隙 相对于大矩形
    private float barWidth;//大矩形宽度 相对于大矩形+间隙(大大矩形)

    private int paddingTop = dp2px(15);
    private int paddingLeft = dp2px(10);
    private int paddingBottom = dp2px(10);
    private int paddingRight = dp2px(10);
    private boolean mDrawGridBackground;
    private Paint mGridBackgroundPaint;
    private int rotate;
    private boolean mBordTag;
    private String mTitle;
    /**
     * 设置宽度
     */
    private int mesureWidth;
    private int color0=-1;
    private int color1=-1;

    public BarsCharView(Context context) {
        this(context, null);
    }

    public BarsCharView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarsCharView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BarsCharView);
        mBordTag = typedArray.getBoolean(R.styleable.BarsCharView_bord, false);

        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.BarsCharView_bord) {
                mBordTag = typedArray.getBoolean(index, false);
            } else if (index == R.styleable.BarsCharView_title) {
                mTitle = typedArray.getString(index);
            } else if (index == R.styleable.BarsCharView_title_left) {
                mTitleLeft = typedArray.getString(index);
            } else if (index == R.styleable.BarsCharView_h_w) {
                h_w = typedArray.getFloat(index, 0);
            } else if (index == R.styleable.BarsCharView_title_size) {
                title_size = typedArray.getDimensionPixelSize(index, -1);
            } else if (index == R.styleable.BarsCharView_x_text_size) {
                x_text_size = typedArray.getDimensionPixelSize(index, -1);
            } else if (index == R.styleable.BarsCharView_y_text_size) {
                y_text_size = typedArray.getDimensionPixelSize(index, -1);
            } else if (index == R.styleable.BarsCharView_top_inf_size) {
                top_inf_size = typedArray.getDimensionPixelSize(index, -1);
            } else if (index == R.styleable.BarsCharView_top_inf_color) {
                top_inf_color = typedArray.getColor(index, -1);
            } else if (index == R.styleable.BarsCharView_x_text_color) {
                x_text_color = typedArray.getColor(index, -1);
            } else if (index == R.styleable.BarsCharView_y_text_color) {
                y_text_color = typedArray.getColor(index, -1);
            } else if (index == R.styleable.BarsCharView_min_size) {
                min_size = typedArray.getInteger(index, -1);
            }
        }
        typedArray.recycle();
        init();
    }

    public void setBord(boolean tag) {
        this.mBordTag = tag;
    }

    /**
     * 添加数据
     *
     * @param list
     * @param tag  是否清空原数据
     */
    public void setList_all(List<Entry> list, boolean tag) {
        if (tag) {
            list_all.clear();
        }
        Log.e(TAG, "setList_all: "+list.size() );
        list_all.add(list);
        if (getWidth() > 0) {
            invalidate();
            requestLayout();
        }
    }

    public void setListAll(List<List<Entry>> lists) {
        list_all.clear();
        list_all = lists;
        if (getWidth() > 0) {
            invalidate();
            requestLayout();
        }
    }

    public void setMin_size(int min_size) {
        this.min_size = min_size;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public void setSpace(float barWidthSpace, float barSpace) {
        this.barSpace = barSpace;
        this.barWidthSpace = barWidthSpace;
        barWidth = 1 - barWidthSpace * 2;
        if (getWidth() > 0) {
            invalidate();
        }
    }

    private void init() {
        list_all = new ArrayList<>();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(getDefaultTextSize(getContext()));
        paintRectF = getPaint();
        mGridBackgroundPaint = new Paint();
        mGridBackgroundPaint.setAntiAlias(true);
        barWidth = 1 - barWidthSpace * 2;
    }

    private float getDefaultTextSize(Context context) {
        return context.getResources().getDimension(R.dimen.dp_8);
    }

    public void setXArray(String[] array) {
        Log.e(TAG, "setXArray: "+ Arrays.toString(array));
        xArray = iniArray(array);
        color0=-1;
        color1=-1;
        if (getWidth() > 0) {
            requestLayout();
            invalidate();
        }
    }

    private String[] iniArray(String[] array) {
        if (array == null) {
            return null;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = iniString(array[i]);
        }
        return array;
    }

    private String iniString(String s) {
        if(s==null)
            return "";
        if (s.length() > 5) {
            return s.substring(0, 6) + "...";
        } else {
            return s;
        }
    }

    public void setDrawGridBackground(boolean enabled) {
        mDrawGridBackground = enabled;
    }

    public void setGridBackgroundColor(int color) {
        mGridBackgroundPaint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        Log.e(TAG, "onMeasure: "+width+", "+mesureWidth );
        if (width == 0) {
            width = mesureWidth;
        }
        int height;
        //Log.e(TAG, "width=" + width);
        if (h_w > 0f) {
            height = (int) ((mesureWidth > 0 ? mesureWidth : width) * h_w);
        } else {
            height = (mesureWidth > 0 ? mesureWidth : width) * 1000 / 1581;
        }
        paddingBottom = paddingLeft = paddingRight = paddingTop = (mesureWidth > 0 ? mesureWidth : width) * 90 / 1581;
        Log.e(TAG, "onMeasure: " + height + ", " + (list_all != null ? list_all.size() : null));
        if (list_all != null && list_all.size() > 0) {
            float topTextWidth = getTopTextWidth();//顶部信息所占宽度 即子矩形的最下宽度
            int size = list_all.size();
            float childWidthF = (1 - barSpace * (size - 1)) / size;//子矩形所占大矩形宽度比例
            if (list_all.get(0) != null) {
                if (xArray != null) {
                    int length = xArray.length;
                    float y_space = getYSpaceX();
                    float every_width = (width - y_space - paddingRight) * 1f / length;//大大矩形的实际宽度
                    if (every_width * barWidth * childWidthF < topTextWidth) {//所给宽度不和要求，添加
                        width = (int) (topTextWidth / childWidthF / barWidth * length + y_space + paddingRight) + 1;
                        Log.e(TAG, "计算后 width=" + width);
                    }
                }
            }
            if (list_all.get(0) != null && list_all.get(0).size() >= 5) {
                rotate = -30;
            }else{
                rotate = 0;
            }
        }else{
            rotate = 0;
        }
        int need_add_ = 0;
        if (rotate != 0&&xArray!=null&&xArray.length>0) {
            String text = com.bshuiban.baselibrary.utils.TextUtils.getMaxLengthS(xArray);
            if(text!=null&&text.length()>0) {
                Rect rect_text = new Rect();
                paint.setTextSize(x_text_size);
                paint.getTextBounds(text, 0, text.length(), rect_text);
                float textMeasureLength = paint.measureText(text);
                double abs = Math.abs(Math.sin(rotate * Math.PI / 180d));
                float rotate_height = (float) (textMeasureLength * abs);
                //Log.e(TAG, "onMeasure: "+text+", "+textMeasureLength+", "+rect_text.height()+", "+rotate_height );
                need_add_ = (int) (rotate_height - rect_text.height());
                //Log.e(TAG, "onMeasure: text="+text+", "+need_add_ +", "+rotate+", "+rect_text.height()+", "+rotate_height);
            }
        }
        Log.e(TAG, "onMeasure: " + (height + need_add_) + " , w=" + width);
        setMeasuredDimension(width, height + need_add_);
    }

    private List<Entry> entries_;

    /**
     * 隐藏或者显示其中的一天数据，紧针对两条
     * 没做扩展
     *
     * @return
     */

    public boolean hiddenOrshow() {
        if (list_all != null) {
            if (list_all.size() == 2) {
                entries_ = list_all.get(1);
                list_all.remove(1);
                invalidate();
                return false;
            } else {
                if (entries_ != null && list_all.size() == 1) {
                    setList_all(entries_, false);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //Log.e(TAG, "mBordTag=" + mBordTag);
        if (mBordTag) {
            drawBackGroundBord(canvas);
        }
        if (xArray == null || yArray == null || xArray.length == 0 || yArray.length == 0) {
            //throw new RuntimeException("数据为初始化xArray|yArray");
            paint.setTextSize(16);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("暂无数据", getWidth() / 2, getHeight() / 2, paint);
            return;
        }
        Rect rect_text = new Rect();
        //x轴绘制初步计算
        if (x_text_size > 0) {
            paint.setTextSize(x_text_size);
        } else {
            paint.setTextSize(getResources().getDimension(R.dimen.dp_8));
        }
        String text = com.bshuiban.baselibrary.utils.TextUtils.getMaxLengthS(xArray);
        paint.getTextBounds(text, 0, 1, rect_text);
        float x_space;
        Paint paintX = new Paint(paint);
        paintX.setStyle(Paint.Style.FILL);
        paintX.setAntiAlias(true);
        if (x_text_color > 0) {
            paintX.setColor(x_text_color);
        } else {
            paintX.setColor(getResources().getColor(R.color.gray_dia));
        }
        //Log.e(TAG, "onDraw: "+x_text_size );
        if (x_text_size > 0) {
            paintX.setTextSize(x_text_size);
        } else {
            paintX.setTextSize(getResources().getDimension(R.dimen.dp_8));
        }
        if (rotate == 0) {
            x_space = paddingBottom + rect_text.height() + space_x;//x轴占用空间 （相对于Y轴(view)）;
        } else {
            //float rotate_height = (float) (paint.measureText(text) * Math.abs(Math.sin(rotate)) + Math.abs(rect_text.height() * Math.cos(rotate)));
            float v = paintX.measureText(text);
            float rotate_height = (float) (v * Math.abs(Math.sin(Math.PI*rotate/180)));
            x_space = paddingBottom + rotate_height + space_x;//x轴占用空间 （相对于Y轴(view)）;
            //Log.e(TAG,"paddingBottom="+paddingBottom+", rotate_height="+rotate_height+", v="+v+", "+rotate+", "+Math.abs(Math.sin(rotate)));
        }

        //Log.e(TAG, x_space + " x距离");
        int margin_top_title = 0;//距标题距离，图表最大值
        int title_height = 0;//标题字体高度
        if (TextUtils.isEmpty(mTitle)) {
            //title 说明
            //Log.e(TAG, "onDraw: "+"x_text_size="+x_text_size+", title_size="+ title_size);
            if(title_size>0){
                paint.setTextSize(title_size);
            }
            String title = "年级超过人数百分比";
            if (!TextUtils.isEmpty(mTitleLeft)) {
                title = mTitleLeft;
            }
            if (!TextUtils.isEmpty(title)) {
                paint.getTextBounds(title, 0, title.length(), rect_text);
                //int title_width = rect_text.width();
                title_height = rect_text.height();
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(title, paddingLeft, getBaseLine(paint, paddingTop + title_height / 2f), paint);
            }
        } else {
            title_height = (int) com.bshuiban.baselibrary.utils.TextUtils.getTextHeightR(mTitle, paint);
            paint.setTextAlign(Paint.Align.CENTER);
            float textSize = paint.getTextSize();
            //paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,15,getResources().getDisplayMetrics()));
            paint.setTextSize(getResources().getDimension(R.dimen.dp_8));
            //canvas.drawText(mTitle, getMeasuredWidth() / 2, getBaseLine(paint, paddingTop + title_height / 2f), paint);
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.LEFT);
            Paint legend = new Paint(this.paint);
            legend.setTextSize(getResources().getDimension(R.dimen.dp_6));
            paint.setTextSize(getResources().getDimension(R.dimen.dp_6));
            float legend_height = 0;
            float legend_length = 0;
            if (list_all != null && list_all.size() > 0) {
                //List<Entry> entries = list_all.get(0);
                String sArray[] = new String[]{"我的", "班级"};
                for (int i = 0; i < sArray.length; i++) {
                    //Entry entry = entries.get(i);
                    //legend.setColor(entry.color);
                    //sArray[i]=entry.s;
                    if (i == 0) {
                        legend_height = com.bshuiban.baselibrary.utils.TextUtils.getTextHeightR("我的", paint);
                    }
                    legend_length += com.bshuiban.baselibrary.utils.TextUtils.getTextWidth(sArray[i], paint);
                    //canvas.drawText();
                }
                //legend_length+=(20*entries.size()-20+(legend_height+10)*entries.size());
                legend_length += (20 * 2 - 20 + (legend_height + 10) * 2);
                //Log.e(TAG, "legend_length=" + legend_length);
                RectF rectF = new RectF();
                rectF.top = paddingTop + title_height + paddingTop / 2;
                rectF.bottom = rectF.top + legend_height;
                float base_line = com.bshuiban.baselibrary.utils.TextUtils.getTextBaseLine(legend_height / 2, legend) + rectF.top;
                if (list_all.size() > 0) {//仅仅使用这次，没有扩展，没心情
                    for (int i = 0; i < 2; i++) {
                        if(i==0&&color0==-1) {
                            color0 = list_all.get(i).get(0).color;
                        }
                        if(i==1&&color1==-1) {
                            color1 = list_all.get(i).get(0).color;
                        }
                        legend.setColor(i==0?color0:color1);
                        if (i == 0) {
                            rectF.left = getMeasuredWidth() / 2 - legend_length / 2;
                            rectF.right = rectF.left + legend_height;
                        } else if (i == 1) {
                            rectF.left = getMeasuredWidth() / 2 + 10;
                            rectF.right = rectF.left + legend_height;
                        }
                        //canvas.drawRect(rectF, legend);
                        //canvas.drawText(sArray[i], rectF.right + 10, base_line, paint);
                    }
                }
                title_height = (int) (rectF.height());
            }
            paint.setTextSize(getDefaultTextSize(getContext()) - 5);
        }
        margin_top_title = paddingTop * 1;
        //y轴
        int size_y = yArray.length - 1;//分成几个
        String stringMaxLength = "100%";
        Paint paintY = new Paint(paint);
        if (y_text_color > 0) {
            paintY.setColor(y_text_color);
        } else {
            paintY.setColor(getResources().getColor(R.color.gray_dia));
        }
        if (y_text_size > 0) {
            paintY.setTextSize(y_text_size);
        }
        paintY.getTextBounds(stringMaxLength, 0, stringMaxLength.length(), rect_text);//字符串必须为最长的一个
        float max_y_center = paddingTop + title_height + margin_top_title + rect_text.height() / 2f + rect_text.height() + 20;
        float max_y_x = paddingLeft + paintY.measureText(stringMaxLength);
        final float y_space = max_y_x + space_y;//y轴占用空间 X

        Paint paintLine = new Paint(paint);
        //y轴网格（垂直于y轴）
        float length_y = getMeasuredHeight() - x_space - max_y_center;
        float grid_y_space = length_y / size_y;
        paintLine.setColor(Color.parseColor("#E8E9E9"));
        paintLine.setStrokeWidth(1);
        int stopX = getMeasuredWidth() - paddingRight;
        paintY.setTextAlign(Paint.Align.RIGHT);
        if (mDrawGridBackground) {
            canvas.drawRect(y_space, max_y_center, stopX, max_y_center + size_y * (grid_y_space), mGridBackgroundPaint);
        }
        //Log.e(TAG, "grid_y_space=" + grid_y_space + ", max_y_center=" + max_y_center + ", size_y=" + size_y + ", " + getMeasuredHeight());
        for (int i = 0; i <= size_y; i++) {//需要多size_y一条线
            float y = max_y_center + i * (grid_y_space);
            canvas.drawLine(y_space, y, stopX, y, paintLine);//网格
            canvas.drawText(yArray[size_y - i] + "%", max_y_x, getBaseLine(paint, y), paintY);//y轴上数据
        }
        //x轴
        //final int size = list.size();
        final int size = list_all.get(0).size();
        int size_;
        if (min_size > 0) {
            size_ = min_size;
        } else {
            size_ = size > 4 ? size : 4;
        }
        float every_space_x = (getMeasuredWidth() - y_space - paddingRight) * 1f / size_;//一条大大矩形数据占用空间x轴（实际值，两个空格）
        if (every_space_x < 3) {
            Log.e(TAG, "宽度设置错误");
        }
        if (barWidth > 1) {
            Log.e(TAG, "数据宽度比例好设置错误");
            return;
        }
        float every_bar_width_x = barWidth * every_space_x;//一条数据实际值x
        //float every_bar_space_x = (1 - barWidth) / 2f * every_space_x;//空格值x
        float every_bar_space_x = barWidthSpace * every_space_x;//空格值x
        //绘制数据
        RectF rectF = new RectF();//大矩形
        float bottom = getMeasuredHeight() - x_space;
        float top = (100 - Integer.parseInt("100")) / 100f * length_y + max_y_center;
        rectF.bottom = bottom;
        rectF.top = top;
        Entry entry;

        float y = getBaseLine(paintX, bottom + com.bshuiban.baselibrary.utils.TextUtils.getTextHeightR(text,paintX) / 2 + space_x);//x轴数据基线
        //Log.e(TAG,"x轴数据基线="+y);
        for (int i = 0; i < size; i++) {
            //Entry entry = list.get(i);
            float left = every_space_x * i + every_bar_space_x + y_space;
            float right = left + every_bar_width_x;
            //rectF = new RectF(left, top, right, bottom);//大矩形
            //rectF.left =left;
            //rectF.right =right;
            //paintRectF.setColor(Color.YELLOW);
            //canvas.drawRect(rectF, paintRectF);
            /*********************************添加子图形**************************************/
            float every_bar_width_space_x = every_space_x * barSpace;//子矩形间的间隙
            float every_bar_width_width_x = (every_bar_width_x - every_bar_width_space_x * (list_all.size() - 1)) / list_all.size();//子矩形宽度
            //float every_bar_width_width_x = every_bar_width_x/list_all.size() -every_bar_width_space_x;//子矩形宽度
            float every_bar_width_sum = every_bar_width_space_x + every_bar_width_width_x;
            Paint paintTopInf = new Paint(paint);
            if (top_inf_color > 0) {
                paintTopInf.setColor(top_inf_color);
            } else {
                paintTopInf.setColor(getResources().getColor(R.color.gray_dia));
            }
            if (top_inf_size > 0) {
                paintTopInf.setTextSize(top_inf_size);
            }
            paintTopInf.setTextAlign(Paint.Align.CENTER);
            for (int j = 0; j < list_all.size(); j++) {
                entry = list_all.get(j).get(i);
                paintRectF.setColor(entry.color);
                rectF.left = every_bar_width_sum * j + left;
                rectF.right = rectF.left + every_bar_width_width_x;
                float num=0;
                try {
                    num = Float.parseFloat(entry.s);
                }catch (Exception e){

                }
                rectF.top = (100 - num) / 100f * length_y + max_y_center;
                if(rectF.height()>0f) {
                    canvas.drawRect(rectF, paintRectF);
                }
                float x = (rectF.left + rectF.right) / 2;
                if(num>=0) {
                    canvas.drawText((num == 0f ? 0 : entry.s) + "%", x, getBaseLine(paintTopInf, rectF.top - rect_text.height() / 2 - 10), paintTopInf);
                }
            }
            /********************************绘制x轴下标数据*****************************************/
            float x = (left + right) / 2;
            //float y = getBaseLine(this.paint, bottom + rect_text.height() / 2 + x_space);
            if (rotate != 0) {
                paintX.setTextAlign(Paint.Align.RIGHT);
                canvas.save();
                canvas.rotate(rotate, x, bottom + space_x);
                canvas.drawText(xArray[i], x, y, paintX);
                canvas.restore();
            } else {
                paintX.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(xArray[i], x, y, paintX);
            }
//            if(i==2){
//                int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
//                paintRectF.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//                canvas.drawRect(rectF, paintRectF);
//                canvas.restoreToCount(layerId);
//                paintRectF.setXfermode(null);
//            }
        }
    }

    private void drawBackGroundBord(Canvas canvas) {
//        if (getBackground() == null) {
//            //setBackgroundResource(R.drawable.bg_bord);
//            GradientDrawable shapeDrawable=new GradientDrawable();
//            shapeDrawable.setCornerRadius(ViewData.radius);
//            shapeDrawable.setStroke((int) TypedValue.applyDimension(1,1,getResources().getDisplayMetrics()),getResources().getColor(R.color.line_bord));
//            shapeDrawable.setColor(Color.WHITE);
//            setBackground(shapeDrawable);
//            //setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_bord));
//            return;
//        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        paint.setAlpha(255);
        paint.setColorFilter(null);
//        mFillPaint.setAlpha(currFillAlpha);
//        mFillPaint.setDither(st.mDither);
//        mFillPaint.setColorFilter(mColorFilter);
        paint.setStrokeWidth(TypedValue.applyDimension(1,1,getResources().getDisplayMetrics()));
        RectF rectF = new RectF(1, 1, getMeasuredWidth()-1, getMeasuredHeight()-1);
        canvas.drawRoundRect(rectF, ViewData.radius, ViewData.radius, paint);
        paint.setColor(getResources().getColor(R.color.line_bord));
        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,1,getResources().getDisplayMetrics()));
//        paint.setStrokeWidth();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectF, ViewData.radius, ViewData.radius, paint);
    }

    private float getTopTextWidth() {
        Paint p = new Paint();
        if (top_inf_size > 0) {
            p.setTextSize(top_inf_size);
        } else {
            p.setTextSize(getResources().getDimension(R.dimen.dp_8));
        }
        //顶部信息宽度
        float textWidth = com.bshuiban.baselibrary.utils.TextUtils.getTextWidth("100.0%", p);
        return textWidth;
    }

    /**
     * y轴占用空间 X
     *
     * @return
     */
    private float getYSpaceX() {
        String stringMaxLength = "100%";
        Paint paintY = new Paint(paint);
        if (y_text_color > 0) {
            paintY.setColor(y_text_color);
        } else {
            paintY.setColor(getResources().getColor(R.color.gray_dia));
        }
        if (y_text_size > 0) {
            paintY.setTextSize(y_text_size);
        }
        Rect rect_text = new Rect();
        paintY.getTextBounds(stringMaxLength, 0, stringMaxLength.length(), rect_text);//字符串必须为最长的一个
        //float max_y_center = paddingTop + title_height + margin_top_title + rect_text.height() / 2f + rect_text.height() + 20;
        float max_y_x = paddingLeft + paintY.measureText(stringMaxLength);
        //Log.i(TAG, rect_text.width() + ", " + (paint.measureText(stringMaxLength)));
        //Log.i(TAG, rect_text.height() + ", " + (paint.getFontMetrics().bottom - paint.getFontMetrics().top));
        final float y_space = max_y_x + space_y;//y轴占用空间 X
        return y_space;
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    private float getBaseLine(Paint paint, float center) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return center - fontMetrics.top / 2 - fontMetrics.bottom / 2;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    public void setMesureWidth(int mesureWidth) {
        this.mesureWidth = mesureWidth;
        requestLayout();
        invalidate();
    }

    public static class Entry {
        private int i;
        private String s;
        private Object o;
        private int color;

        public Entry(int i, String s, int color) {
            this.i = i;
            this.s = s;
            this.color = color;
        }

        public Entry(int i, String s, int color, Object o) {
            this.i = i;
            this.s = s;
            this.color = color;
            this.o = o;
        }
    }

}
