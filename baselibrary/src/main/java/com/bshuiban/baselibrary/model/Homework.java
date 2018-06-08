package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：作业结果
 *
 */
public class Homework<T extends Homework.Data> {
    private String title;
    private String time;
    private List<T> homework;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<T> getHomework() {
        return homework;
    }

    public void setHomework(List<T> homework) {
        this.homework = homework;
    }

    public static class Data{
        private boolean complete;
        private int result=2;//0 错误 1 正确 2 未知 3 半对

        public boolean getComplete() {
            return complete;
        }

        public void setComplete(boolean complete) {
            this.complete = complete;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }
    }
    public void setFTime(String time){
        float aFloat = 0;
        try {
            aFloat = Float.parseFloat(time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        setTime(getTime(aFloat));
    }
    public static String getTime(float time){
        int h= (int) (time/3600);
        int m=(int)(time-h*3600)/60;
        int s=(int)(time-h*3600-m*60);
        String hour=h>0?(h+"时"):"";
        String min="";
        if(h>0){
            min="0分";
        }else{
            if(m>0){
                min=m+"分";
            }
        }
        return hour+min+s+"秒";
    }
}
