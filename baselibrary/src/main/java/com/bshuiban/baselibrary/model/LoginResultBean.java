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
        private String userId="2030246";
        private String passWord;
        private String mobile;
        private int userType;

        private String realName;

        private String icoPath;

        private String schoolId;

        private String schoolName;

        private List<String> classId;
        private String classId1;

        private String gradeId;

        private String otherId;

        private int vipDays;

        private List<String> className;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return this.userId;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
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

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolId() {
            return this.schoolId;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getSchoolName() {
            return this.schoolName;
        }

        public void setClassId(List<String> classId) {
            this.classId = classId;
            if(null!=classId&&classId.size()>0){
                classId1=classId.get(0);
            }
        }

        public String getClassId1() {
            if(null==classId1){
                if(null!=classId&&classId.size()>0){
                    classId1=classId.get(0);
                }
            }
            return classId1;
        }

        public List<String> getClassId() {
            return this.classId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public String getGradeId() {
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

        @Override
        public String toString() {
            return "Data{" +
                    "userId='" + userId + '\'' +
                    ", passWord='" + passWord + '\'' +
                    ", userType=" + userType +
                    ", realName='" + realName + '\'' +
                    ", icoPath='" + icoPath + '\'' +
                    ", schoolId='" + schoolId + '\'' +
                    ", schoolName='" + schoolName + '\'' +
                    ", classId=" + classId +
                    ", classId1='" + classId1 + '\'' +
                    ", gradeId='" + gradeId + '\'' +
                    ", otherId='" + otherId + '\'' +
                    ", vipDays=" + vipDays +
                    ", className=" + className +
                    '}';
        }
    }
}
