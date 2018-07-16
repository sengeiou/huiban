package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.present.ListPresent;
import com.bshuiban.teacher.contract.MainWeiClassContract;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public class MainWeiClassPresent extends ListPresent<MainWeiClassContract.View> implements  MainWeiClassContract.Present{
    private String userId;

    public MainWeiClassPresent(MainWeiClassContract.View view,String userId) {
        super(view);
        this.userId=userId;
    }

    @Override
    public void loadWeiClassData() {
        getInterNetData();
    }

    @Override
    public void getInterNetData() {//{"userId":"","index":,"limit":}
        askInternet("getTeacherCourseList","{\"userId\":\""+ userId+"\",\"index\":"+start+",\"limit\":"+limit+"}",callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        view.updateList(json);
    }
}
