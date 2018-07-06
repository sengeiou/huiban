package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.SubjectBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.AllSubjectPresent;
import com.bshuiban.baselibrary.present.ListPresent;
import com.bshuiban.teacher.contract.FilterConditionContract;
import com.bshuiban.teacher.contract.LessonListContract;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：课程列表
 */
public class LessonListPresent extends ListPresent<LessonListContract.View> implements LessonListContract.Present{
    private final AllSubjectPresent<LessonListContract.View> allSubjectPresent;
    private JsonObject mJson;
    private final RecommendParentParent<LessonListContract.View> recommendParentParent;

    public LessonListPresent(LessonListContract.View view) {
        super(view);
        allSubjectPresent = new AllSubjectPresent<LessonListContract.View>(view) {
            @Override
            protected void loadAllSubject(SubjectBean dataBean) {
                LessonListPresent.this.loadAllSubject(dataBean);
            }

            @Override
            protected void fail(String s) {
                loadLessonListData(null);
            }
        };
        recommendParentParent = new RecommendParentParent<LessonListContract.View>(view){
            @Override
            public void success() {
                view.fail("成功");
            }
        };

    }

    private void loadAllSubject(SubjectBean dataBean) {
        view.loadAllSubject(dataBean);
        List<SubjectBean.DataBean> data = dataBean.getData();
        int id=-10;
        if(HomeworkBean.isEffictive(data)){
            for (int i = 0; i < data.size(); i++) {
                SubjectBean.DataBean dataBean1 = data.get(i);
                if(dataBean1.getIsSelect()==1){
                    id=dataBean1.getId();
                    break;
                }
            }
        }
        //{"subjectId":""}
        String key;
        if(id==-10){
            key=null;
        }else {
            key="{\"subjectId\":"+id+"}";
        }
        loadLessonListData(key);
    }

    @Override
    public void loadLessonListData(String json) {
        mJson=pareJsonObj(json);
        start=0;
        clearArray();
        getInterNetData();
    }

    @Override
    public void loadSearchLessonListData(String search) {
        if(mJson==null){
            mJson=new JsonObject();
        }
        start=0;
        mJson.addProperty("name",search);
        //mJson.addProperty("keyword",search);
        getInterNetData();
    }

    @Override
    public void loadRecommendParent(String courseId, String classId) {
        //List<String> classId = User.getInstance().getUserData().getClassId();
        recommendParentParent.recommend(courseId, User.getInstance().getUserId(), classId);
    }

    @Override
    public void getInterNetData() {
        mJson.addProperty("index",start);
        mJson.addProperty("limit",limit);
        askInternet("getVipCourseRcomList",gson.toJson(mJson),callHTMLJsonArray);
        //askInternet("getHBCourseList",gson.toJson(mJson),callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        view.updateList(json);
    }

    public void loadAllSubject() {
        allSubjectPresent.getAllSubject();
    }
}
