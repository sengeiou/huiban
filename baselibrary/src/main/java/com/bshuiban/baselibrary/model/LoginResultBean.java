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
    public static class Data {
        private String userId;
        private String parentsId;
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
        private List<StuArrBean> stuArr;
        private List<String> className;
        public void clean(){
            userId=null;
            parentsId=null;
            passWord=null;
            mobile=null;
            userType=-1;
            realName=null;
            icoPath=null;
            schoolId=null;
            schoolName=null;
            classId=null;
            classId1=null;
            gradeId=null;
            otherId=null;
            vipDays=0;
            stuArr=null;
            className=null;
        }
        public String getParentsId() {
            return parentsId;
        }

        public void setParentsId(String parentsId) {
            this.parentsId = parentsId;
        }

        public void setClassId1(String classId1) {
            this.classId1 = classId1;
        }

        public List<StuArrBean> getStuArr() {
            return stuArr;
        }

        public void setStuArr(List<StuArrBean> stuArr) {
            this.stuArr = stuArr;
        }

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
            if (null != classId && classId.size() > 0) {
                classId1 = classId.get(0);
            }
        }

        public String getClassId1() {
            if (null == classId1) {
                if (null != classId && classId.size() > 0) {
                    classId1 = classId.get(0);
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

        public static class StuArrBean {
            /**
             * stuId : 2030076
             * stuName : 沈陽
             * schoolId : 45
             * schoolName : 青岛实验初级中学
             * classId : 3000153
             * className : 八3班N
             * gradeId : 8
             * gradeName : 八年级
             */

            private String stuId;
            private String stuName;
            private int schoolId;
            private String schoolName;
            private int classId;
            private String className;
            private int gradeId;
            private String gradeName;

            public String getStuId() {
                return stuId;
            }

            public void setStuId(String stuId) {
                this.stuId = stuId;
            }

            public String getStuName() {
                return stuName;
            }

            public void setStuName(String stuName) {
                this.stuName = stuName;
            }

            public int getSchoolId() {
                return schoolId;
            }

            public void setSchoolId(int schoolId) {
                this.schoolId = schoolId;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public int getClassId() {
                return classId;
            }

            public void setClassId(int classId) {
                this.classId = classId;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public int getGradeId() {
                return gradeId;
            }

            public void setGradeId(int gradeId) {
                this.gradeId = gradeId;
            }

            public String getGradeName() {
                return gradeName;
            }

            public void setGradeName(String gradeName) {
                this.gradeName = gradeName;
            }
        }
    }
}
