package com.bshuiban.baselibrary.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bshuiban.baselibrary.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public static String cleanNull(String s){
        if(null==s){
            return "";
        }
        return s;
    }

    /**
     * -1 转化--
     * @param s 内容
     * @return 转化值
     */
    public static String clearStringForMinusone(String s){
        if("-1".equals(cleanNull(s))){
            s="- - ";
        }
        return s;
    }
    public static String getMaxLengthS(String... values){
        int max=0;
        String sTag="";
        if(values==null){
            return "";
        }
        for (String s:values){
            if(s.length()>max){
                max=s.length();
                sTag=s;
            }
        }
        return sTag;
    }

    /**
     * 集合转化
     * 重写toString() 方法
     * @param list 集合
     * @return 字符串类型集合
     */
    public static List<String> getListString(List list){
        List<String> strings = new ArrayList<>();
        if(null==list||list.size()==0){
            return strings;
        }
        Iterator iterator = list.iterator();

        while (iterator.hasNext()){
            Object next = iterator.next();
            if(next instanceof String){
                strings.add((String) next);
            }else {
                strings.add(next.toString());
            }
        }
        return strings;
    }
}
