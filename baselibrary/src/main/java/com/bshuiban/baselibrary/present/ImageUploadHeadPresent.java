package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.BaseView;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.ImageUploadHeadBean;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/6/8.<br/>
 * describe：图片上传后图片地址域名
 */
public class ImageUploadHeadPresent extends BasePresent{

    public ImageUploadHeadPresent(BaseView baseView) {
        super(baseView);
    }
    public void loadImageHead(){
        askInternet("getWebPath", (String) null, new RetrofitService.CallResult<ImageUploadHeadBean>(ImageUploadHeadBean.class) {
            @Override
            protected void success(ImageUploadHeadBean imageUploadHeadBean) {
                ImageUploadHeadBean.DataBean data = imageUploadHeadBean.getData();
                if(null!=data){
                    String url = data.getUrl();
                    User.getInstance().setImgHeadUrl(url);
                    if(isEffective()) {
                        loadResult(url);
                    }
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    fail(error);
                }
            }
        });
    }
    protected void loadResult(String url){

    }
    protected void fail(String error){
        view.fail(error);
    }
}
