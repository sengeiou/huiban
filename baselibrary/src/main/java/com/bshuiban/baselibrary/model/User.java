package com.bshuiban.baselibrary.model;

import android.os.Environment;
import android.util.Log;

import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;

import java.util.List;

/**
 * Created by xinheng on 2018/5/15.<br/>
 * describe：
 */
public class User {
    public static final String path= Environment.getExternalStorageDirectory().getAbsolutePath() + "/HuiBan/";
    private SubjectBean subjectBean;
    private LoginResultBean.Data data;
    private static User user;
    private HomeworkInfBean.DataBean homeworkInfBean;
    private Homework homework;
    private String userName;
    private String imgHeadUrl="";
    public int getUserType() {
        return data.getUserType();
    }

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
        return data.getClassId1();
    }

    public String getUserId(){
        if(null==data){
            Log.e("TAG", "getUserId: 异常！！！" );
            return "2030246";
        }
        return data.getUserId();
    }

    public String getImgHeadUrl() {
        return imgHeadUrl;
    }

    public void setImgHeadUrl(String imgHeadUrl) {
        this.imgHeadUrl = imgHeadUrl;
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
        return data.getRealName();
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

    /**
     * 获取班级列表，逗号拼接
     * @return
     */
    public String getClassIdArrayString() {
        List<String> classIds = data.getClassId();
        StringBuffer stringBuffer=new StringBuffer();
        if(classIds!=null&&classIds.size()>0){
            for (int i = 0; i < classIds.size(); i++) {//3
                String s = classIds.get(i);
                stringBuffer.append(s);
                if(i<classIds.size()-1) {
                    stringBuffer.append(",");
                }
            }
        }
        return stringBuffer.toString();
    }
    public boolean isTeacher(){
        int userType = User.getInstance().getUserData().getUserType();
        if(userType!=1&&userType!=4){
            return true;
        }
        return false;
    }
    public boolean isParents(){
        int userType = User.getInstance().getUserData().getUserType();
        if(userType==4){
            return true;
        }
        return false;
    }
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
