package com.bshuiban.parents;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        //1533916740
        Date date = new Date(1533896690 * 1000l);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date1 = new Date();
        System.out.print(date1.getTime()/1000+"\n");

        String format = sf.format(date);
        System.out.print(format);
    }
}