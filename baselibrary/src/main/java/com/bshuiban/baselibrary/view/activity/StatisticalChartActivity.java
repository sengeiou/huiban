package com.bshuiban.baselibrary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.StatisticalChartContract;
import com.bshuiban.baselibrary.model.StatisticalChartBean;
import com.bshuiban.baselibrary.present.StatisticalChartPresent;
import com.bshuiban.baselibrary.utils.DensityUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 统计折线图
 */
public class StatisticalChartActivity extends BaseActivity<StatisticalChartPresent> implements StatisticalChartContract.View {

    private LineChart lineChart;
    private boolean data;
    private TextView tv_front;
    private TextView tv_middle;
    private TextView tv_after;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical_chart);
        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        String subjectId = intent.getStringExtra("subjectId");
        lineChart = findViewById(R.id.lineChart);
        tPresent=new StatisticalChartPresent(this);
        tPresent.getStatisticalData(subjectId,time);
        tv_front = findViewById(R.id.tv_front);
        tv_middle = findViewById(R.id.tv_middle);
        tv_after = findViewById(R.id.tv_after);
        initLineChart(lineChart);
        tv_front.setOnClickListener(onClickListener);
        tv_middle.setOnClickListener(onClickListener);
        tv_after.setOnClickListener(onClickListener);
        findViewById(R.id.iv).setOnClickListener(v -> finish());
    }
    private View.OnClickListener onClickListener= v -> {
        if(!data){
            toast("数据加载中");
            return;
        }
        int colorSelect = ContextCompat.getColor(getApplicationContext(), R.color.guide_start_btn);
        int colorUnSelect = ContextCompat.getColor(getApplicationContext(), R.color.guide_start_btn3);
        float dp3 = DensityUtil.dip2px(getApplicationContext(), 3);
        int i = v.getId();
        if (i == R.id.tv_front) {
            tv_front.setBackground(getDrawable(colorSelect, colorSelect,new float[]{dp3,dp3,0,0,0,0,dp3,dp3}));
            tv_middle.setBackground(new ColorDrawable(colorUnSelect));
            tv_after.setBackground(getDrawable(colorUnSelect,colorUnSelect,new float[]{0,0,dp3,dp3,dp3,dp3,0,0}));
            updateChartView(tPresent.getList(1));
        } else if (i == R.id.tv_middle) {
            tv_front.setBackground(getDrawable(colorUnSelect, colorUnSelect,new float[]{dp3,dp3,0,0,0,0,dp3,dp3}));
            tv_middle.setBackground(new ColorDrawable(colorSelect));
            tv_after.setBackground(getDrawable(colorUnSelect,colorUnSelect,new float[]{0,0,dp3,dp3,dp3,dp3,0,0}));
            updateChartView(tPresent.getList(2));
        } else if (i == R.id.tv_after) {
            tv_front.setBackground(getDrawable(colorUnSelect, colorUnSelect,new float[]{dp3,dp3,0,0,0,0,dp3,dp3}));
            tv_middle.setBackground(new ColorDrawable(colorUnSelect));
            tv_after.setBackground(getDrawable(colorSelect,colorSelect,new float[]{0,0,dp3,dp3,dp3,dp3,0,0}));
            updateChartView(tPresent.getList(3));
        }
    };
    private GradientDrawable getDrawable(int colorStroke,int colorBg,float array[]){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(colorBg);
        float dp1 = DensityUtil.dip2px(getApplicationContext(), 1);
        //左上角xy半径，右上角，右下角，左下角
        //drawable.setCornerRadii(new float[]{dp3,dp3,0,0,0,0,dp3,dp3});
        drawable.setCornerRadii(array);
        drawable.setStroke((int) dp1,colorStroke);
        return drawable;
    }
    @Override
    public void updateStatisticalChartView(List<StatisticalChartBean.DataBean.ListBean> listBeans) {
        data=true;
        //updateChartView(listBeans);
        onClickListener.onClick(tv_front);
    }

    private void updateChartView(List<StatisticalChartBean.DataBean.ListBean> preList) {
        List<Entry> list_nime = new ArrayList<>();
        List<Entry> list_class = new ArrayList<>();
        if (preList != null && preList.size() > 0) {
            sort(preList);
            for (StatisticalChartBean.DataBean.ListBean bean : preList) {
                int day = bean.getDay();
                list_nime.add(new Entry(day, bean.getMineRate(),1));
                list_class.add(new Entry(day, bean.getClassRate()));
            }
        }
        setLineChart(lineChart, list_nime, list_class);
    }
    private void sort(List<StatisticalChartBean.DataBean.ListBean> list) {
        Collections.sort(list, (o1, o2) -> o1.getDay() - o2.getDay());
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
    private void initLineChart(final LineChart chart) {
        int xSizeId = R.dimen.dp_8;

        chart.setTouchEnabled(false); // 设置是否可以触摸
        //chart.setDragEnabled(false);// 是否可以拖拽
        //chart.setDoubleTapToZoomEnabled(false);
        chart.setScaleEnabled(false);
        //chart.setBackgroundResource(R.drawable.bg_bord);
        // 不显示数据描述
        chart.getDescription().setEnabled(false);

        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("暂无数据");
        // 不显示表格颜色
        //chart.setDrawGridBackground(false);
        // 不可以缩放
        //chart.setScaleEnabled(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);

        Legend legend = chart.getLegend();
        // 不显示图例
        legend.setEnabled(true);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setTextSize(13);
        legend.setXEntrySpace(20);
        float px80 = getResources().getDimension(R.dimen.dp_27);// value * metrics.density;
        float dp80 = px80 / getResources().getDisplayMetrics().density;
        legend.setYOffset(-getResources().getDimensionPixelOffset(R.dimen.dp_5));
        legend.setXOffset(0);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormLineWidth(10);
        legend.setFormSize(15);
        //legend.setYOffset(20);
        //legend.setCustom();
        // 向左偏移15dp，抵消y轴向右偏移的30dp
        //chart.setExtraLeftOffset(-15);
        //chart.setExtraLeftOffset(10);
        chart.setExtraTopOffset(-45);
        chart.setExtraBottomOffset(20);
        XAxis xAxis = chart.getXAxis();
        // 不显示x轴
        xAxis.setDrawAxisLine(false);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(0);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(px2dp(xSizeId));
        xAxis.setDrawGridLines(false);
        //xAxis.setGridColor(Color.parseColor("#30FFFFFF"));
        chart.post(new Runnable() {
            @Override
            public void run() {
                //mesureWidth : width) * 40 / 1581
                float i = chart.getMeasuredWidth() * 40f / 1581;
                chart.setExtraLeftOffset(px2dp(i));
                chart.invalidate();
            }
        });
        xAxis.setAxisMinimum(0);
        //xAxis.setAxisMaximum(30);
        //xAxis.setAxisLineWidth(1/30f);
        // 设置x轴数据偏移量
        xAxis.setYOffset(15);
        xAxis.setLabelCount(31);

        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 从y轴发出横向直线
        yAxis.setDrawGridLines(true);
        yAxis.setGridColor(Color.parseColor("#E8E9E9"));
        yAxis.setTextColor(getResources().getColor(R.color.gray_dia));
        //yAxis.setGridColor(Color.RED);
        yAxis.setZeroLineColor(Color.BLACK);
        yAxis.setTextSize(px2dp(xSizeId));
        // 设置y轴数据偏移量
        //yAxis.setXOffset(px2dp(10f));
//        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(100);
        //yAxis.setAxisLineWidth(0.2f);
        chart.setDrawGridBackground(false);
        //chart.setGridBackgroundColor(Color.RED);
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //Log.e("TAG", "getFormattedValue: "+value );
                return (int) value + "%";
            }
        });
    }
    public float px2dp(int px){
        //value * metrics.density;
        return getResources().getDimension(px)/getResources().getDisplayMetrics().density;
    }
    public float px2dp(float px){
        //value * metrics.density;
        return px/getResources().getDisplayMetrics().density;
    }
    private void setLineChart(LineChart chart, List<Entry> list_mine, List<Entry> list_class) {
        int max = Math.max(list_class.size(), list_mine.size());
        if (max > 0 && chart.getXAxis().getLabelCount() != max) {
            chart.getXAxis().setLabelCount(max);
        }
        LineDataSet lineDataSet1;
        LineDataSet lineDataSet2;
        if (chart.getData() != null && chart.getData().getDataSetCount() == 2) {
            lineDataSet1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            lineDataSet1.setValues(list_class);
            lineDataSet2.setValues(list_mine);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
        } else {
            List<ILineDataSet> lineDataSets = new ArrayList<>();

            if (list_class.size() > 0) {
                lineDataSet2 = getLineDataSet(list_class, "班级", "#77baff", false);
                lineDataSets.add(lineDataSet2);
            }
            if (list_mine.size() > 0) {
                lineDataSet1 = getLineDataSet(list_mine, "我的", "#ffa777", true);
                lineDataSets.add(lineDataSet1);
            }
            //isHighlightEnabled
            //lineDataSet.isHighlightEnabled()
            LineData data = new LineData(lineDataSets);
            chart.setData(data);
            chart.notifyDataSetChanged();
            chart.invalidate();
        }
    }
    private LineDataSet getLineDataSet(List<Entry> value, String name, String color, boolean tag) {
        LineDataSet lineDataSet;
        lineDataSet = new LineDataSet(value, name);
        // 设置曲线颜色
        int color1 = Color.parseColor(color);
        lineDataSet.setColor(color1);
        // 设置平滑曲线
        //lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        // 显示坐标点的小圆点
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleColor(color1);
        //lineDataSet.setCircleColorHole(color1);
        lineDataSet.setDrawCircleHole(false);
        // 不显示坐标点的数据
        lineDataSet.setDrawValues(false);
        // 不显示定位线
        lineDataSet.setHighlightEnabled(tag);
        if (tag) {
            lineDataSet.setCircleRadius(3);
        }else{
            lineDataSet.setCircleRadius(4);
        }
        //Log.e("TAG", "getLineDataSet: CircleRadius="+tag+", "+lineDataSet.getCircleRadius());
        lineDataSet.setHighLightColor(Color.TRANSPARENT);
        return lineDataSet;
    }
}
