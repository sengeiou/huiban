package com.bshuiban.baselibrary.present;

import android.text.TextUtils;

import com.bshuiban.baselibrary.contract.HomeworkResultContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ExamAnswerBean;
import com.bshuiban.baselibrary.model.HomeWorkCommitBean;
import com.bshuiban.baselibrary.model.Homework;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.PrepareVideoAnswerBean;
import com.bshuiban.baselibrary.model.PrepareWorkAnswerBean;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.model.VideoAnswerBean;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：
 */
public class HomeworkResultPresent extends BasePresent<HomeworkResultContract.View> implements HomeworkResultContract.Present {
    private HomeWorkCommitBean homeWorkCommitBean;
    private Gson gson=new Gson();
    public HomeworkResultPresent(HomeworkResultContract.View view,boolean complete) {
        super(view);
        if(!complete) {
            homeWorkCommitBean = getHomeworkCommitJson();
        }
    }

    @Override
    public void commitService(int prepareId,int workId) {
        homeWorkCommitBean.setWorkId(workId);
        homeWorkCommitBean.setPrepareId(prepareId);
        askInternet("commitStuWorkAnswer", gson.toJson(homeWorkCommitBean), new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()){
                    view.commitSuccess();
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }

    @Override
    public void saveHomework(int prepareId,int workId) {
        homeWorkCommitBean.setWorkId(workId);
        homeWorkCommitBean.setPrepareId(prepareId);
        askInternet("saveStuWorkAnswer", gson.toJson(homeWorkCommitBean), new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()){
                    //view.commitSuccess();
                    view.fail("保存成功");
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }
    private static int toInt(String s){
        try {
            int hour = Integer.parseInt(s);
            return hour;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private static String getTime(float time){
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
    public static HomeWorkCommitBean getHomeworkCommitJson(){
        HomeworkInfBean.DataBean homeworkInfBean = User.getInstance().getHomeworkInfBean();
        /*Homework homework = User.getInstance().getHomework();
        if(null==homework){
            homework=new Homework();
            time = toInt(homeworkInfBean.getTimes());
            homework.setTime(getTime(time));
            homework.setTitle(homeworkInfBean.getTitle());
            User.getInstance().setHomework(homework);
        }else {
            String time1 = homework.getTime();// 11时2分1秒
            if(null==time1){
                time1=homeworkInfBean.getTimes();
                time=toInt(time1);
                homework.setTime(getTime(time));
            }else {
                int hIndex = time1.indexOf("时");
                String h = "", m = "", s = "";
                if (hIndex > -1) {
                    h = time1.substring(0, hIndex);
                }
                hIndex += 1;
                int mIndex = time1.indexOf("分");
                if (mIndex > -1) {
                    m = time1.substring(hIndex, mIndex);
                }
                mIndex += 1;
                int sIndex = time1.indexOf("秒");
                if (sIndex > -1) {
                    s = time1.substring(mIndex, sIndex);
                }
                int hour = toInt(h);
                int min = toInt(m);
                int socend = toInt(s);
                time = hour * 60 + min * 60 + socend;
            }
        }*/
        //List<Homework.Data> dataList=new ArrayList<>();//作业结果记录
        HomeWorkCommitBean homeWorkCommitBean=new HomeWorkCommitBean();
        homeWorkCommitBean.setClassId(User.getInstance().getClassId());
        homeWorkCommitBean.setPrepareId(-1);
        homeWorkCommitBean.setTimes(toInt(homeworkInfBean.getTimes()));
        homeWorkCommitBean.setUserId(User.getInstance().getUserId());
        //homeWorkCommitBean.setWorkId(workId);
        //homeWorkCommitBean.setPrepareId(prepareId);//列表接口提供

        List<HomeworkInfBean.DataBean.OnLineBean> onLine = homeworkInfBean.getOnLine();
        List<PrepareWorkAnswerBean> listOnline=new ArrayList<>();

        if(onLine!=null&&onLine.size()>0) {
            for (int i = 0; i < onLine.size(); i++) {
                List<HomeworkInfBean.DataBean.OnLineBean.NextBean> next = onLine.get(i).getNext();
                for (int j = 0; next!=null&&j < next.size(); j++) {
                    PrepareWorkAnswerBean bean1=new PrepareWorkAnswerBean();
                    HomeworkInfBean.DataBean.OnLineBean.NextBean bean = next.get(j);
                    if(null!=bean) {
                        bean1.setExamId(bean.getExamId());
                        bean1.setStuAnswer(bean.getStuAnswer());
                        bean1.setType(bean.getOptionName());
                        listOnline.add(bean1);
                        /*boolean empty = isEmpty(bean.getStuAnswer());
                        Homework.Data data11=new Homework.Data();
                        data11.setComplete(!empty);
                        if(empty&&isSelect(bean.getOptionName())){
                            data11.setResult(bean.getAnswer().equals(bean.getStuAnswer())?1:0);
                        }else{
                            data11.setResult(2);
                        }
                        dataList.add(data11);*/
                    }

                }
                homeWorkCommitBean.setExamAnswer(listOnline);
            }
        }
        List<HomeworkInfBean.DataBean.ExamPaperBean> examPaper = homeworkInfBean.getExamPaper();
        List<ExamAnswerBean> listExamPaper=new ArrayList<>();
        if(examPaper!=null&&examPaper.size()>0){
            for (int i = 0; i < examPaper.size(); i++) {//一个试卷
                ExamAnswerBean examAnswerBean=new ExamAnswerBean();
                HomeworkInfBean.DataBean.ExamPaperBean examPaperBean = examPaper.get(i);
                int paperId = examPaperBean.getPaperId();
                examAnswerBean.setExamPaperId(paperId);
                //一类题
                List<HomeworkInfBean.DataBean.ExamPaperBean.ExamBean> exam = examPaperBean.getExam();
                ArrayList<PrepareWorkAnswerBean> prepareWorkAnswerBeans=new ArrayList<>();
                for (int j = 0; exam!=null&&j < exam.size(); j++) {
                    HomeworkInfBean.DataBean.ExamPaperBean.ExamBean examBean = exam.get(j);
                    List<HomeworkInfBean.DataBean.ExamPaperBean.ExamBean.NextBeanX> next = examBean.getNext();
                    for (int k = 0; next!=null&&k < next.size(); k++) {
                        PrepareWorkAnswerBean answerBean=new PrepareWorkAnswerBean();
                        HomeworkInfBean.DataBean.ExamPaperBean.ExamBean.NextBeanX nextBeanX = next.get(k);
                        if(null!=nextBeanX) {
                            answerBean.setExamId(nextBeanX.getExamId());
                            answerBean.setStuAnswer(nextBeanX.getStuAnswer());
                            answerBean.setType(nextBeanX.getOptionName());
                            prepareWorkAnswerBeans.add(answerBean);
                            /*boolean empty = isEmpty(nextBeanX.getStuAnswer());
                            Homework.Data data11 = new Homework.Data();
                            data11.setComplete(!empty);
                            if (empty && isSelect(nextBeanX.getOptionName())) {
                                data11.setResult(nextBeanX.getAnswer().equals(nextBeanX.getStuAnswer()) ? 1 : 0);
                            } else {
                                data11.setResult(2);
                            }
                            dataList.add(data11);*/
                        }
                    }
                }
                examAnswerBean.setExamPaperAnswer(prepareWorkAnswerBeans);
                listExamPaper.add(examAnswerBean);
            }
        }

        List<HomeworkInfBean.DataBean.VideoBean> video = homeworkInfBean.getVideo();
        List<PrepareVideoAnswerBean> prepareVideoAnswerBeans=new ArrayList<>();
        for (int i = 0; video!=null && i < video.size(); i++) {
            PrepareVideoAnswerBean videoAnswerBean=new PrepareVideoAnswerBean();
            HomeworkInfBean.DataBean.VideoBean videoBean = video.get(i);
            List<HomeworkInfBean.DataBean.VideoBean.Exam> exam = videoBean.getExam();
            List<VideoAnswerBean> videoAnswerBeanList=new ArrayList<>();
            for (int j = 0; exam!=null&&j < exam.size(); j++) {
                HomeworkInfBean.DataBean.VideoBean.Exam exam1 = exam.get(i);
                if(null!=exam1) {
                    VideoAnswerBean videoAnswerBean1 = new VideoAnswerBean();
                    videoAnswerBean1.setExamId(exam1.getExamId());
                    videoAnswerBean1.setStuAnswer(exam1.getStuAnswer());
                    videoAnswerBean1.setType(exam1.getOptionName());
                    videoAnswerBeanList.add(videoAnswerBean1);
                    /*boolean empty = isEmpty(exam1.getStuAnswer());
                    Homework.Data data11 = new Homework.Data();
                    data11.setComplete(!empty);
                    if (empty && isSelect(exam1.getOptionName())) {
                        data11.setResult(exam1.getAnswer().equals(exam1.getStuAnswer()) ? 1 : 0);
                    } else {
                        data11.setResult(2);
                    }
                    dataList.add(data11);*/
                }
            }
            if(videoAnswerBeanList.size()>0){
                videoAnswerBean.setVideoId(exam.get(0).getVideoId());
            }
            videoAnswerBean.setVideoAns(videoAnswerBeanList);
            prepareVideoAnswerBeans.add(videoAnswerBean);
        }
        homeWorkCommitBean.setExamAnswer(listOnline);
        homeWorkCommitBean.setExamPaperAnswer(listExamPaper);
        homeWorkCommitBean.setVideoAnswer(prepareVideoAnswerBeans);
        //homework.setHomework(dataList);
//        return new Gson().toJson(homeWorkCommitBean);
        return homeWorkCommitBean;
    }
    private static boolean isEmpty(Object o){
        if(null==o){
            return true;
        }
        if(o instanceof List){
            return ((List) o).size()==0;
        }else if(o instanceof String){
            return TextUtils.isEmpty((String) o);
        }else{
            return true;
        }
    }
    private static boolean isSelect(String type) {
        if ("radio".equals(type) || "check".equals(type)) {
            return true;
        }
        return false;
    }
}
