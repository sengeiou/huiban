package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.GeneralSituationContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.GeneralBean;
import com.bshuiban.baselibrary.view.fragment.GeneralSituationFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinheng on 2018/5/4.<br/>
 * describe：概况P
 */
public class GeneralSituationPresent extends BasePresent<GeneralSituationContract.View> implements GeneralSituationContract.Present {

    public GeneralSituationPresent(GeneralSituationContract.View view) {
        super(view);
    }

    @Override
    public void askInterNetForData() {
        if(isEffective()) {
            getClassInfo(view.getClassId(),view.getUserId());
        }
    }

    /**
     * 获取班级概况
     * @param classId
     * @param userId
     */
    private void getClassInfo(String classId,String userId){
//        call = RetrofitService.getInstance().getServiceResult("getClassInfo", getMapinf(classId, userId), new RetrofitService.CallTest());
        call = RetrofitService.getInstance().getServiceResult("getClassInfo", getMapinf(classId, userId), new RetrofitService.CallResult<GeneralBean>(GeneralBean.class) {
            @Override
            protected void success(GeneralBean generalBean) {
                if(isEffective()){
                    GeneralBean.DataBean data = generalBean.getData();
                    view.updateView(data);
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

    private Map<String,Object> getMapinf(String classId, String userId){
        Map<String,Object> map=new HashMap<>(2);
        map.put("classId",classId);
        map.put("userId",userId);
        return map;
    }
}
