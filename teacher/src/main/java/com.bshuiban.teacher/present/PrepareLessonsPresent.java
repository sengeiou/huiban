package com.bshuiban.teacher.present;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.ListPresent;
import com.bshuiban.teacher.contract.PrepareLessonsContract;
import com.google.gson.JsonObject;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：
 */
public class PrepareLessonsPresent extends ListPresent<PrepareLessonsContract.View> implements PrepareLessonsContract.Present{
    private JsonObject mJson;
    public PrepareLessonsPresent(PrepareLessonsContract.View view) {
        super(view);
    }

    @Override
    public void loadLessons(String json) {
        mJson=pareJsonObj(json);
        mJson.addProperty("userId", User.getInstance().getUserId());
        start=0;
        getInterNetData();
    }

    @Override
    public void getInterNetData() {
        mJson.addProperty("start",start);
        mJson.addProperty("limit",limit);
        askInternet("getHBPrepareList",gson.toJson(mJson),callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        view.updateList(json);
    }

}
