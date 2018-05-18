package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ListContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Created by xinheng on 2018/5/16.<br/>
 * describe：收藏
 */
public class CollectionPresent extends ListPresent<ListContract.View>{

    public CollectionPresent(ListContract.View view) {
        super(view);
    }


    @Override
    public void updateView(String json) {
        view.updateList(json);
    }

    @Override
    public void getInterNetData() {
        call = RetrofitService.getInstance().getServiceResult("getUserCollectVipCourseList", "{\"userId\":\""+ User.getInstance().getUserId()+"\",\"index\":"+start+",\"limit\":"+limit+"}", callHTMLJsonArray);
    }

}
