package com.bshuiban.student;

import com.bshuiban.student.model.Homework;
import com.bshuiban.student.present.StudentHomePresent;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws InterruptedException {
        //new StudentHomePresent(null).getUserDataForInernet();
        Homework homework = new Homework();
        ArrayList<Homework.Data> homework1 = new ArrayList<>();
        homework.setHomework(homework1);
        Random random=new Random();
        homework.setTime("15:23");
        homework.setTitle("第三单元测试");
        for (int i = 0; i < 12; i++) {
            Homework.Data data = new Homework.Data();
            boolean tag=random.nextInt(5)>2?true:false;
            data.setComplete(tag);
            if(tag){
                data.setResult(random.nextInt(3));
            }
            homework1.add(data);
        }
        Gson gson = new Gson();
        String s = gson.toJson(homework);
        System.out.print(s);
        //Thread.sleep(3000);
    }
}