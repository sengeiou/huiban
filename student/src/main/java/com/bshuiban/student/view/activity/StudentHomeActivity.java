package com.bshuiban.student.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.TextView;

import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.activity.HomePageActivity;
import com.bshuiban.baselibrary.view.customer.BottomBar;
import com.bshuiban.baselibrary.view.customer.BottomBarTab;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webFragment.InteractionBaseWebViewFragment;
import com.bshuiban.student.R;
import com.bshuiban.student.contract.StudentHomeContract;
import com.bshuiban.student.model.StudentUser;
import com.bshuiban.student.present.StudentHomePresent;
import com.bshuiban.student.view.fragment.StudentHomePageFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * 学生端首页
 */
public class StudentHomeActivity extends HomePageActivity<InteractionBaseWebViewFragment, StudentHomePresent> implements StudentHomeContract.View {
    private static final String BOTTOM1 = "homepage";
    private static final String BOTTOM2 = "homework";
    private static final String BOTTOM3 = "report";
    private static final String BOTTOM4 = "wrongHomework";
    private ImageView iv_head;
    private TextView tv_text, tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new StudentHomePresent(this);
        tPresent.getUserDataForInernet();
    }

    @Override
    protected void initNavigationView(NavigationView navigationView) {
        navigationView.inflateHeaderView(R.layout.nav_student_header_home_page);
        navigationView.inflateMenu(R.menu.activity_home_page_student_drawer);
        //头像
        iv_head = (ImageView) navigationView.findViewById(R.id.iv_head);
        //简介
        tv_text = (TextView) navigationView.findViewById(R.id.tv_text);
        //名字
        tv_name = (TextView) navigationView.findViewById(R.id.tv_name);
    }

    @Override
    protected void initBottomBar(BottomBar bottomBar) {
        bottomBar.addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_home, "首页"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_work_u, "作业"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_report_u, "报告"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_error_u, "错题"));
        BottomBar.OnTabSelectedListener onTabSelectedListener = new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                if (position == mNowPosition) {
                    return;
                } else {
                    mNowPosition = position;
                }
                switch (position) {
                    case 0://首页
                        startFragment(BOTTOM1, null);
                        break;
                    case 1://作业
                        //startFragment(BOTTOM2, null);
                        break;
                    case 2://报告
                        //startFragment(BOTTOM3, null);
                        break;
                    case 3://错题
                        //startFragment(BOTTOM4, null);
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        };
        bottomBar.setOnTabSelectedListener(onTabSelectedListener);
        onTabSelectedListener.onTabSelected(0, 1);
        mNowPosition = 0;
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
    protected InteractionBaseWebViewFragment getFragment(String tag, Bundle bundle) {
        InteractionBaseWebViewFragment fragment = null;
        switch (tag) {
            case BOTTOM1:
                fragment = new StudentHomePageFragment();
                break;
            case BOTTOM2:
                break;
            case BOTTOM3:
                break;
            case BOTTOM4:
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public void updateSlideView(StudentUser.DataBean data) {
        if (null != data) {
            String realName = data.getRealName();
            tv_name.setText(com.bshuiban.baselibrary.utils.TextUtils.cleanNull(realName));
            String schoolName = com.bshuiban.baselibrary.utils.TextUtils.cleanNull(data.getSchoolName());
            String gradleName = com.bshuiban.baselibrary.utils.TextUtils.cleanNull(data.getGradeName());
            List<String> classNames = data.getClassName();
            String className = "";
            if (null != classNames && classNames.size() > 0) {
                className = com.bshuiban.baselibrary.utils.TextUtils.cleanNull(classNames.get(0));
            }
            tv_text.setText(schoolName + gradleName + className);
            String icoPath = data.getIcoPath();
            if (TextUtils.isEmpty(icoPath)) {
                iv_head.setImageResource(R.drawable.app_logo);
            } else {
                RequestOptions requestOptions = new RequestOptions()
                        .circleCrop()
                        .error(R.drawable.app_logo);
                Glide.with(this).load(icoPath).apply(requestOptions).into(iv_head);
            }

        }
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {

    }
}
