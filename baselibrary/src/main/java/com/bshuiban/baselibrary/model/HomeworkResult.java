package com.bshuiban.baselibrary.model;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：作业结果
 */
public class HomeworkResult extends Homework.Data {
    /**
     * 试卷类型
     */
    private String type;
    /**
     * 第几套试卷下标
     */
    private int examIndex;
    /**
     * 一套试卷第几类下标
     */
    private int typeIndex;
    /**
     * 一类题第几道下标
     */
    private int nextIndex;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExamIndex() {
        return examIndex;
    }

    public void setExamIndex(int examIndex) {
        this.examIndex = examIndex;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }
}
