package com.bshuiban.teacher.model;

import com.bshuiban.baselibrary.model.ResultBean;

import java.util.List;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public class PrepareLessonBean extends ResultBean {
    /**
     * data : {"id":"3755","title":"第8章 运动和力","subjectName":"物理","versionName":"人教版","fasName":"八年级下册","chapName":"第8章 运动和力","serName":"","info":"物理 / 八年级下册 / 第8章 运动和力","classArr":[{"id":623,"workId":9108,"classId":3000153,"addtime":1527218420,"endTime":1527263999,"addTime":"2018-05-25 11:20","className":"八3班N","noReply":1}]}
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
         * id : 3755
         * title : 第8章 运动和力
         * subjectName : 物理
         * versionName : 人教版
         * fasName : 八年级下册
         * chapName : 第8章 运动和力
         * serName :
         * info : 物理 / 八年级下册 / 第8章 运动和力
         * classArr : [{"id":623,"workId":9108,"classId":3000153,"addtime":1527218420,"endTime":1527263999,"addTime":"2018-05-25 11:20","className":"八3班N","noReply":1}]
         */

        private String id;
        private String title;
        private String subjectName;
        private String versionName;
        private String fasName;
        private String chapName;
        private String serName;
        private String info;
        private List<ClassArrBean> classArr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getFasName() {
            return fasName;
        }

        public void setFasName(String fasName) {
            this.fasName = fasName;
        }

        public String getChapName() {
            return chapName;
        }

        public void setChapName(String chapName) {
            this.chapName = chapName;
        }

        public String getSerName() {
            return serName;
        }

        public void setSerName(String serName) {
            this.serName = serName;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public List<ClassArrBean> getClassArr() {
            return classArr;
        }

        public void setClassArr(List<ClassArrBean> classArr) {
            this.classArr = classArr;
        }

        public static class ClassArrBean {
            /**
             * id : 623
             * workId : 9108
             * classId : 3000153
             * addtime : 1527218420
             * endTime : 1527263999
             * addTime : 2018-05-25 11:20
             * className : 八3班N
             * noReply : 1
             */

            private int id;
            private int workId;
            private int classId;
            private int addtime;
            private int endTime;
            private String addTime;
            private String className;
            private int noReply;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getWorkId() {
                return workId;
            }

            public void setWorkId(int workId) {
                this.workId = workId;
            }

            public int getClassId() {
                return classId;
            }

            public void setClassId(int classId) {
                this.classId = classId;
            }

            public int getAddtime() {
                return addtime;
            }

            public void setAddtime(int addtime) {
                this.addtime = addtime;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public int getNoReply() {
                return noReply;
            }

            public void setNoReply(int noReply) {
                this.noReply = noReply;
            }

            @Override
            public String toString() {
                return className;
            }
        }
    }
}
