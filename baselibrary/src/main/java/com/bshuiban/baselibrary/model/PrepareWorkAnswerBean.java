package com.bshuiban.baselibrary.model;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public class PrepareWorkAnswerBean {
    private int examId;//试题id
    private int score  ;//得分
    private int status;//作答状态
    private Object stuAnswer="";
    private int  change;//是否有改变
    private String type;
    private int  sort;
    private String accuracy="";//是否有分数
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public Object getStuAnswer() {
        return stuAnswer;
    }

    public void setStuAnswer(Object stuAnswer) {
        this.stuAnswer = stuAnswer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
