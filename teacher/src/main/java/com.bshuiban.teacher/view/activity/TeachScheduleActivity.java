package com.bshuiban.teacher.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bshuiban.baselibrary.model.ClassScheduleBean;
import com.bshuiban.baselibrary.view.activity.BaseActivity;
import com.bshuiban.baselibrary.view.customer.ClassSchedule;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bshuiban.teacher.R;
import com.bshuiban.teacher.contract.TeachScheduleContract;
import com.bshuiban.teacher.present.TeachSchedulePresent;

import java.util.List;

/**
 * Created by xinheng on 2018/6/8.<br/>
 * describe：老师课表
 */
public class TeachScheduleActivity extends BaseActivity<TeachSchedulePresent> implements TeachScheduleContract.View {
    private ClassSchedule classSchedule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_schedule);
        tPresent=new TeachSchedulePresent(this);

        TitleView titleView = findViewById(R.id.titleView);
        titleView.setOnClickListener(v -> finish());
        classSchedule=findViewById(R.id.class_schedule);
        classSchedule.setTeachTag(true);
        tPresent.loadSchedule();
    }

    @Override
    public void updateView(List<List<ClassScheduleBean.DataBean>> data) {
        classSchedule.setData(data);
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        toast(error);
    }
}
