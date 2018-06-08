package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.TodayHomeworkContract;

/**
 * Created by xinheng on 2018/6/5.<br/>
 * describe：
 */
public class TodayHomeworkPresent extends BasePresent<TodayHomeworkContract.View> implements TodayHomeworkContract.Present {
    public TodayHomeworkPresent(TodayHomeworkContract.View view) {
        super(view);
    }

    /**
     *
     * @param type 课前1、课后3
     */
    @Override
    public void loadTodayHomework(int type) {//{"userId":"","type":}
        askInternet("getHBTeaTodayWork", "{\"userId\":\""+ User.getInstance().getUserId()+"\",\"type\":"+type+"}", new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateListView(type,msg);
                }
            }

            @Override
            protected void fail(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }

    @Override
    public void addWorkRemind(int workId) {//{"":"","":};
        String classIdArrayString = User.getInstance().getClassIdArrayString();
        askInternet("addWorkRemind", "{\"classId\":\"" + classIdArrayString + "\",\"workId\":" + workId + "}", new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()){
                    view.fail("提醒成功");
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }
}
