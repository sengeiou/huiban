package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public class MessageBean extends ResultBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5afb9099f5ddaf069d972095
         * imgUrl :
         * send : 2030246
         * sendRoleId : 1
         * sendName : 戴帅帅
         * content : DFGDFG
         * addTime : 2018-05-16 09:59
         * count : 0
         * slist : [{"id":"5afb9160f5ddaf069d9720b6","send":"2030246","sendRoleId":1,"sendName":"戴帅帅","receive":"2030246","receiveName":"戴帅帅","content":"321312312","addTime":"2018-05-16 10:03"},{"id":"5afb915ef5ddaf069d9720b4","send":"2030246","sendRoleId":1,"sendName":"戴帅帅","receive":"2030246","receiveName":"戴帅帅","content":"321312312","addTime":"2018-05-16 10:03"},{"id":"5afb915bf5ddaf069d9720b2","send":"2030246","sendRoleId":1,"sendName":"戴帅帅","receive":"2030246","receiveName":"戴帅帅","content":"4124124124","addTime":"2018-05-16 10:03"},{"id":"5afb9159f5ddaf069d9720b0","send":"2030246","sendRoleId":1,"sendName":"戴帅帅","receive":"2030246","receiveName":"戴帅帅","content":"3123123123","addTime":"2018-05-16 10:03"},{"id":"5afb9156f5ddaf069d9720ae","send":"2030246","sendRoleId":1,"sendName":"戴帅帅","receive":"2030246","receiveName":"戴帅帅","content":"42414214","addTime":"2018-05-16 10:03"},{"id":"5afb9153f5ddaf069d9720ac","send":"2030246","sendRoleId":1,"sendName":"戴帅帅","receive":"2030246","receiveName":"戴帅帅","content":"131231231","addTime":"2018-05-16 10:02"}]
         */

        private String id;
        private String imgUrl;
        private String send;
        private int sendRoleId;
        private String sendName;
        private String content;
        private String addTime;
        private int count;
        private List<SlistBean> slist;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getSend() {
            return send;
        }

        public void setSend(String send) {
            this.send = send;
        }

        public int getSendRoleId() {
            return sendRoleId;
        }

        public void setSendRoleId(int sendRoleId) {
            this.sendRoleId = sendRoleId;
        }

        public String getSendName() {
            return sendName;
        }

        public void setSendName(String sendName) {
            this.sendName = sendName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<SlistBean> getSlist() {
            return slist;
        }

        public void setSlist(List<SlistBean> slist) {
            this.slist = slist;
        }

        public static class SlistBean {
            /**
             * id : 5afb9160f5ddaf069d9720b6
             * send : 2030246
             * sendRoleId : 1
             * sendName : 戴帅帅
             * receive : 2030246
             * receiveName : 戴帅帅
             * content : 321312312
             * addTime : 2018-05-16 10:03
             */

            private String id;
            private String send;
            private int sendRoleId;
            private String sendName;
            private String receive;
            private String receiveName;
            private String content;
            private String addTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSend() {
                return send;
            }

            public void setSend(String send) {
                this.send = send;
            }

            public int getSendRoleId() {
                return sendRoleId;
            }

            public void setSendRoleId(int sendRoleId) {
                this.sendRoleId = sendRoleId;
            }

            public String getSendName() {
                return sendName;
            }

            public void setSendName(String sendName) {
                this.sendName = sendName;
            }

            public String getReceive() {
                return receive;
            }

            public void setReceive(String receive) {
                this.receive = receive;
            }

            public String getReceiveName() {
                return receiveName;
            }

            public void setReceiveName(String receiveName) {
                this.receiveName = receiveName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }
        }
    }
}
