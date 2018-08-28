package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.UpdateAppContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.UploadAppBean;

/**
 * Created by xinheng on 2018/6/14.<br/>
 * describe：
 */
public class UpdateAppPresent extends BasePresent<UpdateAppContract.View> implements UpdateAppContract.Present {
    public UpdateAppPresent(UpdateAppContract.View view) {
        super(view);
    }

    @Override
    public void loadUpdate() {
       askInternet("getSoftVersionLastest",(String)null, new RetrofitService.CallResult<UploadAppBean>(UploadAppBean.class) {
           @Override
           protected void success(UploadAppBean uploadAppBean) {
                if(isEffective()){
                    UploadAppBean.DataBean data = uploadAppBean.getData();
                    view.updateView(data);
                }
           }

           @Override
           protected void error(String error) {
               if(isEffective()){
                   view.updateView(null);
                   view.fail(error);
               }
           }
       });
    }
}
