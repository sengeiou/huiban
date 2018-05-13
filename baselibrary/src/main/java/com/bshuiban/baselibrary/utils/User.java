package com.bshuiban.baselibrary.utils;

/**
 * Created by xinheng on 2018/5/8.<br/>
 * describe：用户信息
 */
public final class User {
    /**
     * 用户id
     */
    private int userId;
    /**
     * 用户角色1学生、2老师、3管理者、4家长
     */
    private int userType;
    /**
     * vip剩余有效天数
     */
    private int vipDays;
    /**
     * 用户头像http://........
     */
    private String icoPath;

    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 学校id
     */
    private int schoolId;
    /**
     * 班级id，多个班级时逗号间隔
     */
    private String classId;
    /**
     * 年级id
     */
    private int gradeId;

    public User(int userId, int userType, int vipDays, String icoPath, String schoolName, String userName, int schoolId, String classId, int gradeId) {
        this.userId = userId;
        this.userType = userType;
        this.vipDays = vipDays;
        this.icoPath = icoPath;
        this.schoolName = schoolName;
        this.userName = userName;
        this.schoolId = schoolId;
        this.classId = classId;
        this.gradeId = gradeId;
    }
    
}
