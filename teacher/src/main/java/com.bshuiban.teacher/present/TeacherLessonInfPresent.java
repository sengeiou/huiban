package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.contract.LessonInfContract;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.LessonInfPresent;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public class TeacherLessonInfPresent extends LessonInfPresent {

    private final RecommendParentParent<LessonInfContract.View> viewRecommendParentParent;

    public TeacherLessonInfPresent(LessonInfContract.View view) {
        super(view);
        viewRecommendParentParent = new RecommendParentParent<LessonInfContract.View>(view){
            @Override
            public void success() {
                view.recommendSuccess();
            }
        };
    }
    public void loadRecommendParent(String courseId,String classIds){
        viewRecommendParentParent.recommend(courseId, User.getInstance().getUserId(),classIds);
    }
}
