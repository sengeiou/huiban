package com.bshuiban.teacher.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bshuiban.baselibrary.view.activity.AboutSelfActivity;
import com.bshuiban.baselibrary.view.activity.CleanCacheActivity;
import com.bshuiban.baselibrary.view.activity.HomePageActivity;
import com.bshuiban.baselibrary.view.activity.OpinionActivity;
import com.bshuiban.baselibrary.view.customer.BottomBar;
import com.bshuiban.baselibrary.view.customer.BottomBarTab;
import com.bshuiban.baselibrary.view.fragment.HomeworkFragment;
import com.bshuiban.baselibrary.view.fragment.ReportFragment;
import com.bshuiban.baselibrary.view.webview.webActivity.CollectionListActivity;
import com.bshuiban.baselibrary.view.webview.webFragment.ErrorHomeworkWebFragment;
import com.bshuiban.baselibrary.view.webview.webFragment.InteractionBaseWebViewFragment;
import com.bshuiban.teacher.R;
import com.bshuiban.teacher.contract.TeacherHomeContract;
import com.bshuiban.teacher.model.TeacherUser;
import com.bshuiban.teacher.present.TeacherHomePresent;
import com.bshuiban.teacher.view.webView.webActivity.MySpaceWebActivity;
import com.bshuiban.teacher.view.webView.webActivity.TeachClassWebActivity;
import com.bshuiban.teacher.view.webView.webFragment.PrepareLessonsWebFragment;
import com.bshuiban.teacher.view.webView.webFragment.TeacherHomePageFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * 学生端首页
 */
public class TeacherHomeActivity extends HomePageActivity<InteractionBaseWebViewFragment, TeacherHomePresent> implements TeacherHomeContract.View {
    private static final String BOTTOM1 = "homepage";
    private static final String BOTTOM2 = "prepare";
    private static final String BOTTOM3 = "report";
    private ImageView iv_head;
    private TextView tv_text, tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new TeacherHomePresent(this);
        tPresent.getUserDataForInternet();
    }

    @Override
    protected void initNavigationView(NavigationView navigationView) {
        View view = navigationView.inflateHeaderView(R.layout.nav_teacher_header_home_page);
        navigationView.inflateMenu(R.menu.activity_home_page_teacher_drawer);
        //头像
        iv_head = (ImageView) view.findViewById(R.id.iv_head);
        //简介
        tv_text = (TextView) view.findViewById(R.id.tv_text);
        //名字
        tv_name = (TextView) view.findViewById(R.id.tv_name);
    }

    @Override
    protected void initBottomBar(BottomBar bottomBar) {
        bottomBar.addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_home, "首页"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_work_u, "备课"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_report_u, "报告"));
                //.addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_error_u, "错题"));
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
                    case 1://备课
                        startFragment(BOTTOM2, null);
                        break;
                    case 2://报告
                        startFragment(BOTTOM3, null);
                        break;
//                    case 3://错题
//                        startFragment(BOTTOM4, null);
//                        break;
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
        Class<?> cls;
        if (id==R.id.nav_teach_class) {
            cls = TeachClassWebActivity.class;
        }else if(id==R.id.nav_my_space){
            cls= MySpaceWebActivity.class;
        }else if (id == R.id.nav_gallery) {
            cls= CollectionListActivity.class;
        } else if (id == R.id.nav_about_self) {
            cls= AboutSelfActivity.class;
        } else if (id == R.id.nav_opinion) {
            cls=OpinionActivity.class;
        } else {//nav_clear_cache
            cls= CleanCacheActivity.class;
        }
        if(null!=cls) {
            startActivity(new Intent(this, cls));
        }
    }

    @Override
    protected InteractionBaseWebViewFragment getFragment(String tag, Bundle bundle) {
        InteractionBaseWebViewFragment fragment = null;
        switch (tag) {
            case BOTTOM1:
                fragment = new TeacherHomePageFragment();
                break;
            case BOTTOM2:
                fragment = new PrepareLessonsWebFragment();
                break;
            case BOTTOM3:
                fragment = new ReportFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public void updateSlideView(TeacherUser.DataBean data) {
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

    @Override
    public void transportData(String tag) {
        if("toggleSlide".equals(tag)){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }else{
                drawer.openDrawer(GravityCompat.START);
            }
        }
    }
}
