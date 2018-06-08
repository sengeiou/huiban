package com.bshuiban.baselibrary.view.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.view.fragment.ClassScheduleFragment;
import com.bshuiban.baselibrary.view.fragment.ClassScheduleFragment1;

public class ClassScheduleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_schedule);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_fragment,new ClassScheduleFragment(),"classSchedule").commit();
    }
}
