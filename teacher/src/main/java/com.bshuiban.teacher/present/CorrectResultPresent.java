package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.CorrectResultContract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：
 */
public class CorrectResultPresent extends BasePresent<CorrectResultContract.View> implements CorrectResultContract.Present{
    public CorrectResultPresent(CorrectResultContract.View view) {
        super(view);
    }

    @Override
    public void commitHomeworkResult(String classId,String preId, int type, int workId, String studentId, String json) {
        askInternet("markHBWork", getjsonMap(classId,User.getInstance().getUserId(), preId, type, workId, studentId, json), new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()){
                    view.commitSuccess();
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

    @Override
    public Map<String, Object> getjsonMap(String classId,String userId, String preId, int type, int workId, String studentId, String scoreStr) {
        Map<String, Object> map= new HashMap<>(6);
        map.put("classId",classId);
        map.put("userId",userId);
        map.put("preId",preId);
        map.put("type",type);
        map.put("workId",workId);
        map.put("studentId",studentId);
        map.put("scoreStr",scoreStr);
        return map;
    }
}
