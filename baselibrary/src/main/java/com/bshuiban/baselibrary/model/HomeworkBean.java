package com.bshuiban.baselibrary.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinheng on 2018/6/1.<br/>
 * describe：试题处理
 */
public class HomeworkBean extends Homework.Data {
    public static final String ONLINE = "onLine";
    public static final String EXAM = "exam";
    public static final String VIDEO = "video";
    private boolean objective;
    /**
     * 是否批阅
     */
    private boolean correct;
    /**
     * 试题Id （拼接的）
     */
    private String problemId;
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

    public boolean getObjective() {
        return objective;
    }

    public void setObjective(boolean objective) {
        this.objective = objective;
    }

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

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    /**
     * @param type         {@link com.bshuiban.baselibrary.model.HomeworkBean#type}
     * @param pageIndex    {@link HomeworkBean#pageIndex}
     * @param typeIndex    {@link HomeworkBean#typeIndex}
     * @param problemIndex {@link HomeworkBean#problemIndex}
     * @return 作业类
     */
    private static HomeworkBean getHomeworkBean(String type, int pageIndex, int typeIndex, int problemIndex) {
        return getHomeworkBean(false, type, pageIndex, typeIndex, problemIndex);
    }

    /**
     * @param correct      {@link HomeworkBean#correct}
     * @param pageIndex    {@link HomeworkBean#pageIndex}
     * @param typeIndex    {@link HomeworkBean#typeIndex}
     * @param problemIndex {@link HomeworkBean#problemIndex}
     * @return 作业类
     */
    private static HomeworkBean getHomeworkBean(boolean correct, String type, int pageIndex, int typeIndex, int problemIndex) {
        HomeworkBean homeworkBean = new HomeworkBean();
        homeworkBean.setType(type);
        homeworkBean.setPageIndex(pageIndex);
        homeworkBean.setTypeIndex(typeIndex);
        homeworkBean.setProblemIndex(problemIndex);
        homeworkBean.setCorrect(correct);
        return homeworkBean;
    }

    /**
     * 填入问题答案
     *
     * @param stuAnswer       学生答案
     * @param indexAnswer     学生答案位置（多个填空，暂未用）
     * @param homeworkInfBean 作业
     * @param type            试卷类型
     * @param pageIndex       第几个试卷
     * @param typeIndex       第几类
     * @param problemIndex    第几个
     */
    public static void setProblemAnswer(HomeworkBean bean, String stuAnswer, int indexAnswer, HomeworkInfBean.DataBean homeworkInfBean, String type, int pageIndex, int typeIndex, int problemIndex) {
        String optionName;
        bean.setComplete(!TextUtils.isEmpty(stuAnswer));
        switch (type) {
            case ONLINE:
                List<HomeworkInfBean.DataBean.OnLineBean> onLine = homeworkInfBean.getOnLine();
                HomeworkInfBean.DataBean.OnLineBean onLineBean = onLine.get(pageIndex);
                List<HomeworkInfBean.DataBean.OnLineBean.NextBean> next = onLineBean.getNext();
                HomeworkInfBean.DataBean.OnLineBean.NextBean nextBean = next.get(problemIndex);
                optionName = nextBean.getOptionName();
            {
                switch (optionName) {
                    case "fill":
                        nextBean.setStuAnswer(dealWithAnswerList(stuAnswer));
                        break;
                    case "subjective":
                        nextBean.setStuAnswer(addImgAnswer(stuAnswer));
                        break;
                    default:
                        nextBean.setStuAnswer(stuAnswer);
                        if (isSelect(optionName)) {
                            bean.setCorrect(true);
                            bean.setResult(isRight((String) nextBean.getAnswer(), stuAnswer) ? 1 : 0);
                        }
                }
            }
            break;
            case EXAM:
                HomeworkInfBean.DataBean.ExamPaperBean.ExamBean.NextBeanX nextBeanX = homeworkInfBean.getExamPaper().get(pageIndex).getExam().get(typeIndex).getNext().get(problemIndex);
                optionName = nextBeanX.getOptionName();
            {
                switch (optionName) {
                    case "fill":
                        nextBeanX.setStuAnswer(dealWithAnswerList(stuAnswer));
                        break;
                    case "subjective":
                        nextBeanX.setStuAnswer(addImgAnswer(stuAnswer));
                        break;
                    default:
                        nextBeanX.setStuAnswer(stuAnswer);
                        if (isSelect(optionName)) {
                            bean.setCorrect(true);
                            bean.setResult(isRight((String) nextBeanX.getAnswer(), stuAnswer) ? 1 : 0);
                        }
                }
            }
            break;
            case VIDEO://没有主观题
                HomeworkInfBean.DataBean.VideoBean.Exam exam = homeworkInfBean.getVideo().get(pageIndex).getExam().get(problemIndex);
                optionName = exam.getOptionName();
            {
                exam.setStuAnswer(stuAnswer);
                if (isSelect(optionName)) {
                    bean.setCorrect(true);
                    bean.setResult(isRight((String) exam.getAnswer(), stuAnswer) ? 1 : 0);
                }
            }
            break;
            default:
        }

    }

    /**
     * 填空题答题
     *
     * @param answer
     * @return
     */
    private static List<String> dealWithAnswerList(String answer) {
        ArrayList<String> strings = new ArrayList<>(0);
        strings.add(answer);
        return strings;
    }

    /**
     * 图片链接添加img网页便签
     *
     * @param answer
     * @return String
     */
    public static String addImgAnswer(String answer) {
        String s;
        if (TextUtils.isEmpty(answer)) {
            s = "";
        } else {
            if (answer.trim().indexOf("<img") == 0) {
                return answer;
            }
            if (answer.indexOf("http") == 0) {
                s = "<img src=\"" + answer + "\"/>";
            } else {
                s = answer;
            }
        }
        return s;
    }

    /**
     * 获取一道题的内容
     *
     * @param homeworkInfBean 全部试题
     * @param homeworkInfBean 作业
     * @param type            试卷类型
     * @param pageIndex       第几个试卷
     * @param typeIndex       第几类
     * @param problemIndex    第几个
     * @return 一个试题内容（json）
     */
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
        return getHomeworkBean(homeworkInfBean, false);
    }

    /**
     * @param homeworkInfBean 全部试题
     * @param tag             是否已经完成
     * @return
     */
    public static List<HomeworkBean> getHomeworkBean(HomeworkInfBean.DataBean homeworkInfBean, boolean tag) {
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
                        if (null != nextBean) {
                            String optionName = nextBean.getOptionName();
                            homeworkBean.setProblemId("5___0___" + nextBean.getExamId());
                            if (isSelect(optionName)) {
                                homeworkBean.setCorrect(true);
                                homeworkBean.setObjective(true);
                                String stuAnswer = (String) nextBean.getStuAnswer();
                                homeworkBean.setComplete(!TextUtils.isEmpty(stuAnswer));
                                homeworkBean.setResult(isRight((String) nextBean.getAnswer(), stuAnswer) ? 1 : 0);
                            } else {
                                homeworkBean.setCorrect(false);
                                homeworkBean.setObjective(false);
                                homeworkBean.setComplete(!isEmpty(nextBean.getStuAnswer()));
                                if (tag) {
                                    homeworkBean.setResult(getResult(nextBean.getStatus()));
                                }
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
                                    homeworkBean.setProblemId("6___" + nextBeanX.getPaperId() + "___" + nextBeanX.getExamId());
                                    if (isSelect(optionName)) {
                                        homeworkBean.setCorrect(true);
                                        homeworkBean.setObjective(true);
                                        String stuAnswer = (String) nextBeanX.getStuAnswer();
                                        homeworkBean.setComplete(!TextUtils.isEmpty(stuAnswer));
                                        homeworkBean.setResult(isRight((String) nextBeanX.getAnswer(), stuAnswer) ? 1 : 0);
                                    } else {
                                        homeworkBean.setCorrect(false);
                                        homeworkBean.setObjective(false);
                                        homeworkBean.setComplete(!isEmpty(nextBeanX.getStuAnswer()));
                                        if (tag) {
                                            homeworkBean.setResult(getResult(nextBeanX.getStatus()));
                                        }
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
                        homeworkBean.setProblemId("8___" + exam1.getVideoId() + "___" + exam1.getExamId());
                        if (isSelect(optionName)) {
                            homeworkBean.setCorrect(true);
                            homeworkBean.setObjective(true);
                            String stuAnswer = exam1.getStuAnswer();
                            homeworkBean.setComplete(!TextUtils.isEmpty(stuAnswer));
                            homeworkBean.setResult(isRight(exam1.getAnswer(), stuAnswer) ? 1 : 0);
                        } else {
                            homeworkBean.setCorrect(false);
                            homeworkBean.setObjective(false);
                            homeworkBean.setComplete(!isEmpty(exam1.getStuAnswer()));
                            if (tag) {
                                homeworkBean.setResult(getResult(exam1.getStatus()));
                            }
                        }
                    }
                    list.add(homeworkBean);
                }
            }
        }
        return list;
    }

    /**
     * 集合是否有效
     *
     * @param list 集合
     * @return 是否有效
     */
    public static boolean isEffictive(List list) {
        return null != list && list.size() > 0;
    }

    /**
     * 是否为选择、多选、判断
     *
     * @param name 题目类型
     * @return boolean
     */
    public static boolean isSelect(String name) {
        if (null != name && ("check".equals(name) || "radio".equals(name) || "judge".equals(name))) {
            return true;
        }
        return false;
    }

    /**
     * 是否正确
     *
     * @param answer    正确答案
     * @param stuAnswer 学生答案
     * @return boolean
     */
    public static boolean isRight(String answer, String stuAnswer) {
        Log.e("TAG", "isRight: " + answer + ", " + stuAnswer);
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

    /**
     * 作业是否已做完
     *
     * @param stuAnswer
     * @return true ->未做完，false->已做完
     */
    public static boolean isEmpty(Object stuAnswer) {
        if (null == stuAnswer) {
            return true;
        }
        if (stuAnswer instanceof String) {
            return ((String) stuAnswer).length() == 0;
        }
        if (stuAnswer instanceof List) {//填空题，只要有一个空未填，即未做完
            List stuAnswer1 = (List) stuAnswer;
            if (stuAnswer1.size() == 0) {
                return true;
            } else {
                for (int i = 0; i < stuAnswer1.size(); i++) {
                    Object o = stuAnswer1.get(i);//字符串，其它不考虑
                    if (o == null || TextUtils.isEmpty(o.toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param statue 0未批阅1正确2错误3半对半错
     * @return 0 错误，1正确，2 未知，3 半对
     */
    private static int getResult(Object statue) {
        int parseInt = 0;
        try {
            parseInt = Integer.parseInt(statue + "");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        switch (parseInt) {
            case 0:
                return 2;
            case 1:
                return 1;
            case 2:
                return 0;
            case 3:
                return 3;
            default:
                return 2;
        }
    }
}
