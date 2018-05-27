package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by xinheng on 2018/5/25.<br/>
 * describe：
 */
public class SubjcetsBean extends ResultBean {
    /**
     * cnt : 7
     * data : [{"id":1,"name":"语文"},{"id":2,"name":"数学"},{"id":3,"name":"英语"},{"id":4,"name":"物理"},{"id":6,"name":"生物"},{"id":8,"name":"历史"},{"id":17,"name":"心理"}]
     */

    private int cnt;
    private List<DataBean> data;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 语文
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
