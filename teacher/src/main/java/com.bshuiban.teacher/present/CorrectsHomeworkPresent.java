package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.present.HomeworkPresent;
import com.bshuiban.teacher.contract.CorrectsHomeworkContract;

/**
 * Created by xinheng on 2018/5/31.<br/>
 * describe：批阅作业
 */
public class CorrectsHomeworkPresent extends HomeworkPresent<CorrectsHomeworkContract.View> implements CorrectsHomeworkContract.Present {
    public CorrectsHomeworkPresent(CorrectsHomeworkContract.View view) {
        super(view);
    }

    @Override
    public void loadHomeworkInf(int workId, int wtype) {
        loadHomeworkInfData(workId,wtype);
    }
}
