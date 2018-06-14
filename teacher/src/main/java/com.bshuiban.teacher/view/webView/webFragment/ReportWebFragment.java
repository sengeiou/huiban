package com.bshuiban.teacher.view.webView.webFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.model.LogUtils;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.view.activity.StatisticalChartActivity;
import com.bshuiban.baselibrary.view.webview.webFragment.InteractionBaseWebViewFragment;
import com.bshuiban.teacher.contract.ReportContract;
import com.bshuiban.teacher.present.ReportPresent;
import com.bshuiban.teacher.view.dialog.GradeClassDialog;
import com.xinheng.date_dialog.dialog.SeleTwoDialog;
import com.xinheng.date_dialog.dialog.SelectDateDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：
 */
public class ReportWebFragment extends InteractionBaseWebViewFragment<ReportPresent> implements ReportContract.View {
    private int mYear = 2018, mMonth;
    private int subjectId = -1, subjectId1 = -1, gradeId = -1, gradeId1 = -1;
    private boolean dialogShow;
    private boolean classData = true;
    private int classId=-1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new ReportPresent(this);
        tPresent.loadGradeList();
        tPresent.loadSubjectList();
        tPresent.loadGradeClassList();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH)+1;
    }

    @Override
    public void update(Bundle bundle) {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        loadFileHtml("report");
        registerWebViewH5Interface(new ReportHtml());
        return view;
    }

    @Override
    protected void webViewLoadFinished() {
        loadJavascriptMethod("changeTime", mYear + "年" + mMonth + "月");
    }

    private void loadData() {
        if (classData) {
            if (subjectId != -1 && gradeId != -1)
                tPresent.loadReportsOfClasses(subjectId, gradeId, getStringDate());
        } else {
            if (subjectId1 != -1 && gradeId1 != -1)
                tPresent.loadGraspingOfStudents(subjectId1, gradeId, getStringDate(),classId);
        }
    }

    @Override
    public void updateView(int type, String json) {
        if (type == 1) {
            loadJavascriptMethod("getContent", json);
        } else {
            loadJavascriptMethod("getMsg", json);
        }
    }

    @Override
    public void loadListComplete() {
        dialogShow = true;
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
    private void startStatisticalChartActivity(){
        if(subjectId==-1){
            toast("想选择学科");
            return;
        }
        startActivity(new Intent(getActivity(), StatisticalChartActivity.class)
                .putExtra("time", getStringDate())
                .putExtra("subjectId", subjectId+""));
    }
    class ReportHtml {
        /**
         * 启动弹窗
         *
         * @param type 1 时间，2 科目 ，3 年级，4 年级+班级
         */
        @JavascriptInterface
        public void startDialog(int type) {
            getActivity().runOnUiThread(() -> {
                showReportDialog(type);
            });
        }

        @JavascriptInterface
        public void changeItem(int type) {
            classData = type == 0;
        }
        @JavascriptInterface
        public void statisticalChartActivity(){
            getActivity().runOnUiThread(()->{
                startStatisticalChartActivity();
            });
        }
    }

    private void showReportDialog(int type) {
        if (!dialogShow) {
            toast("数据加载中");
            return;
        }
        switch (type) {// 1 时间，2 科目 ，3 年级，4 年级+班级
            case 1:
                startTimeDialog();
                break;
            case 2:
                if(tPresent.getSubjectEff()) {
                    startSubjectDialog(tPresent.getSubjectList());
                }else{
                    toast("学科暂无数据");
                }
                break;
            case 3:
                if(tPresent.getGradeListEff()) {
                    startGradeDialog(tPresent.getGradeListString());
                }else{
                    toast("年级暂无数据");
                }
                break;
            case 4:
                if(tPresent.getGradeClassEff()) {
                    startGradeClassDialog();
                }else {
                    toast("年级/班级暂无数据");
                }
                break;
        }
    }

    private String getStringDate() {
        String months;
        if (mMonth >= 10) {
            months = String.valueOf(mMonth);
        } else {
            months = "0" + mMonth;
        }
        return mYear + "" + months;
    }

    SelectDateDialog.OnClickListener changeTime = new SelectDateDialog.OnClickListener() {
        @Override
        public boolean onSure(int year, int month, int day, long time) {
            mYear = year;
            mMonth = month;
            loadData();
            Log.e(TAG, "onSure: " + year + ", " + month);
            loadJavascriptMethod("changeTime", year + "年" + month + "月");
            return false;
        }

        @Override
        public boolean onCancel() {
            return false;
        }
    };

    private void startTimeDialog() {
        SelectDateDialog dateDialog = new SelectDateDialog(getActivity());
        dateDialog.setDateType(SelectDateDialog.YEAR_MONTH);
        dateDialog.setOnClickListener(changeTime);
        dateDialog.show(mYear, mMonth, 1);
    }

    SeleTwoDialog.OnClickListener listener = new SeleTwoDialog.OnClickListener() {
        @Override
        public boolean onSure(String left, int leftIndex, String right, int rightIndex) {
            int subjectId = tPresent.getSubjectId(leftIndex);
            if (classData) {
                ReportWebFragment.this.subjectId = subjectId;
            } else {
                subjectId1 = subjectId;
            }
            LogUtils.e(LogUtils.TAG, "onSure subject=" + left);
            String name;
            if(classData){
                name="changeSubject";
            }else {
                name="stuSub";
            }
            loadJavascriptMethod(name,left);
            loadData();
            return false;
        }

        @Override
        public boolean onCancel() {
            return false;
        }
    };

    private void startSubjectDialog(List<String> list1) {
        startSelectTwoDialog(list1, null, listener);
    }

    private void startGradeClassDialog() {
        GradeClassDialog.OnClickListener listener = new GradeClassDialog.OnClickListener() {

            @Override
            public boolean onSure(String left, int gradeId, String right, int classId) {
                ReportWebFragment.this.gradeId1=gradeId;
                ReportWebFragment.this.classId=classId;
                LogUtils.e(LogUtils.TAG, "onSure class=" + left + ", " + right);
                loadJavascriptMethod("changeclass", left + TextUtils.cleanNull(right));
                loadData();
                return false;
            }

            @Override
            public boolean onCancel() {
                return false;
            }
        };
        new GradeClassDialog(getActivity(), listener, tPresent.getGrade(), tPresent.getGradeClass()).show();
    }

    private void startGradeDialog(List<String> list1) {
        SeleTwoDialog.OnClickListener listener = new SeleTwoDialog.OnClickListener() {
            @Override
            public boolean onSure(String left, int leftIndex, String right, int rightIndex) {
                int graid=tPresent.getGradeId(leftIndex);
                gradeId=graid;
                LogUtils.e(LogUtils.TAG, "onSure Grade=" + left);
                String name;
                if(classData){
                    name="changeGrade";
                }else {
                    name="stuSub";
                }
                loadJavascriptMethod(name, left);
                loadData();
                return false;
            }

            @Override
            public boolean onCancel() {
                return false;
            }
        };
        startSelectTwoDialog(list1, null, listener);
    }

    private void startSelectTwoDialog(List list1, List list2, SeleTwoDialog.OnClickListener listener) {
        new SeleTwoDialog(getActivity(), listener, list1, list2).show();
    }

}
