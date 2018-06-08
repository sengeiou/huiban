package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public interface ParentConfirmContract {
    interface View extends BaseView {
        void updateList(String json);
    }

    interface Present {
        void loadListData();
    }
}
