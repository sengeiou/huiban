package com.bshuiban.baselibrary.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：作业列表
 */
public class HomeworkListData {
    //[{"title":"exam1","index":0},{"title":"exam2","index":1},{"title":"exam3","index":2},{"title":"onLine","index":3}]
    private String title;
    private int index;
    private String type;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public static List<HomeworkListData> getHomeworkInfList(HomeworkInfBean.DataBean homeworkInfBean){
        if(null==homeworkInfBean){
            return null;
        }
        List<HomeworkListData> list = new ArrayList<>();
        //HomeworkInfBean.DataBean homeworkInfBean = User.getInstance().getHomeworkInfBean();
        List<HomeworkInfBean.DataBean.OnLineBean> onLine = homeworkInfBean.getOnLine();
        List<HomeworkInfBean.DataBean.ExamPaperBean> examPaper = homeworkInfBean.getExamPaper();
        List<HomeworkInfBean.DataBean.VideoBean> video = homeworkInfBean.getVideo();
        if (isEffictive(onLine)) {
            HomeworkListData data = new HomeworkListData();
            data.setTitle("试题");
            data.setIndex(-1);
            data.setType("onLine");
            list.add(data);
        }
        if (null != examPaper && examPaper.size() > 0) {
            for (int i = 0; i < examPaper.size(); i++) {
                HomeworkInfBean.DataBean.ExamPaperBean examPaperBean = examPaper.get(i);
                HomeworkListData data = new HomeworkListData();
                data.setIndex(i);
                data.setTitle(examPaperBean.getPaperName());
                data.setType("examPaper");
                list.add(data);
            }
        }
        if (isEffictive(video)) {
            for (int i = 0; i < video.size(); i++) {
                HomeworkInfBean.DataBean.VideoBean videoBean = video.get(i);
                HomeworkListData data = new HomeworkListData();
                data.setIndex(i);
                data.setType("video");
                data.setTitle(videoBean.getVideoName());
                list.add(data);
            }
        }
        return list;
    }
    private static boolean isEffictive(List list) {
        return null != list && list.size() > 0;
    }

    /**
     * 获取一个试卷
     * @param type
     * @param index
     * @return 试卷内容
     */
    public static String getHomeworkInfJson(String type,int index){
        Gson gson=new Gson();
        HomeworkInfBean.DataBean homeworkInfBean = User.getInstance().getHomeworkInfBean();
        if(null==homeworkInfBean){
            return null;
        }
        String json = null;
        switch (type) {
            case "onLine":
                List<HomeworkInfBean.DataBean.OnLineBean> onLine = homeworkInfBean.getOnLine();
                json = gson.toJson(onLine);
                break;
            case "examPaper":
                List<HomeworkInfBean.DataBean.ExamPaperBean> examPaper = homeworkInfBean.getExamPaper();
                HomeworkInfBean.DataBean.ExamPaperBean examPaperBean = examPaper.get(index);
                json = gson.toJson(examPaperBean);
                break;
            case "video":
                List<HomeworkInfBean.DataBean.VideoBean> video = homeworkInfBean.getVideo();
                HomeworkInfBean.DataBean.VideoBean videoBean = video.get(index);
                json = gson.toJson(videoBean);
                break;
            default:
                json = null;
        }
        return json;
    }
}
