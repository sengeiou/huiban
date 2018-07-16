package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.model.ResultBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/22.<br/>
 * describe：
 */
public class StudyReportBean extends ResultBean {
    /**
     * data : {"mine":{"rate":"29.17","crank":"8","progress":0,"grank":"6","classAvg":"35.69","classTop":"50","gradeAvg":"34.72","gradeTop":"50"},"knowList":[{"knowId":103971,"knowName":"等腰三角形","classRate":25,"mineRate":"-1"},{"knowId":3361,"knowName":"数与代数","classRate":25,"mineRate":"100"},{"knowId":104075,"knowName":"正方形","classRate":25,"mineRate":"100"},{"knowId":124240,"knowName":"常量与变量","classRate":3.13,"mineRate":"25"},{"knowId":124273,"knowName":"四边形","classRate":50,"mineRate":"50"},{"knowId":103354,"knowName":"反比例函数","classRate":0,"mineRate":"0"},{"knowId":3373,"knowName":"二次根式","classRate":0,"mineRate":"0"},{"knowId":3369,"knowName":"整式","classRate":0,"mineRate":"0"},{"knowId":124241,"knowName":"函数的概念","classRate":0,"mineRate":"0"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * mine : {"rate":"29.17","crank":"8","progress":0,"grank":"6","classAvg":"35.69","classTop":"50","gradeAvg":"34.72","gradeTop":"50"}
         * knowList : [{"knowId":103971,"knowName":"等腰三角形","classRate":25,"mineRate":"-1"},{"knowId":3361,"knowName":"数与代数","classRate":25,"mineRate":"100"},{"knowId":104075,"knowName":"正方形","classRate":25,"mineRate":"100"},{"knowId":124240,"knowName":"常量与变量","classRate":3.13,"mineRate":"25"},{"knowId":124273,"knowName":"四边形","classRate":50,"mineRate":"50"},{"knowId":103354,"knowName":"反比例函数","classRate":0,"mineRate":"0"},{"knowId":3373,"knowName":"二次根式","classRate":0,"mineRate":"0"},{"knowId":3369,"knowName":"整式","classRate":0,"mineRate":"0"},{"knowId":124241,"knowName":"函数的概念","classRate":0,"mineRate":"0"}]
         */

        private MineBean mine;
        private List<KnowListBean> knowList;

        public MineBean getMine() {
            return mine;
        }

        public void setMine(MineBean mine) {
            this.mine = mine;
        }

        public List<KnowListBean> getKnowList() {
            return knowList;
        }

        public void setKnowList(List<KnowListBean> knowList) {
            this.knowList = knowList;
        }

        public static class MineBean {
            /**
             * rate : 29.17
             * crank : 8
             * progress : 0
             * grank : 6
             * classAvg : 35.69
             * classTop : 50
             * gradeAvg : 34.72
             * gradeTop : 50
             */

            private String rate;//我的正确率
            /**
             * 班级排名
             */
            private String crank;
            /**
             * 进步名次
             */
            private int progress;
            private String grank;
            private String classAvg;
            private String classTop;
            private String gradeAvg;
            private String gradeTop;

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getCrank() {
                return crank;
            }

            public void setCrank(String crank) {
                this.crank = crank;
            }

            public int getProgress() {
                return progress;
            }

            public void setProgress(int progress) {
                this.progress = progress;
            }

            public String getGrank() {
                return grank;
            }

            public void setGrank(String grank) {
                this.grank = grank;
            }

            public String getClassAvg() {
                return classAvg;
            }

            public void setClassAvg(String classAvg) {
                this.classAvg = classAvg;
            }

            public String getClassTop() {
                return classTop;
            }

            public void setClassTop(String classTop) {
                this.classTop = classTop;
            }

            public String getGradeAvg() {
                return gradeAvg;
            }

            public void setGradeAvg(String gradeAvg) {
                this.gradeAvg = gradeAvg;
            }

            public String getGradeTop() {
                return gradeTop;
            }

            public void setGradeTop(String gradeTop) {
                this.gradeTop = gradeTop;
            }
        }

        public static class KnowListBean {
            /**
             * knowId : 103971
             * knowName : 等腰三角形
             * classRate : 25
             * mineRate : -1
             */

            private int knowId;
            private String knowName;
            private float classRate;
            private String mineRate;

            public int getKnowId() {
                return knowId;
            }

            public void setKnowId(int knowId) {
                this.knowId = knowId;
            }

            public String getKnowName() {
                return knowName;
            }

            public void setKnowName(String knowName) {
                this.knowName = knowName;
            }

            public float getClassRate() {
                return classRate;
            }

            public void setClassRate(float classRate) {
                this.classRate = classRate;
            }

            public String getMineRate() {
                return mineRate;
            }

            public void setMineRate(String mineRate) {
                this.mineRate = mineRate;
            }
        }
    }
}
