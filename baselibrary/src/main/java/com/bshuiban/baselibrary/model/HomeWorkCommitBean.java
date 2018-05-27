package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by zx315476228 on 17-7-4.
 */

public class HomeWorkCommitBean {
    private String schoolId;
    private int subjectId;
    private String userId;
    private String classId;
    private String userName;
    private String save;
    private int workId;
    private int prepareId;
    private int upFlag;
    private int status;
    private int times;
    private int score;
    private int type;
    private String title;
    private List<PrepareWorkAnswerBean> examAnswer;//试题
    private List<PrepareVideoAnswerBean> videoAnswer;
    private List<ExamAnswerBean> examPaperAnswer;

    public List<PrepareWorkAnswerBean> getExamAnswer() {
        return examAnswer;
    }


    public void setExamAnswer(List<PrepareWorkAnswerBean> examAnswer) {
        this.examAnswer = examAnswer;
    }

    public List<ExamAnswerBean> getExamPaperAnswer() {
        return examPaperAnswer;
    }

    public void setExamPaperAnswer(List<ExamAnswerBean> examPaperAnswer) {
        this.examPaperAnswer = examPaperAnswer;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }


    public int getUpFlag() {
        return upFlag;
    }

    public void setUpFlag(int upFlag) {
        this.upFlag = upFlag;
    }

    public List<PrepareVideoAnswerBean> getVideoAnswer() {
        return videoAnswer;
    }

    public void setVideoAnswer(List<PrepareVideoAnswerBean> videoAnswer) {
        this.videoAnswer = videoAnswer;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getPrepareId() {
        return prepareId;
    }

    public void setPrepareId(int prepareId) {
        this.prepareId = prepareId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
