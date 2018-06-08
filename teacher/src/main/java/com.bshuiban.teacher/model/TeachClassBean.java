package com.bshuiban.teacher.model;

import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.ResultBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xinheng on 2018/6/4.<br/>
 * describe：
 */
public class TeachClassBean extends ResultBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3001157
         * classId : 3000153
         * teacherId : 2030219
         * isHeader : 1
         * subjectId : 4
         * addTime : 1527229333
         * className : 八3班
         * gradeId : 8
         */
        private int id;
        private int classId;
        private int teacherId;
        private int isHeader;
        private int subjectId;
        private int addTime;
        private String className;
        private int gradeId;

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

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public int getIsHeader() {
            return isHeader;
        }

        public void setIsHeader(int isHeader) {
            this.isHeader = isHeader;
        }

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public int getAddTime() {
            return addTime;
        }

        public void setAddTime(int addTime) {
            this.addTime = addTime;
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

        @Override
        public String toString() {
            return className;
        }
    }
    public List<TeachClassBean.DataBean> getClassList(int gradeId) {
        List<TeachClassBean.DataBean> list = new ArrayList<>();
        if(HomeworkBean.isEffictive(data)){
            Iterator<DataBean> iterator = data.iterator();
            while (iterator.hasNext()){
                DataBean next = iterator.next();
                if(null!=next&&gradeId==next.getGradeId()){
                    list.add(next);
                }
            }
        }
        return list;
    }
}
