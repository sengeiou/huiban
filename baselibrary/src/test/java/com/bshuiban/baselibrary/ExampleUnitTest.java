package com.bshuiban.baselibrary;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.present.ClassActivityPresent;
import com.bshuiban.baselibrary.present.ClassSchedulePresent;
import com.bshuiban.baselibrary.present.HomePageParent;
import com.bshuiban.baselibrary.present.HuiFuDaoListPresent;
import com.bshuiban.baselibrary.present.ImageUploadHeadPresent;
import com.bshuiban.baselibrary.utils.TextUtils;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testInterface() throws Exception {//{"userId":"2030246","time":"201805"}
        String s = "https://192.168.0.3";
        int start = s.indexOf("://");
        String substring = s.substring(start + 3);
        System.out.print(substring+"\n");

        String content="已知 <img class=\"mathml\" src=\"http://math.21cnjy.com/MathMLToImage?mml=%3Cmath+xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F1998%2FMath%2FMathML%22%3E%3Cmrow%3E%3Cmfrac%3E%3Cmrow%3E%3Cmn%3E3%3C%2Fmn%3E%3Cmi%3Ex%3C%2Fmi%3E%3Cmo%3E%E2%88%92%3C%2Fmo%3E%3Cmn%3E4%3C%2Fmn%3E%3C%2Fmrow%3E%3Cmrow%3E%3Cmrow%3E%3Cmo%3E%28%3C%2Fmo%3E%3Cmrow%3E%3Cmi%3Ex%3C%2Fmi%3E%3Cmo%3E%E2%88%92%3C%2Fmo%3E%3Cmn%3E1%3C%2Fmn%3E%3C%2Fmrow%3E%3Cmo%3E%29%3C%2Fmo%3E%3C%2Fmrow%3E%3Cmrow%3E%3Cmo%3E%28%3C%2Fmo%3E%3Cmrow%3E%3Cmi%3Ex%3C%2Fmi%3E%3Cmo%3E%E2%88%92%3C%2Fmo%3E%3Cmn%3E2%3C%2Fmn%3E%3C%2Fmrow%3E%3Cmo%3E%29%3C%2Fmo%3E%3C%2Fmrow%3E%3C%2Fmrow%3E%3C%2Fmfrac%3E%3C%2Fmrow%3E%3C%2Fmath%3E&key=14fdf09267c6c3d27d9149c380c1d353\" style=\"vertical-align: middle;\"> = <img class=\"mathml\" src=\"http://math.21cnjy.com/MathMLToImage?mml=%3Cmath+xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F1998%2FMath%2FMathML%22%3E%3Cmrow%3E%3Cmfrac%3E%3Cmi%3EA%3C%2Fmi%3E%3Cmrow%3E%3Cmi%3Ex%3C%2Fmi%3E%3Cmo%3E%E2%88%92%3C%2Fmo%3E%3Cmn%3E1%3C%2Fmn%3E%3C%2Fmrow%3E%3C%2Fmfrac%3E%3C%2Fmrow%3E%3C%2Fmath%3E&key=14fdf09267c6c3d27d9149c380c1d353\" style=\"vertical-align: middle;\"> + <img class=\"mathml\" src=\"http://math.21cnjy.com/MathMLToImage?mml=%3Cmath+xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F1998%2FMath%2FMathML%22%3E%3Cmrow%3E%3Cmfrac%3E%3Cmi%3EB%3C%2Fmi%3E%3Cmrow%3E%3Cmi%3Ex%3C%2Fmi%3E%3Cmo%3E%E2%88%92%3C%2Fmo%3E%3Cmn%3E2%3C%2Fmn%3E%3C%2Fmrow%3E%3C%2Fmfrac%3E%3C%2Fmrow%3E%3C%2Fmath%3E&key=14fdf09267c6c3d27d9149c380c1d353\" style=\"vertical-align: middle;\"> ，则实数A=（&nbsp; ）．\n" +
                "<input class=\"tkbox\" type=\"text\" name=\"answerId_3921134_1_9760\" placeholder=\"请填写答案\">";
        String encode = URLEncoder.encode(content, "utf-8");
        System.out.print(encode+"\n");
        content=content.replace("%22","\\%22");
        String decode11 = URLDecoder.decode(content, "utf-8");
        System.out.print(decode11+"\n");

        HttpURLConnection connection = null;
        String url = "http://yddown.21cnjy.com/NSoftMoved/2018/08/21/21cnjycom20180821230536_3_63804.zip?st=g1CrNhff0C7W2d6TFhe6ZQ&e=1534939361&n=%E4%BA%BA%E6%95%99%E7%89%88%E5%85%AB%E5%B9%B4%E7%BA%A7%E6%95%B0%E5%AD%A6%E4%B8%8A%E5%86%8C%E3%80%8A%E7%AC%AC11%E7%AB%A0%E4%B8%89%E8%A7%92%E5%BD%A2%E3%80%8B%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95%E9%A2%98%E5%90%AB%E7%AD%94%E6%A1%88";
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = 0;
        code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            String fileName = connection.getHeaderField("Content-Disposition");
            String decode = URLDecoder.decode(fileName, "utf-8");
            System.out.print(decode+"\n");
            // 通过Content-Disposition获取文件名
            if (fileName == null || fileName.length() < 1) {
                // 通过截取URL来获取文件名
                URL downloadUrl = connection.getURL();
                // 获得实际下载文件的URL
                fileName = downloadUrl.getFile();
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                System.out.print(fileName);

            } else {
                fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("filename=") + 9), "UTF-8");
                // 存在文件名会被包含在""里面，所以要去掉，否则读取异常
                fileName = fileName.replaceAll("\"", "");
                fileName = fileName.substring(0,fileName.indexOf(";"));
                System.out.print(fileName);

            }
        }

    }

    @Test
    public void addition_isCorrect() {
        String time1 = "2分01秒";// 11时2分1秒
        int hIndex = time1.indexOf("时");
        String h = "", m = "", s = "";
        if (hIndex > -1) {
            h = time1.substring(0, hIndex);
        }
        hIndex += 1;
        int mIndex = time1.indexOf("分");
        if (mIndex > -1) {
            m = time1.substring(hIndex, mIndex);
        }
        mIndex += 1;
        int sIndex = time1.indexOf("秒");
        if (sIndex > -1) {
            s = time1.substring(mIndex, sIndex);
        }
        int hour = toInt(h);
        int min = toInt(m);
        int socend = toInt(s);
        int time = hour * 60 + min * 60 + socend;
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
        String json = "{\"subjectId\":\"0\"}";
        RetrofitService.getInstance().getServiceResult("getStuClassSub", "{\"classId\":\"3000153\"}", new RetrofitService.CallTest());
        //huiFuDaoListPresent.getScreeningData(json);
        Thread.sleep(2000);

    }

    private int toInt(String s) {
        try {
            int hour = Integer.parseInt(s);
            return hour;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}