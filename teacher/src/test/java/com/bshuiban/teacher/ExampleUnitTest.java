package com.bshuiban.teacher;

import com.bshuiban.baselibrary.internet.RetrofitService;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void internet() throws InterruptedException {//2030219    222222   3000153, 3000151 "visitorId":""
        //RetrofitService.getInstance().getServiceResult("getUserInfo", "{\"userId\":\"" + "2030219"+"\"}", new RetrofitService.CallTest());
        //RetrofitService.getInstance().getServiceResult("getHBUserDetail", "{\"userId\":\"" + "2030219"+"\",\"masterId\":\"2030219\"}", new RetrofitService.CallTest());
        //RetrofitService.getInstance().getServiceResult("getHBPrepareList", "{\"userId\":\"" + "2030219"+"\",\"start\":0,\"limit\":10}", new RetrofitService.CallTest());
        //RetrofitService.getInstance().getServiceResult("getHBPreReleaseClass","{\"wtype\":"+1+",\"preId\":\""+"3755"+"\"}",new RetrofitService.CallTest());
//        RetrofitService.getInstance().getServiceResult("getClassTeacherNowC","{\"userId\":\"2030219\"}",new RetrofitService.CallTest());
        //"schoolId":"","subjectId":
        //RetrofitService.getInstance().getServiceResult("getReportsOfClasses","{\"userId\":\"2030219\"}",new RetrofitService.CallTest());
        //{"preparationId":"623","process":"1","workId":"9108","classId":"3000153"}
//        RetrofitService.getInstance().getServiceResult("getHBStudentTypeWork","{\"preparationId\":\"623\",\"process\":\"1\",\"workId\":\"9108\",\"classId\":\"3000153\"}",new RetrofitService.CallTest());
        RetrofitService.getInstance().getServiceResult("getCourseListByTid","{\"userId\":\"2030219\"}",new RetrofitService.CallTest());

        Thread.sleep(2000);
    }
}