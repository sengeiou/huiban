package com.bshuiban.baselibrary;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.present.ClassActivityPresent;
import com.bshuiban.baselibrary.present.ClassSchedulePresent;
import com.bshuiban.baselibrary.present.HomePageParent;
import com.bshuiban.baselibrary.present.HuiFuDaoListPresent;
import com.bshuiban.baselibrary.utils.TextUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testInterface() throws InterruptedException {//{"userId":"2030246","time":"201805"}
        RetrofitService.getInstance().getServiceResult("getClassTeacherNowC","{\"userId\":\"2030219\"}",new RetrofitService.CallTest());

        //RetrofitService.getInstance().getServiceResult("getStuLearnBottom","{\"userId\":\"2030246\",\"time\":\"201805\"}",new RetrofitService.CallTest());
        Thread.sleep(2000);
    }
    @Test
    public void addition_isCorrect() {
        String time1 = "2分01秒";// 11时2分1秒
        int hIndex = time1.indexOf("时");
        String h="",m="",s="";
        if(hIndex>-1){
            h=time1.substring(0,hIndex);
        }
        hIndex+=1;
        int mIndex=time1.indexOf("分");
        if(mIndex>-1){
            m=time1.substring(hIndex,mIndex);
        }
        mIndex+=1;
        int sIndex=time1.indexOf("秒");
        if (sIndex>-1){
            s=time1.substring(mIndex,sIndex);
        }
        int hour = toInt(h);
        int min = toInt(m);
        int socend = toInt(s);
        int time=hour*60+min*60+socend;
        assertEquals(4, 2 + 2);
    }
    @Test
    public void text() throws InterruptedException {
        //new ClassActivityPresent(null).askInternetForClassActivityData("3000153",0,20);
        //new ClassSchedulePresent(null).askInternetForScheduleData("3000153");
        //new HomePageParent(null).test();{"userId":"2030246","start":0,"limit":20}
        //new HomePageParent(null).getMessageList("2030246");
//        RetrofitService.getInstance().getServiceResult("getClassNoticeList","{\"userId\":\"2030246\",\"start\":0,\"limit\":20}",new RetrofitService.CallTest());
        //new ClassActivityPresent(null).askInternetForClassActivityData("3000153",0,20);
        //留言列表
        //RetrofitService.getInstance().getServiceResult("getUserLeaveList", TextUtils.getUserIdListJson("2030246",0,10),new RetrofitService.CallTest());
        //获取教师主讲微课列表
        //RetrofitService.getInstance().getServiceResult("getTeacherCourseList", TextUtils.getUserIdListIndexJson("2030246",0,10),new RetrofitService.CallTest());
        HuiFuDaoListPresent huiFuDaoListPresent = new HuiFuDaoListPresent(null);
        //huiFuDaoListPresent.getAllSubject();
        //{"code":"2002","msg":"success","data":[{"id":24,"subjectName":"\u80fd\u529b\u57f9\u517b"},{"id":23,"subjectName":"\u7126\u70b9\u95ee\u9898"},{"id":22,"subjectName":"\u6280\u5de7\u77e5\u8bc6"},{"id":21,"subjectName":"\u57fa\u7840\u77e5\u8bc6"},{"id":19,"subjectName":"\u4ea7\u54c1\u57f9\u8bad"},{"id":18,"subjectName":"\u9053\u5fb7\u4e0e\u6cd5\u6cbb"},{"id":2,"subjectName":"\u6570\u5b66"},{"id":3,"subjectName":"\u82f1\u8bed"},{"id":4,"subjectName":"\u7269\u7406"},{"id":5,"subjectName":"\u5316\u5b66"},{"id":9,"subjectName":"\u5730\u7406"},{"id":6,"subjectName":"\u751f\u7269"},{"id":8,"subjectName":"\u5386\u53f2"},{"id":7,"subjectName":"\u653f\u6cbb"},{"id":11,"subjectName":"\u4fe1\u606f\u6280\u672f"},{"id":14,"subjectName":"\u4f53\u80b2"},{"id":16,"subjectName":"\u7f8e\u672f"},{"id":15,"subjectName":"\u97f3\u4e50"},{"id":17,"subjectName":"\u5fc3\u7406"},{"id":1,"subjectName":"\u8bed\u6587"}]}
        String json="{\"subjectId\":\"0\"}";
        RetrofitService.getInstance().getServiceResult("getStuClassSub","{\"classId\":\"3000153\"}",new RetrofitService.CallTest());
        //huiFuDaoListPresent.getScreeningData(json);
        Thread.sleep(2000);

    }
    private int toInt(String s){
        try {
            int hour = Integer.parseInt(s);
            return hour;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}