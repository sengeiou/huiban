package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.CollectionContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Created by xinheng on 2018/5/16.<br/>
 * describe：
 */
public class CollectionPresent extends BasePresent<CollectionContract.View> implements CollectionContract.Present{
    private int limit;
    private int start;
    private JsonArray jsonArray;
    public CollectionPresent(CollectionContract.View view) {
        super(view);
    }

    @Override
    public void loadMoreData() {
        start+=limit;
        getInterNetData();
    }

    @Override
    public void refresh() {
        start=0;
        getInterNetData();
    }
    private Gson gson=new Gson();
    @Override
    public void getInterNetData() {
        RetrofitService.getInstance().getServiceResult("getUserCollectVipCourseList", "{\"userId\":\""+ User.getInstance().getUserId()+"\",\"index\":"+start+",\"limit\":"+limit+"}", new RetrofitService.CallHTMLJsonArray() {
            @Override
            protected void success(JsonArray msg) {
                if(isEffective()){
                    if(null==jsonArray){
                        jsonArray=msg;
                        view.updateList(gson.toJson(jsonArray));
                    }else{
                        if(null!=msg&&msg.size()>0) {
                            jsonArray.addAll(msg);
                            view.updateList(gson.toJson(jsonArray));
                        }
                    }
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

}
