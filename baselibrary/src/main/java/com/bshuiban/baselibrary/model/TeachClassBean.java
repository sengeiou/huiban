package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/6/14.<br/>
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

    public static class DataBean{
        private String classId;	//int,班级id
        private String  className;	//string,班级名
        private String gradeId;	//年级id

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }
    }
}
