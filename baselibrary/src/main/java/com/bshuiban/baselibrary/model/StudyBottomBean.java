package com.bshuiban.baselibrary.model;

/**
 * Created by xinheng on 2018/5/22.<br/>
 * describe：
 */
public class StudyBottomBean extends ResultBean {
    /**
     * data : {"videoCnt":"0","length":"0.0","answerCnt":"0","examCnt":"24"}
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
         * videoCnt : 0
         * length : 0.0
         * answerCnt : 0
         * examCnt : 24
         */

        private String videoCnt;////学习视频个数
        private String length;//学习时长
        private String answerCnt;//参与答疑次数
        private String examCnt;//完成测试题数

        public String getVideoCnt() {
            return videoCnt;
        }

        public void setVideoCnt(String videoCnt) {
            this.videoCnt = videoCnt;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getAnswerCnt() {
            return answerCnt;
        }

        public void setAnswerCnt(String answerCnt) {
            this.answerCnt = answerCnt;
        }

        public String getExamCnt() {
            return examCnt;
        }

        public void setExamCnt(String examCnt) {
            this.examCnt = examCnt;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "videoCnt='" + videoCnt + '\'' +
                    ", length='" + length + '\'' +
                    ", answerCnt='" + answerCnt + '\'' +
                    ", examCnt='" + examCnt + '\'' +
                    '}';
        }
    }
}
