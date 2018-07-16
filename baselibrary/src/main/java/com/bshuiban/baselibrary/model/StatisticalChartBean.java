package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：
 */
public class StatisticalChartBean extends ResultBean {
    /**
     * data : {"preList":[{"day":1,"mineRate":-1,"classRate":-1},{"day":2,"mineRate":-1,"classRate":-1},{"day":3,"mineRate":-1,"classRate":-1},{"day":4,"mineRate":-1,"classRate":-1},{"day":5,"mineRate":-1,"classRate":-1},{"day":6,"mineRate":-1,"classRate":-1},{"day":7,"mineRate":-1,"classRate":-1},{"day":8,"mineRate":-1,"classRate":-1},{"day":9,"mineRate":-1,"classRate":-1},{"day":10,"mineRate":-1,"classRate":-1},{"day":11,"mineRate":-1,"classRate":-1},{"day":12,"mineRate":-1,"classRate":-1},{"day":13,"mineRate":-1,"classRate":-1},{"day":14,"mineRate":-1,"classRate":-1},{"day":15,"mineRate":-1,"classRate":-1},{"day":16,"mineRate":"-1","classRate":50},{"day":17,"mineRate":"-1","classRate":0},{"day":18,"mineRate":-1,"classRate":-1},{"day":19,"mineRate":-1,"classRate":-1},{"day":20,"mineRate":-1,"classRate":-1},{"day":21,"mineRate":"33.33","classRate":35.42},{"day":22,"mineRate":-1,"classRate":-1},{"day":23,"mineRate":-1,"classRate":-1},{"day":24,"mineRate":-1,"classRate":-1},{"day":25,"mineRate":-1,"classRate":-1},{"day":26,"mineRate":-1,"classRate":-1},{"day":27,"mineRate":-1,"classRate":-1},{"day":28,"mineRate":-1,"classRate":-1},{"day":29,"mineRate":-1,"classRate":-1},{"day":30,"mineRate":-1,"classRate":-1},{"day":31,"mineRate":-1,"classRate":-1}],"inList":[{"day":1,"mineRate":-1,"classRate":-1},{"day":2,"mineRate":-1,"classRate":-1},{"day":3,"mineRate":-1,"classRate":-1},{"day":4,"mineRate":-1,"classRate":-1},{"day":5,"mineRate":-1,"classRate":-1},{"day":6,"mineRate":-1,"classRate":-1},{"day":7,"mineRate":-1,"classRate":-1},{"day":8,"mineRate":-1,"classRate":-1},{"day":9,"mineRate":-1,"classRate":-1},{"day":10,"mineRate":-1,"classRate":-1},{"day":11,"mineRate":-1,"classRate":-1},{"day":12,"mineRate":-1,"classRate":-1},{"day":13,"mineRate":-1,"classRate":-1},{"day":14,"mineRate":-1,"classRate":-1},{"day":15,"mineRate":-1,"classRate":-1},{"day":16,"mineRate":-1,"classRate":-1},{"day":17,"mineRate":-1,"classRate":-1},{"day":18,"mineRate":-1,"classRate":-1},{"day":19,"mineRate":-1,"classRate":-1},{"day":20,"mineRate":-1,"classRate":-1},{"day":21,"mineRate":-1,"classRate":-1},{"day":22,"mineRate":-1,"classRate":-1},{"day":23,"mineRate":-1,"classRate":-1},{"day":24,"mineRate":-1,"classRate":-1},{"day":25,"mineRate":-1,"classRate":-1},{"day":26,"mineRate":-1,"classRate":-1},{"day":27,"mineRate":-1,"classRate":-1},{"day":28,"mineRate":-1,"classRate":-1},{"day":29,"mineRate":-1,"classRate":-1},{"day":30,"mineRate":-1,"classRate":-1},{"day":31,"mineRate":-1,"classRate":-1}],"workList":[{"day":1,"mineRate":-1,"classRate":-1},{"day":2,"mineRate":-1,"classRate":-1},{"day":3,"mineRate":-1,"classRate":-1},{"day":4,"mineRate":-1,"classRate":-1},{"day":5,"mineRate":-1,"classRate":-1},{"day":6,"mineRate":-1,"classRate":-1},{"day":7,"mineRate":-1,"classRate":-1},{"day":8,"mineRate":-1,"classRate":-1},{"day":9,"mineRate":-1,"classRate":-1},{"day":10,"mineRate":-1,"classRate":-1},{"day":11,"mineRate":-1,"classRate":-1},{"day":12,"mineRate":-1,"classRate":-1},{"day":13,"mineRate":-1,"classRate":-1},{"day":14,"mineRate":-1,"classRate":-1},{"day":15,"mineRate":-1,"classRate":-1},{"day":16,"mineRate":"-1","classRate":0},{"day":17,"mineRate":-1,"classRate":-1},{"day":18,"mineRate":-1,"classRate":-1},{"day":19,"mineRate":-1,"classRate":-1},{"day":20,"mineRate":-1,"classRate":-1},{"day":21,"mineRate":-1,"classRate":-1},{"day":22,"mineRate":-1,"classRate":-1},{"day":23,"mineRate":-1,"classRate":-1},{"day":24,"mineRate":-1,"classRate":-1},{"day":25,"mineRate":-1,"classRate":-1},{"day":26,"mineRate":-1,"classRate":-1},{"day":27,"mineRate":-1,"classRate":-1},{"day":28,"mineRate":-1,"classRate":-1},{"day":29,"mineRate":-1,"classRate":-1},{"day":30,"mineRate":-1,"classRate":-1},{"day":31,"mineRate":-1,"classRate":-1}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> preList;
        private List<ListBean> inList;
        private List<ListBean> workList;

        public List<ListBean> getPreList() {
            return preList;
        }

        public void setPreList(List<ListBean> preList) {
            this.preList = preList;
        }

        public List<ListBean> getInList() {
            return inList;
        }

        public void setInList(List<ListBean> inList) {
            this.inList = inList;
        }

        public List<ListBean> getWorkList() {
            return workList;
        }

        public void setWorkList(List<ListBean> workList) {
            this.workList = workList;
        }

        public static class ListBean {
            /**
             * day : 1
             * mineRate : -1
             * classRate : -1
             */

            private int day;
            private float mineRate;
            private float classRate;

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public float getMineRate() {
                return mineRate;
            }

            public void setMineRate(float mineRate) {
                this.mineRate = mineRate;
            }

            public float getClassRate() {
                return classRate;
            }

            public void setClassRate(float classRate) {
                this.classRate = classRate;
            }
        }
    }
}
