package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：学习动态
 */
public class LearningDynamicBean extends ResultBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * send : 2030219
         * content : 楊楊老师发布课中资料《第八章　运动和力》
         * addtime : 2018-05-08
         * imgUrl :
         * name : 楊楊
         * toNow : 1970-01-01 08:00
         */

        private String send;
        private String content;
        private String addtime;
        private String imgUrl;
        private String name;
        private String toNow;

        public String getSend() {
            return send;
        }

        public void setSend(String send) {
            this.send = send;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToNow() {
            return toNow;
        }

        public void setToNow(String toNow) {
            this.toNow = toNow;
        }
    }
}
