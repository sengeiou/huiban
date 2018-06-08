package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：
 */
public class WeiClassSelectBean extends ResultBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * userId : 2030213
         * stageId : 2
         * subjectId : 2
         * versionId : 76
         * fasId : 19
         */

        private int id;
        private int userId;
        private int stageId;
        private int subjectId;
        private int versionId;
        private int fasId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getStageId() {
            return stageId;
        }

        public void setStageId(int stageId) {
            this.stageId = stageId;
        }

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public int getVersionId() {
            return versionId;
        }

        public void setVersionId(int versionId) {
            this.versionId = versionId;
        }

        public int getFasId() {
            return fasId;
        }

        public void setFasId(int fasId) {
            this.fasId = fasId;
        }
    }
}
