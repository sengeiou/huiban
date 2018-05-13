package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：班级活动
 */
public class ClassActivityBean extends ResultBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5adea4e5f5ddaf069d9716fc
         * roleId : 1
         * imgUrl :
         * send : 2030246
         * sendRoleId : 1
         * sendName : 戴帅帅
         * content : <img alt="大笑" src="/static/js/plugin/xheditor/xheditor_emot/default/laugh.gif" />
         * toNow : 2018-04-24 11:30
         * slist : [{"id":"5ae94f71f5ddaf069d971b36","send":"2030076","sendRoleId":1,"sendName":"沈陽","receive":"","receiveName":"","content":"ttgt ","addTime":"2018-05-02 13:41"}]
         * count : 1
         */

        private String id;
        private int roleId;
        private String imgUrl;
        private String send;
        private int sendRoleId;
        private String sendName;
        private String content;
        private String toNow;
        private int count;
        private List<SlistBean> slist;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
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

        public String getToNow() {
            return toNow;
        }

        public void setToNow(String toNow) {
            this.toNow = toNow;
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
             * id : 5ae94f71f5ddaf069d971b36
             * send : 2030076
             * sendRoleId : 1
             * sendName : 沈陽
             * receive :
             * receiveName :
             * content : ttgt
             * addTime : 2018-05-02 13:41
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
