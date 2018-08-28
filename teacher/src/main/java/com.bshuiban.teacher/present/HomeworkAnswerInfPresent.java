package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.teacher.contract.HomeworkAnswerInfContract;
import com.bshuiban.teacher.model.HomeworkAnswerInfBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：各题答题详情
 */
public class HomeworkAnswerInfPresent extends BasePresent<HomeworkAnswerInfContract.View> implements HomeworkAnswerInfContract.Present {
    private List<HomeworkAnswerInfBean.DataBean> data;

    public HomeworkAnswerInfPresent(HomeworkAnswerInfContract.View view) {
        super(view);
    }

    @Override
    public void loadHomeworkAnswerData(String preId,int workId,int type,String classId) {//"preId":,"type":,"classId":"":
        askInternet("getHBTeaPreExamRate", "{\"preId\":" + preId + ",\"workId\":" + workId + ",\"type\":" + type + ",\"classId\":\"" + classId + "\"}", new RetrofitService.CallResult<HomeworkAnswerInfBean>(HomeworkAnswerInfBean.class) {
            @Override
            protected void success(HomeworkAnswerInfBean homeworkAnswerInfBean) {
                data = homeworkAnswerInfBean.getData();
                int size=data!=null?data.size():0;
                if(isEffective()){
                    view.loadWebView(size);
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
    Gson gson=new Gson();
    @Override
    public void loadProblemContent(int position) {
        if(null!=data){
            HomeworkAnswerInfBean.DataBean dataBean = data.get(position);
            if(isEffective()){
                view.updateWebView(gson.toJson(dataBean));
            }
        }else{//赞不考虑

        }
    }
}
