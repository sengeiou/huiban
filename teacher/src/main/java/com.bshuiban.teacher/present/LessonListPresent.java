package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.ListPresent;
import com.bshuiban.teacher.contract.LessonListContract;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：课程列表
 */
public class LessonListPresent extends ListPresent<LessonListContract.View> implements LessonListContract.Present{
    private JsonObject mJson;
    private final RecommendParentParent<LessonListContract.View> recommendParentParent;

    public LessonListPresent(LessonListContract.View view) {
        super(view);
        recommendParentParent = new RecommendParentParent<LessonListContract.View>(view){
            @Override
            public void success() {
                view.fail("成功");
            }
        };
    }

    @Override
    public void loadLessonListData(String json) {
        mJson=pareJsonObj(json);
        start=0;
        getInterNetData();
    }

    @Override
    public void loadSearchLessonListData(String search) {
        if(mJson==null){
            mJson=new JsonObject();
        }
        mJson.addProperty("keyword",search);
        getInterNetData();
    }

    @Override
    public void loadRecommendParent(String courseId) {
        List<String> classId = User.getInstance().getUserData().getClassId();
        recommendParentParent.recommend(courseId, User.getInstance().getUserId(), classId);
    }

    @Override
    public void getInterNetData() {
        mJson.addProperty("index",start);
        mJson.addProperty("limit",limit);
        askInternet("getHBCourseList",gson.toJson(mJson),callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        view.updateList(json);
    }

}
