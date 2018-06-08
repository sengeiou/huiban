package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.GeneralBean;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：概况
 */
public interface GeneralSituationContract {
    interface View extends BaseView{
        void updateView(GeneralBean.DataBean data);
        String getUserId();
        String getClassId();

        /**
         * 处理关注
         * @param tag true 关注成功 ，false 取消关注成功
         */
        void guanZhuResult(boolean tag);
    }
    interface Present{
        /**
         * 获取网络数据
         */
        void askInterNetForData();

        /**
         * 关注
         * @param tag true 加关注
         * @param attUserId
         */
        void guanZhu(boolean tag,int attUserId);
    }
}
