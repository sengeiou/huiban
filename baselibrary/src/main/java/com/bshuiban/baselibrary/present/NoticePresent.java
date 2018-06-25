package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.NoticeContract;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/5/23.<br/>
 * describe：
 */
public class NoticePresent extends ListPresent<NoticeContract.View> {
    public NoticePresent(NoticeContract.View view) {
        super(view);
    }

    @Override
    public void getInterNetData() {//{"userId":"","start":,"limit":}
        askInternet("getClassNoticeList","{\"classId\":\""+ User.getInstance().getClassId()+"\",\"start\":"+start+",\"limit\":"+limit+"}",callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        view.updateList(json);
    }
}
