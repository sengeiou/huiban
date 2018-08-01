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

    /**
     * 开始限制
     */
    private boolean start;
    @Override
    public void loadCollectionListData() {
        start=true;
        clearArray();
        getInterNetData();
    }
    @Override
    public void getInterNetData() {
        if(!start){
            return;
        }
        askInternet("getUserCollectVipCourseList", "{\"userId\":\""+ User.getInstance().getReallyUserId()+"\",\"index\":"+super.start+",\"limit\":"+limit+"}", callHTMLJsonArray);
    }

    @Override
    public void updateView(String json) {
        if(isEffective()){
            view.updateList(json);
        }
    }
}
