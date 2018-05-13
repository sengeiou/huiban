package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/5/10.<br/>
 * describe：
 */
public class ClassScheduleBean extends ResultBean {
    private List<List<DataBean>> data;

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3004048
         * classId : 3000153
         * semesterId : 3000120
         * week : 1
         * num : 1
         * subjectId : 0
         * teacherId : 0
         * beginTime : 08:20:00
         * endTime : 09:00:00
         * subjectName :
         * teacherName :
         */

        private int id;
        private int classId;
        private int semesterId;
        private int week;
        private int num;
        private int subjectId;
        private int teacherId;
        private String beginTime;
        private String endTime;
        private String subjectName;
        private String teacherName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getClassId() {
            return classId;
        }

        public void setClassId(int classId) {
            this.classId = classId;
        }

        public int getSemesterId() {
            return semesterId;
        }

        public void setSemesterId(int semesterId) {
            this.semesterId = semesterId;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }
    }
}
