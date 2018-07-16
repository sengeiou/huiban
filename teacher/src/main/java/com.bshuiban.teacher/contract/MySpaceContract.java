package com.bshuiban.teacher.contract;
import com.bshuiban.baselibrary.contract.ListContract;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：我的空间
 */
public interface MySpaceContract {
    interface View extends ListContract.View{
        void updateSpaceHeadView(String json);
    }
    interface Present{
        void loadSpaceHeadData();
        void loadMessageListInf(String userId);
    }
}
