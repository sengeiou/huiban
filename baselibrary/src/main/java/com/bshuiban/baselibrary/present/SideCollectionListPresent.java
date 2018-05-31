package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.SideCollectionListContract;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/5/29.<br/>
 * describe：侧边栏我的收藏
 */
public class SideCollectionListPresent extends ListPresent<SideCollectionListContract.View>implements SideCollectionListContract.Present {
    public SideCollectionListPresent(SideCollectionListContract.View view) {
        super(view);
    }

    @Override
    public void loadCollectionListData() {
        getInterNetData();
    }
    @Override
    public void getInterNetData() {
        askInternet("getUserCollectVipCourseList", "{\"userId\":\""+ User.getInstance().getUserId()+"\",\"index\":"+start+",\"limit\":"+limit+"}", callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        if(isEffective()){
            view.updateList(json);
        }
    }
}
