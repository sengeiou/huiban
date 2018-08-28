package com.bshuiban.teacher.model;

import com.bshuiban.baselibrary.model.ResultBean;

import java.util.List;

/**
 * Created by xinheng on 2018/8/9.<br/>
 * describe：
 */
public class TeacherStatisticalChartBean extends ResultBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * classId : 3000221
         * className : 2班
         * monthArr : [{"day":1,"rate":0},{"day":2,"rate":0},{"day":3,"rate":0},{"day":4,"rate":0},{"day":5,"rate":0},{"day":6,"rate":0},{"day":7,"rate":40},{"day":8,"rate":50},{"day":9,"rate":0},{"day":10,"rate":0},{"day":11,"rate":0},{"day":12,"rate":0},{"day":13,"rate":0},{"day":14,"rate":0},{"day":15,"rate":0},{"day":16,"rate":0},{"day":17,"rate":0},{"day":18,"rate":0},{"day":19,"rate":0},{"day":20,"rate":0},{"day":21,"rate":0},{"day":22,"rate":0},{"day":23,"rate":0},{"day":24,"rate":0},{"day":25,"rate":0},{"day":26,"rate":0},{"day":27,"rate":0},{"day":28,"rate":0},{"day":29,"rate":0},{"day":30,"rate":0},{"day":31,"rate":0}]
         */

        private int classId;
        private String className;
        private List<MonthArrBean> monthArr;

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

        public List<MonthArrBean> getMonthArr() {
            return monthArr;
        }

        public void setMonthArr(List<MonthArrBean> monthArr) {
            this.monthArr = monthArr;
        }

        public static class MonthArrBean {
            /**
             * day : 1
             * rate : 0
             */

            private int day;
            private float rate;

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public float getRate() {
                return rate;
            }

            public void setRate(float rate) {
                this.rate = rate;
            }
        }
    }
}
