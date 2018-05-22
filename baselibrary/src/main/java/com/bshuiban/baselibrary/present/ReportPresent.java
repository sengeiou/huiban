package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.ReportContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.SubjectBean;
import com.bshuiban.baselibrary.model.User;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：报告
 */
public class ReportPresent extends AllSubjectPresent<ReportContract.View> implements ReportContract.Present {
    public ReportPresent(ReportContract.View view) {
        super(view);
    }

    @Override
    public void getTitleBarData() {
       getAllSubject();
    }
    @Override
    protected void loadAllSubject(SubjectBean dataBean) {
        if(null!=dataBean.getData()&&isEffective()){
            view.updateTitleBar(dataBean.getData());
        }
    }
}
