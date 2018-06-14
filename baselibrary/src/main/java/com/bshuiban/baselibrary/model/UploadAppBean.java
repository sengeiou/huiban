package com.bshuiban.baselibrary.model;

/**
 * Created by xinheng on 2018/6/14.<br/>
 * describe：
 */
public class UploadAppBean extends ResultBean {
    /**
     * data : {"id":2,"soft":4,"verNum":1,"version":"v2.0","describe":"","downUrl":"1","releaseTime":1524114844}
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
         * id : 2
         * soft : 4
         * verNum : 1
         * version : v2.0
         * describe :
         * downUrl : 1
         * releaseTime : 1524114844
         */

        //private int id;
        //private int soft;
        /**
         * 版本号
         */
        private int verNum;
        /**
         * 应用版本号
         */
        private String version;
        /**
         * 更新描述
         */
        private String describe;
        /**
         *  下载地址
         */
        private String downUrl;
        private int releaseTime;

        /*public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSoft() {
            return soft;
        }

        public void setSoft(int soft) {
            this.soft = soft;
        }*/

        public int getVerNum() {
            return verNum;
        }

        public void setVerNum(int verNum) {
            this.verNum = verNum;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getDownUrl() {
            return downUrl;
        }

        public void setDownUrl(String downUrl) {
            this.downUrl = downUrl;
        }

        public int getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(int releaseTime) {
            this.releaseTime = releaseTime;
        }
    }
}
