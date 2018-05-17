package com.bshuiban.baselibrary.model;

/**
 * Created by xinheng on 2018/5/15.<br/>
 * describe：
 */
public class User {

    private LoginResultBean.Data data;
    private static User user;
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
        return data.getUserId();
    }
    public LoginResultBean.Data getUserData(){
        return data;
    }

    public void setData(LoginResultBean.Data data) {
        this.data = data;
    }
    public String getGradeId() { return data.getGradeId();}
    public String getSchoolId() { return data.getSchoolId();}
}
