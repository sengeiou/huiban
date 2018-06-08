package com.bshuiban.teacher.model;

import com.bshuiban.baselibrary.model.ResultBean;

import java.util.List;

/**
 * Created by xinheng on 2018/6/4.<br/>
 * describe：学校的科目列表
 */
public class SubjectListBean extends ResultBean {
    private int cnt;//数量
    private List<Data> data;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data{
        private int subjectId; //科目 id
        private String subjectName; //

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        @Override
        public String toString() {
            return subjectName;
        }
    }
}
