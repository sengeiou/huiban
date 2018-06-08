package com.bshuiban.baselibrary.model;

/**
 * Created by xinheng on 2018/6/8.<br/>
 * describe：
 */
public class ImageUploadHeadBean extends ResultBean {
    /**
     * data : {"url":"http://192.168.0.3:8091/"}
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
         * url : http://192.168.0.3:8091/
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
