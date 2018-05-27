package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xin_heng on 2017/8/24 0024.
 */

public class ExamAnswerBean {
    private String change;//是否有改变
    private int status;//状态
    private String examPaperTitle;//试卷名称
    private int examPaperId;//试卷id
    //private List<VideoAnswerBean> examPaperAnswer;
    private List<PrepareWorkAnswerBean> examPaperAnswer;

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExamPaperTitle() {
        return examPaperTitle;
    }

    public void setExamPaperTitle(String examPaperTitle) {
        this.examPaperTitle = examPaperTitle;
    }

    public int getExamPaperId() {
        return examPaperId;
    }

    public void setExamPaperId(int examPaperId) {
        this.examPaperId = examPaperId;
    }

    public List<PrepareWorkAnswerBean> getExamPaperAnswer() {
        return examPaperAnswer;
    }

    public void setExamPaperAnswer(List<PrepareWorkAnswerBean> examPaperAnswer) {
        this.examPaperAnswer = examPaperAnswer;
    }
}
