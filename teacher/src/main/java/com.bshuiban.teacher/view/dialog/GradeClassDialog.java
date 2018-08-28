package com.bshuiban.teacher.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.teacher.model.GradeListBean;
import com.bshuiban.teacher.model.TeachClassBean;
import com.xinheng.date_dialog.R;
import com.xinheng.date_dialog.dialog.BaseDialog;
import com.xinheng.date_dialog.view.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：两条选项弹窗
 */
public class GradeClassDialog extends BaseDialog {
    private View dialogView;
    private WheelView leftWheel;
    private WheelView rightWheel;
    private OnClickListener onClickListener;
    private GradeListBean leftList;
    private TeachClassBean rightList;
    private String leftS, rightS;
    private int leftIndex, rightIndex;
    boolean cancelable = true;
    private List<TeachClassBean.DataBean> classList;

    public GradeClassDialog(Context mContext, OnClickListener listener,
                            GradeListBean left, TeachClassBean right) {
        this.context = mContext;
        onClickListener = listener;
        this.leftList = left;
        this.rightList = right;
        create();
    }

    /**
     * 创建选择时间对话框
     */
    private void create() {

        if (dialog != null) {
            return;
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        dialogView = layoutInflater.inflate(R.layout.dialog_wheel_select_two, null);
        leftWheel = (WheelView) dialogView.findViewById(R.id.select_time_wheel_left);
        rightWheel = (WheelView) dialogView.findViewById(R.id.select_time_wheel_right);
        if (leftList != null) {
            leftWheel.setWheelItemList(TextUtils.getListString(leftList.getData()));
            leftS=leftWheel.getCurrentItemValue();
            //未滑动前
            int gradeId = leftList.getData().get(leftWheel.getCurrentItem()).getGradeId();
            if(null!=rightList) {
                classList = rightList.getClassList(gradeId);
                rightWheel.setWheelItemList(TextUtils.getListString(classList));
                if (classList.size() > 0) {
                    rightS = classList.get(0).getClassName();
                    rightIndex = 0;
                } else {
                    rightS = null;
                }
            }else{
                ArrayList<String> rightList1 = new ArrayList<>();
                rightWheel.setWheelItemList(rightList1);
            }
            leftWheel.setOnSelectListener(new WheelView.SelectListener() {
                @Override
                public void onSelect(int index, String text) {
                    leftS = text;
                    leftIndex = index;
                    int gradeId = leftList.getData().get(index).getGradeId();
                    classList = rightList.getClassList(gradeId);
                    rightWheel.setWheelItemList(TextUtils.getListString(classList));
                    if(classList.size()>0) {
                        rightS = classList.get(0).getClassName();
                        rightIndex = 0;
                    }else {
                        rightS=null;
                    }
                }
            });
        } else {
            ArrayList<String> leftList1 = new ArrayList<String>();
            leftWheel.setWheelItemList(leftList1);
            ArrayList<String> rightList1 = new ArrayList<>();
            rightWheel.setWheelItemList(rightList1);
        }

        rightWheel.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                rightS = text;
                rightIndex = index;
            }
        });

        dialog = new AlertDialog.Builder(context).setView(dialogView).create();
        dialog.setCancelable(cancelable);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.select_time_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    if (!onClickListener.onCancel()) {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
        Button sureBtn = (Button) dialogView.findViewById(R.id.select_time_sure_btn);
        sureBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    int rightId=-1;
                    if(null!=classList&&classList.size()>rightIndex){
                        rightId=classList.get(rightIndex).getClassId();
                    }
                    if (!onClickListener.onSure(leftS, leftList.getData().get(leftIndex).getGradeId(), rightS, rightId)) {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }

            }
        });

    }

    /**
     * 显示对话框
     *
     * @param leftIndex  默认显示
     * @param rightIndex 默认显示
     */
    public void show(int leftIndex, int rightIndex) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        leftWheel.setCurrentItem(leftIndex);
        rightWheel.setCurrentItem(rightIndex);
        dialog.show();
    }

    public void setOnUpdateTimeListener(OnClickListener listener) {
        onClickListener = listener;
    }

    /**
     * 选择时间对话框回调接口，调用者实现
     *
     * @author huangzj
     */
    public interface OnClickListener {
        boolean onSure(String left, int gradeId, String right, int classId);

        boolean onCancel();
    }

}
