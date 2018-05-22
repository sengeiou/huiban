package com.bshuiban.baselibrary.view.activity;

import android.os.Bundle;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.StatisticalChartContract;
import com.bshuiban.baselibrary.model.StatisticalChartBean;
import com.bshuiban.baselibrary.present.StatisticalChartPresent;

import java.util.List;

public class StatisticalChartActivity extends BaseActivity<StatisticalChartPresent> implements StatisticalChartContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical_chart);
    }

    @Override
    public void updateStatisticalChartView(List<StatisticalChartBean.DataBean.ListBean> listBeans) {

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
