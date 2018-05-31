package com.bshuiban.baselibrary.contract;

import android.widget.ListView;

/**
 * Created by xinheng on 2018/5/29.<br/>
 * describe：
 */
public interface SideCollectionListContract {
    interface View extends ListContract.View{

    }
    interface Present {
        void loadCollectionListData();
    }
}
