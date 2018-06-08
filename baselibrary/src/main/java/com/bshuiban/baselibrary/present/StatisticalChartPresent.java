package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.contract.StatisticalChartContract;
import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.StatisticalChartBean;
import com.bshuiban.baselibrary.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinheng on 2018/5/21.<br/>
 * describe：
 */
public class StatisticalChartPresent extends BasePresent<StatisticalChartContract.View> implements StatisticalChartContract.Present {
    private StatisticalChartBean.DataBean data;
    private String mSubjectId, mTime;
    /**
     * 课前1，课中2，课后3
     */
    private int type=1;

    public StatisticalChartPresent(StatisticalChartContract.View view) {
        super(view);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void getStatisticalData(String subjectId, String time) {
        if (subjectId!=null&&subjectId.equals(mSubjectId) && time.equals(mTime)) {
            return;
        }
        mSubjectId = subjectId;
        mTime = time;
        RetrofitService.getInstance().getServiceMapResult("getStuLearnReportSubWork", getMap(subjectId, time), new RetrofitService.CallResult<StatisticalChartBean>(StatisticalChartBean.class) {
            @Override
            protected void success(StatisticalChartBean statisticalChartBean) {
                data = statisticalChartBean.getData();
                loadViewData();
            }

            @Override
            protected void error(String error) {
                data = null;
                loadViewData();
                if (isEffective()) {
                    view.fail(error);
                }
            }
        });
    }

    private Map<String, Object> getMap(String subjectId, String timeSlot) {
        Map<String, Object> map = new HashMap<>(6);
        LoginResultBean.Data userData = User.getInstance().getUserData();
        map.put("subjectId", subjectId);
        map.put("time", timeSlot);
        map.put("userId", userData.getUserId());
        map.put("classId", userData.getClassId1());
        return map;
    }

    private void loadViewData() {
        if (!isEffective()) {
            return;
        }
        List<StatisticalChartBean.DataBean.ListBean> listBeans = null;
        if (null != data) {
            switch (type) {
                case 1:
                    listBeans = data.getPreList();
                    break;
                case 2:
                    listBeans = data.getInList();
                    break;
                default:
                    listBeans = data.getWorkList();
                    break;
            }
        }
        view.updateStatisticalChartView(listBeans);
    }
    public List<StatisticalChartBean.DataBean.ListBean> getList(int type){
        List<StatisticalChartBean.DataBean.ListBean> listBeans = null;
        if (null != data) {
            switch (type) {
                case 1:
                    listBeans = data.getPreList();
                    break;
                case 2:
                    listBeans = data.getInList();
                    break;
                default:
                    listBeans = data.getWorkList();
                    break;
            }
        }
        return listBeans;
    }
}
