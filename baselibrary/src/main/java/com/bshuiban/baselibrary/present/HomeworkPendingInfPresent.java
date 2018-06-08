package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.HomeworkPendingInfContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.internet.RetrofitUpload;
import com.bshuiban.baselibrary.model.Homework;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.ImageUploadResult;
import com.bshuiban.baselibrary.model.User;
import java.util.List;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：未完成（待）作业详情
 */
public class HomeworkPendingInfPresent extends HomeworkPresent<HomeworkPendingInfContract.View> implements HomeworkPendingInfContract.Present {
    private String url;
    public HomeworkPendingInfPresent(HomeworkPendingInfContract.View view) {
        super(view);
        flag=0;
        if(User.getInstance().getImgHeadUrl()==null){
            new ImageUploadHeadPresent(view){
                @Override
                protected void loadResult(String url) {
                    HomeworkPendingInfPresent.this.url=url;
                }

                @Override
                protected void fail(String error) {
                    super.fail(error);
                    url="";
                }
            }.loadImageHead();
        }else {
            url=User.getInstance().getImgHeadUrl();
        }
    }

    @Override
    public void loadHomeworkInf(int workId, int wtype) {
        loadHomeworkInfData(workId,wtype);
    }

    @Override
    protected void updateHomeworkView(HomeworkInfBean.DataBean dataBean) {
        if(isEffective()){
            String times = dataBean.getTimes();
            String title = dataBean.getTitle();
            List<HomeworkBean> homeworkBean = HomeworkBean.getHomeworkBean(dataBean);
            Homework<HomeworkBean> homework=new Homework<>();
            homework.setHomework(homeworkBean);
            int time = 0;
            try {
                time = Integer.parseInt(times);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //homework.setTime(Homework.getTime(time));
            homework.setTitle(title);
            User.getInstance().setHomework(homework);
            view.updateHomeworkTitleList(homeworkBean,time);
        }
    }

    @Override
    public void uploadImage(String imgPath) {
       call = RetrofitUpload.getInstance().loadFile(imgPath, new RetrofitService.CallPlaintext<ImageUploadResult>(ImageUploadResult.class) {
           @Override
           protected void error(String error) {
               if(isEffective()){
                   view.fail(error);
               }
           }
           @Override
           protected void result(ImageUploadResult imageUploadResult) {
               if(isEffective()) {
                   String img = imageUploadResult.getImg();
                   view.updateAnswerResult(url+img);
               }
           }
       });
    }
}
