package com.bshuiban.student.model;

import java.util.List;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：作业结果
 *
 */
public class Homework {
    private String title;
    private String time;
    private List<Data> homework;

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

    public List<Data> getHomework() {
        return homework;
    }

    public void setHomework(List<Data> homework) {
        this.homework = homework;
    }

    public static class Data{
        private boolean complete;
        private int result;

        public boolean isComplete() {
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

}
