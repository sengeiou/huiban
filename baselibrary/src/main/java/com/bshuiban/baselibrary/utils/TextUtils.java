package com.bshuiban.baselibrary.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/5/3.<br/>
 * describe：文本工具
 */
public class TextUtils {
    public static Rect getTextRect(String text, Paint paint) {
        Rect rect = new Rect();
        if (null != text) {
            paint.getTextBounds(text, 0, text.length(), rect);
        }
        return rect;
    }

    public static int getTextHeightR(String text, Paint paint) {
        Rect textRect = getTextRect(text, paint);
        return textRect.height();
    }

    public static float getTextWidthR(String text, Paint paint) {
        Rect textRect = getTextRect(text, paint);
        return textRect.width();
    }

    public static float getTextWidth(String text, Paint paint) {
        return paint.measureText(text);
    }

    public static float getTextBaseLine(float center, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return center - fontMetrics.bottom / 2 - fontMetrics.top / 2;
    }

    /**
     * 画字
     *
     * @param text   内容
     * @param x      x轴起始坐标
     * @param y      y轴起始坐标
     * @param paint  画笔
     * @param canvas 画布
     */
    public static void drawText(String text, float x, float y, Paint paint, Canvas canvas) {
        int textHeightR = getTextHeightR(text, paint);
        float textBaseLine = y + getTextBaseLine(textHeightR / 2f, paint);
        canvas.drawText(text, x, textBaseLine, paint);
    }

    /**
     * 容器底部画字
     *
     * @param text   内容
     * @param x      x轴起始坐标
     * @param heightY 容器的底部坐标（Y轴）
     * @param paint  画笔
     * @param canvas 画布
     */
    public static void drawTextBottom(String text, float x, float heightY, Paint paint, Canvas canvas) {
        int textHeightR = getTextHeightR(text, paint);
        float textBaseLine = getTextBaseLine(textHeightR / 2f, paint);
        float y = heightY - textHeightR + textBaseLine;
        canvas.drawText(text, x, y, paint);
    }

    /**
     * 容器中心画字
     * @param text   内容
     * @param x      x轴起始坐标
     * @param center 容器一半的坐标（Y轴方向）
     * @param paint  画笔
     * @param canvas 画布
     */
    public static void drawTextCenter(String text, float x, float center, Paint paint, Canvas canvas) {
        float textBaseLine = getTextBaseLine(center, paint);
        canvas.drawText(text, x, textBaseLine, paint);
    }
    public static String getUserIdListJson(String userId,int start,int limit){
        return "{\"userId\":\""+userId+"\",\"start\":"+start+",\"limit\":"+limit+"}";
    }
    public static String getUserIdListIndexJson(String userId,int start,int limit){
        return "{\"userId\":\""+userId+"\",\"index\":"+start+",\"limit\":"+limit+"}";
    }
}
