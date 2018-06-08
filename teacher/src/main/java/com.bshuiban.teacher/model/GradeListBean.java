package com.bshuiban.teacher.model;

import android.text.TextUtils;

import com.bshuiban.baselibrary.model.Homework;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.ResultBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xinheng on 2018/6/4.<br/>
 * describe：年级列表
 */
public class GradeListBean extends ResultBean {
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
        private int gradeId; //年级id
        private String gradeName; //年级名

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

        @Override
        public String toString() {
            return gradeName;
        }
    }
    public String getGradeNameForList(int gradeId){
        if(HomeworkBean.isEffictive(data)){
            Iterator<Data> iterator = data.iterator();
            while (iterator.hasNext()){
                Data next = iterator.next();
                if(next!=null&&next.getGradeId()==gradeId){
                    return next.getGradeName();
                }
            }
        }
        return null;
    }
}
