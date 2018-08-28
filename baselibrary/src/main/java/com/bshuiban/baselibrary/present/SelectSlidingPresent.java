package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.SelectSlidingContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.TalVideoSelectedDetailBean;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.JsonArray;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：未用
 */
public class SelectSlidingPresent extends BasePresent<SelectSlidingContract.View> implements SelectSlidingContract.Present {
    public SelectSlidingPresent(SelectSlidingContract.View view) {
        super(view);
    }

    @Override
    public void loadKeMu() {//{"userId":"","schoolId":""}
        askInternet("getFilterSubjectL", "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"schoolId\":\"" + User.getInstance().getSchoolId() + "\"}", new RetrofitService.CallResult<TalVideoSelectedDetailBean>(TalVideoSelectedDetailBean.class) {
            @Override
            protected void success(TalVideoSelectedDetailBean talVideoSelectedDetailBean) {
                if(isEffective()){
                    view.updateKeMu(talVideoSelectedDetailBean.getData());
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
    public void loadBanben(int subjectId) {
        askInternet("getFilterVersionL", "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"subjectId\":" + subjectId + "}", new RetrofitService.CallResult<TalVideoSelectedDetailBean>(TalVideoSelectedDetailBean.class) {
            @Override
            protected void success(TalVideoSelectedDetailBean talVideoSelectedDetailBean) {
                if(isEffective()){
                    view.updateBanben(talVideoSelectedDetailBean.getData());
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
    public void loadFence(int subjectId, int versionId) {
        askInternet("getFilterFasL", "{\"userId\":\"" + User.getInstance().getUserId() + "\",\"subjectId\":" + subjectId + ",\"versionId\":"+versionId+"}", new RetrofitService.CallResult<TalVideoSelectedDetailBean>(TalVideoSelectedDetailBean.class) {
            @Override
            protected void success(TalVideoSelectedDetailBean talVideoSelectedDetailBean) {
                if(isEffective()){
                    view.updateFence(talVideoSelectedDetailBean.getData());
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
    public void loadZhangJieMuLu(int subjectId, int versionId, int fasId) {//{"userId":"","subjectId":,"versionId":,"fasId":,}
        askInternet("getFilterChapterL","{\"userId\":\""+User.getInstance().getUserId()+"\",\"subjectId\":"+subjectId+",\"versionId\":"+versionId+",\"fasId\":"+fasId+"}",new RetrofitService.CallHTMLJsonArray(){

            @Override
            protected void success(JsonArray msg) {
                if(isEffective()){
                    view.updateMuLu(msg);
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
    public void loadZhiShiDianMuLu(int subjectId, int stageId) {//{"subjectId":,"stageId":}
        askInternet("getFilterKnowL","{\"subjectId\":"+subjectId+",\"stageId\":"+stageId+"}",new RetrofitService.CallHTMLJsonArray(){

            @Override
            protected void success(JsonArray msg) {
                if(isEffective()){
                    view.updateMuLu(msg);
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
    public void loadXueDuan() {
        askInternet("getFilterStageL", "{\"userId\":\"" + User.getInstance().getUserId() + "\"}", new RetrofitService.CallResult<TalVideoSelectedDetailBean>(TalVideoSelectedDetailBean.class) {
            @Override
            protected void success(TalVideoSelectedDetailBean talVideoSelectedDetailBean) {
                if(isEffective()){
                    view.updateXueDuan(talVideoSelectedDetailBean.getData());
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
