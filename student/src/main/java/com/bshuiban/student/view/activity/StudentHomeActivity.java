package com.bshuiban.student.view.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.view.activity.HomePageActivity;
import com.bshuiban.baselibrary.view.customer.BottomBar;
import com.bshuiban.baselibrary.view.customer.BottomBarTab;
import com.bshuiban.baselibrary.view.fragment.BaseFragment;
import com.bshuiban.baselibrary.view.fragment.InteractionBaseFragment;
import com.bshuiban.student.R;

/**
 * 学生端首页
 */
public class StudentHomeActivity extends HomePageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initNavigationView(NavigationView navigationView) {
        navigationView.inflateHeaderView(R.layout.nav_student_header_home_page);
        navigationView.inflateMenu(R.menu.activity_home_page_student_drawer);
        ImageView iv_head = (ImageView) navigationView.findViewById(R.id.iv_head);//头像
        TextView tv_text = (TextView) navigationView.findViewById(R.id.tv_text);//简介

    }

    @Override
    protected void initBottomBar(BottomBar bottomBar) {
        bottomBar.addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_home, "首页"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_work, "作业"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_report, "报告"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_error, "错题"));
    }

    @Override
    protected void itemSelectedId(int id) {
        if (id == R.id.nav_my_space) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_about_self) {

        } else if (id == R.id.nav_opinion) {

        } else {

        }
    }

    @Override
    protected InteractionBaseFragment getFragment(String tag, Bundle bundle) {
        return null;
    }
}
