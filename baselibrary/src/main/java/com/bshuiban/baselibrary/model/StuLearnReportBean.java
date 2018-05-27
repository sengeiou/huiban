package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xin_heng on 2017/10/25 0025.
 */

public class StuLearnReportBean extends ResultBean{
    /**
     * code : 2002
     * msg : success
     * data : {"rank":[{"subjectId":1,"subjectName":"语文","rate":"0","crank":"0","progress":0,"grank":"0","color":"#ff7777;"},{"subjectId":2,"subjectName":"数学","rate":"0","crank":"0","progress":0,"grank":"0","color":"#77baff;"},{"subjectId":5,"subjectName":"化学","rate":"0","crank":"0","progress":0,"grank":"0","color":"#ff7ef7;"}],"contrast":[{"subjectId":1,"subjectName":"语文","rate":"0","classAvg":"0","classTop":"0","gradeAvg":"0","gradeTop":"0"},{"subjectId":2,"subjectName":"数学","rate":"0","classAvg":"0","classTop":"0","gradeAvg":"0","gradeTop":"0"},{"subjectId":5,"subjectName":"化学","rate":"0","classAvg":"0","classTop":"0","gradeAvg":"0","gradeTop":"0"}],"exceed":[{"subjectId":1,"subjectName":"语文","per":0,"color":"#ff7777;"},{"subjectId":2,"subjectName":"数学","per":0,"color":"#77baff;"},{"subjectId":5,"subjectName":"化学","per":0,"color":"#ff7ef7;"}]}
     */
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<RankBean> rank;
        private List<ContrastBean> contrast;
        private List<ExceedBean> exceed;

        public List<RankBean> getRank() {
            return rank;
        }

        public void setRank(List<RankBean> rank) {
            this.rank = rank;
        }

        public List<ContrastBean> getContrast() {
            return contrast;
        }

        public void setContrast(List<ContrastBean> contrast) {
            this.contrast = contrast;
        }

        public List<ExceedBean> getExceed() {
            return exceed;
        }

        public void setExceed(List<ExceedBean> exceed) {
            this.exceed = exceed;
        }

        public static class RankBean {
            /**
             * subjectId : 1
             * subjectName : 语文
             * rate : 0
             * crank : 0
             * progress : 0
             * grank : 0
             * color : #ff7777;
             */

            private int subjectId;
            private String subjectName;
            private String rate;
            private String crank;
            private int progress;
            private String grank;
            private String color;

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

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }

        public static class ContrastBean {
            /**
             * subjectId : 1
             * subjectName : 语文
             * rate : 0
             * classAvg : 0
             * classTop : 0
             * gradeAvg : 0
             * gradeTop : 0
             */

            private int subjectId;
            private String subjectName;
            private String rate;
            private String classAvg;
            private String classTop;
            private String gradeAvg;
            private String gradeTop;

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

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
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

        public static class ExceedBean {
            /**
             * subjectId : 1
             * subjectName : 语文
             * per : 0
             * color : #ff7777;
             */

            private int subjectId;
            private String subjectName;
            private float per;
            private float rate;
            private String color;

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

            public float getPer() {
                return per;
            }

            public void setPer(float per) {
                this.per = per;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }
}
