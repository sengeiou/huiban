package com.bshuiban.baselibrary.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：科目
 */
public class SubjectBean extends ResultBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        //Comparator<DataBean> comparator= (o1, o2) -> o1.getId()<o2.getId()?-1:1;
        //Collections.sort(data,comparator);
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 24
         * subjectName : 能力培养
         */

        private int id;
        private String subjectName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }
    }
}
