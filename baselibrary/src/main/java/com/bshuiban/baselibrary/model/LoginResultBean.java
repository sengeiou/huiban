package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/4/26.<br/>
 * describe：登录结果
 */
public class LoginResultBean extends ResultBean {
    private Data data;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public class Data {
        private int userId;

        private int userType;

        private String realName;

        private String icoPath;

        private int schoolId;

        private String schoolName;

        private List<Integer> classId;

        private int gradeId;

        private String otherId;

        private int vipDays;

        private List<String> className;

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getUserId() {
            return this.userId;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public int getUserType() {
            return this.userType;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRealName() {
            return this.realName;
        }

        public void setIcoPath(String icoPath) {
            this.icoPath = icoPath;
        }

        public String getIcoPath() {
            return this.icoPath;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public int getSchoolId() {
            return this.schoolId;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getSchoolName() {
            return this.schoolName;
        }

        public void setClassId(List<Integer> classId) {
            this.classId = classId;
        }

        public List<Integer> getClassId() {
            return this.classId;
        }

        public void setGradeId(int gradeId) {
            this.gradeId = gradeId;
        }

        public int getGradeId() {
            return this.gradeId;
        }

        public void setOtherId(String otherId) {
            this.otherId = otherId;
        }

        public String getOtherId() {
            return this.otherId;
        }

        public void setVipDays(int vipDays) {
            this.vipDays = vipDays;
        }

        public int getVipDays() {
            return this.vipDays;
        }

        public void setClassName(List<String> className) {
            this.className = className;
        }

        public List<String> getClassName() {
            return this.className;
        }
    }
}
