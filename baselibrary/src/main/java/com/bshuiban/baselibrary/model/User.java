package com.bshuiban.baselibrary.model;

import android.util.Log;

import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;

import java.util.List;

/**
 * Created by xinheng on 2018/5/15.<br/>
 * describe：
 */
public class User {

    private SubjectBean subjectBean;
    private LoginResultBean.Data data;
    private static User user;
    private HomeworkInfBean.DataBean homeworkInfBean;
    private Homework homework;
    private String userName;
    public static User getInstance() {
        if(null==user){
            synchronized (User.class){
                if(null==user){
                    user=new User();
                }
            }
        }
        return user;
    }
    public String getClassId(){
        if(null==data){
            Log.e("TAG", "getUserId: 异常！！！" );
            return "3000153";
        }
        return data.getClassId1();
    }

    public String getUserId(){
        if(null==data){
            Log.e("TAG", "getUserId: 异常！！！" );
            return "2030246";
        }
        return data.getUserId();
    }
    public LoginResultBean.Data getUserData(){
        return data;
    }
    public String getClassName(){
        List<String> className = data.getClassName();
        if(null!=className&&className.size()>0){
            return className.get(0);
        }
        return "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setData(LoginResultBean.Data data) {
        if(null==data){
            Log.e("TAG", "setData: 异常！！！" );
            return;
        }
        this.data = data;
    }
    public String getGradeId() { return data.getGradeId();}
    public String getSchoolId() { return data.getSchoolId();}

    public SubjectBean getSubjectBean() {
        return subjectBean;
    }

    public void setSubjectBean(SubjectBean subjectBean) {
        this.subjectBean = subjectBean;
    }

    public HomeworkInfBean.DataBean getHomeworkInfBean() {
        return homeworkInfBean;
    }

    public void setHomeworkInfBean(HomeworkInfBean.DataBean homeworkInfBean) {
        this.homeworkInfBean = homeworkInfBean;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }
}
