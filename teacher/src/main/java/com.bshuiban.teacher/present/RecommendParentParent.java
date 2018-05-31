package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ResultBean;
import com.bshuiban.baselibrary.present.BasePresent;

import java.util.List;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：推荐课程
 */
public class RecommendParentParent<V extends BaseView> extends BasePresent<V> {
    public RecommendParentParent(V v) {
        super(v);
    }


    public void recommend(String courseId,String recTeaId,String classIds) {//{"courseId":,"recTeaId":"","classId":""}
        askInternet("addVipRecommand", "{\"courseId\":"+courseId+",\"recTeaId\":\""+recTeaId+"\",\"classId\":\""+classIds+"\"}", new RetrofitService.CallResult<ResultBean>(ResultBean.class) {
            @Override
            protected void success(ResultBean resultBean) {
                if(isEffective()) {
                    RecommendParentParent.this.success();
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()) {
                    fail(error);
                }
            }
        });
    }
    /**
     * 推荐课程
     * @param courseId 教学资源标识
     * @param recTeaId 账户id
     * @param classIds 逗号间隔的多个班级id
     */
    public void recommend(String courseId,String recTeaId,List<String> classIds){//{"courseId":,"recTeaId":"","classId":""}
        StringBuffer stringBuffer=new StringBuffer();
        if(classIds!=null&&classIds.size()>0){
            for (int i = 0; i < classIds.size(); i++) {//3
                String s = classIds.get(i);
                stringBuffer.append(s);
                if(i<classIds.size()-1) {
                    stringBuffer.append(",");
                }
            }
        }
        recommend(courseId,recTeaId,stringBuffer.toString());
    }
    public void success(){

    }
    public void fail(String error){
        view.fail(error);
    }
}
