package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/6/12.<br/>
 * describe：
 */
public class UserMoreBean extends ResultBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Id : 3030169
         * UserId : 2029962
         * Email : 15489189222@qq.com
         * Mobile : 13426480529
         * Password : c07cced9c3f0d5a4f2156f0b02159871
         * IsPass : 1
         * typeId : 1
         */

        private int Id;
        private int UserId;
        private String Email;
        private String Mobile;
        private String Password;
        private int IsPass;
        private int typeId;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public int getIsPass() {
            return IsPass;
        }

        public void setIsPass(int IsPass) {
            this.IsPass = IsPass;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

    }
}
