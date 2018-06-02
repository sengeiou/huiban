package com.bshuiban.baselibrary.model;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinheng on 2018/6/1.<br/>
 * describe：
 */
public class HomeworkBean extends Homework.Data {
    public static final String ONLINE = "onLine";
    public static final String EXAM = "exam";
    public static final String VIDEO = "video";
    /**
     * 是否批阅
     */
    private boolean correct;
    /**
     * 此题所属试卷类型
     */
    private String type;
    /**
     * 第几套试卷
     */
    private int pageIndex;
    /**
     * 试卷中第几类
     */
    private int typeIndex;
    /**
     * 一类题第几道
     */
    private int problemIndex;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public int getProblemIndex() {
        return problemIndex;
    }

    public boolean getCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setProblemIndex(int problemIndex) {
        this.problemIndex = problemIndex;
    }

    private static HomeworkBean getHomeworkBean(String type, int pageIndex, int typeIndex, int problemIndex) {
        return getHomeworkBean(false, type, pageIndex, typeIndex, problemIndex);
    }

    private static HomeworkBean getHomeworkBean(boolean correct, String type, int pageIndex, int typeIndex, int problemIndex) {
        HomeworkBean homeworkBean = new HomeworkBean();
        homeworkBean.setType(type);
        homeworkBean.setPageIndex(pageIndex);
        homeworkBean.setTypeIndex(typeIndex);
        homeworkBean.setProblemIndex(problemIndex);
        homeworkBean.setCorrect(correct);
        return homeworkBean;
    }

    public static String getProblemContent(HomeworkInfBean.DataBean homeworkInfBean, String type, int pageIndex, int typeIndex, int problemIndex) {
        Gson gson = new Gson();
        String json;
        switch (type) {
            case ONLINE:
                List<HomeworkInfBean.DataBean.OnLineBean> onLine = homeworkInfBean.getOnLine();
                HomeworkInfBean.DataBean.OnLineBean onLineBean = onLine.get(pageIndex);
                List<HomeworkInfBean.DataBean.OnLineBean.NextBean> next = onLineBean.getNext();
                HomeworkInfBean.DataBean.OnLineBean.NextBean nextBean = next.get(problemIndex);
                json = gson.toJson(nextBean);
                break;
            case EXAM:
                HomeworkInfBean.DataBean.ExamPaperBean.ExamBean.NextBeanX nextBeanX = homeworkInfBean.getExamPaper().get(pageIndex).getExam().get(typeIndex).getNext().get(problemIndex);
                json = gson.toJson(nextBeanX);
                break;
            case VIDEO:
                HomeworkInfBean.DataBean.VideoBean.Exam exam = homeworkInfBean.getVideo().get(pageIndex).getExam().get(problemIndex);
                json = gson.toJson(exam);
                break;
            default:
                json = "";
        }
        return json;
    }

    public static List<HomeworkBean> getHomeworkBean(HomeworkInfBean.DataBean homeworkInfBean) {
        if (null == homeworkInfBean) {
            return null;
        }
        List<HomeworkBean> list = new ArrayList<>();
        List<HomeworkInfBean.DataBean.OnLineBean> onLine = homeworkInfBean.getOnLine();
        List<HomeworkInfBean.DataBean.ExamPaperBean> examPaper = homeworkInfBean.getExamPaper();
        List<HomeworkInfBean.DataBean.VideoBean> video = homeworkInfBean.getVideo();
        if (isEffictive(onLine)) {
            for (int i = 0; i < onLine.size(); i++) {
                HomeworkInfBean.DataBean.OnLineBean onLineBean = onLine.get(i);
                List<HomeworkInfBean.DataBean.OnLineBean.NextBean> next = onLineBean.getNext();
                if (isEffictive(next)) {
                    for (int j = 0; j < next.size(); j++) {
                        HomeworkBean homeworkBean = getHomeworkBean(ONLINE, i, 0, j);
                        HomeworkInfBean.DataBean.OnLineBean.NextBean nextBean = next.get(j);
                        if (null != next) {
                            String optionName = nextBean.getOptionName();
                            if (isSelect(optionName)) {
                                homeworkBean.setCorrect(true);
                                String stuAnswer = (String) nextBean.getStuAnswer();
                                homeworkBean.setComplete(TextUtils.isEmpty(stuAnswer));
                                homeworkBean.setResult(isRight((String) nextBean.getAnswer(), stuAnswer) ? 1 : 0);
                            }
                        }
                        list.add(homeworkBean);
                    }
                }
            }
        }
        if (isEffictive(examPaper)) {
            for (int i = 0; i < examPaper.size(); i++) {
                HomeworkInfBean.DataBean.ExamPaperBean examPaperBean = examPaper.get(i);//一套试卷
                List<HomeworkInfBean.DataBean.ExamPaperBean.ExamBean> exam = examPaperBean.getExam();//试卷中一类题
                if (isEffictive(exam)) {
                    for (int j = 0; j < exam.size(); j++) {
                        HomeworkInfBean.DataBean.ExamPaperBean.ExamBean examBean = exam.get(j);
                        List<HomeworkInfBean.DataBean.ExamPaperBean.ExamBean.NextBeanX> next = examBean.getNext();
                        if (isEffictive(next)) {
                            for (int k = 0; k < next.size(); k++) {
                                HomeworkBean homeworkBean = getHomeworkBean(EXAM, i, j, k);
                                HomeworkInfBean.DataBean.ExamPaperBean.ExamBean.NextBeanX nextBeanX = next.get(k);
                                if (null != next) {
                                    String optionName = nextBeanX.getOptionName();
                                    if (isSelect(optionName)) {
                                        homeworkBean.setCorrect(true);
                                        String stuAnswer = (String) nextBeanX.getStuAnswer();
                                        homeworkBean.setComplete(TextUtils.isEmpty(stuAnswer));
                                        homeworkBean.setResult(isRight((String) nextBeanX.getAnswer(), stuAnswer) ? 1 : 0);
                                    }
                                }
                                list.add(homeworkBean);
                            }
                        }
                    }
                }
            }
        }
        if (isEffictive(video)) {
            for (int i = 0; i < video.size(); i++) {
                HomeworkInfBean.DataBean.VideoBean videoBean = video.get(i);//一套试卷
                List<HomeworkInfBean.DataBean.VideoBean.Exam> exam = videoBean.getExam();//所有题目
                for (int j = 0; j < exam.size(); j++) {
                    HomeworkBean homeworkBean = getHomeworkBean(VIDEO, i, 0, j);
                    HomeworkInfBean.DataBean.VideoBean.Exam exam1 = exam.get(j);
                    if (null != exam1) {
                        String optionName = exam1.getOptionName();
                        if (isSelect(optionName)) {
                            homeworkBean.setCorrect(true);
                            String stuAnswer = exam1.getStuAnswer();
                            homeworkBean.setComplete(TextUtils.isEmpty(stuAnswer));
                            homeworkBean.setResult(isRight(exam1.getAnswer(), stuAnswer) ? 1 : 0);
                        }
                    }
                    list.add(homeworkBean);
                }
            }
        }
        return list;
    }

    public static boolean isEffictive(List list) {
        return null != list && list.size() > 0;
    }

    public static boolean isSelect(String name) {
        if (null != name && ("check".equals(name) || "radio".equals(name))) {
            return true;
        }
        return false;
    }

    public static boolean isRight(String answer, String stuAnswer) {
        if (TextUtils.isEmpty(answer) || TextUtils.isEmpty(stuAnswer)) {
            return false;
        }
        if (answer.equals(stuAnswer)) {
            return true;
        }
        answer = answer.replace(",", "").trim();
        stuAnswer = stuAnswer.replace(",", "").trim();
        if (answer.equals(stuAnswer)) {
            return true;
        }
        return false;
    }
}
