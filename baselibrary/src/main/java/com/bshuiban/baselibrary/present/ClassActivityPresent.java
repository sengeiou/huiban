package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ClassActivityContract;
import com.bshuiban.baselibrary.internet.RetrofitService;

/**
 * Created by xinheng on 2018/5/9.<br/>
 * describe：
 */
public class ClassActivityPresent extends BasePresent<ClassActivityContract.View>implements ClassActivityContract.Present {
    public ClassActivityPresent(ClassActivityContract.View view) {
        super(view);
    }

    @Override
    public void askInternetForClassActivityData(String classId, int start, int limit) {
        RetrofitService.getInstance().getServiceResult("getClassMessageList",getJsonString(classId,start,limit),new RetrofitService.CallTest());
    }

    @Override
    public String getJsonString(String classId, int start, int limit) {
        return "{\"classId\":\""+classId+"\",\"start\":"+start+",\"limit\":"+limit+"}";
    }
}
