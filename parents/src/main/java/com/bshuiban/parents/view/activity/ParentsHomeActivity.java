package com.bshuiban.parents.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.activity.AboutSelfActivity;
import com.bshuiban.baselibrary.view.activity.ChangeUserActivity;
import com.bshuiban.baselibrary.view.activity.CleanCacheActivity;
import com.bshuiban.baselibrary.view.activity.HomePageActivity;
import com.bshuiban.baselibrary.view.activity.OpinionActivity;
import com.bshuiban.baselibrary.view.customer.BottomBar;
import com.bshuiban.baselibrary.view.customer.BottomBarTab;
import com.bshuiban.baselibrary.view.fragment.HomeworkFragment;
import com.bshuiban.baselibrary.view.fragment.ReportFragment;
import com.bshuiban.baselibrary.view.webview.webActivity.SideCollectionListWebActivity;
import com.bshuiban.baselibrary.view.webview.webFragment.ErrorHomeworkWebFragment;
import com.bshuiban.baselibrary.view.webview.webFragment.InteractionBaseWebViewFragment;
import com.bshuiban.parents.R;
import com.bshuiban.parents.contract.ParentsHomeContract;
import com.bshuiban.parents.modul.ParentsUser;
import com.bshuiban.parents.present.ParentsHomePresent;
import com.bshuiban.parents.view.fragment.ParentsHomePageFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * 家长端首页
 */
public class ParentsHomeActivity extends HomePageActivity<InteractionBaseWebViewFragment, ParentsHomePresent> implements ParentsHomeContract.View {
    private static final String BOTTOM1 = "homepage";
    private static final String BOTTOM2 = "homework";
    private static final String BOTTOM3 = "report";
    private static final String BOTTOM4 = "wrongHomework";
    private ImageView iv_head;
    private TextView tv_text, tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null!=User.getInstance().getUserData()) {
            tPresent = new ParentsHomePresent(this);
            tPresent.getUserDataForInternet();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
//        if(intent.getBooleanExtra("change",false)) {
//            if (null == tPresent)
//                tPresent = new ParentsHomePresent(this);
//            tPresent.getUserDataForInternet();
//        }
        super.onNewIntent(intent);
    }

    @Override
    protected void initNavigationView(FrameLayout rl, LinearLayout navigationView) {
        View view = LayoutInflater.from(this).inflate(R.layout.nav_parents_header_home_page,rl,false);
        rl.addView(view,0);
        navigationView.addView(getSlideView(R.mipmap.my_shoucang,"我的收藏","nav_gallery",navigationView));
        navigationView.addView(getSlideView(R.mipmap.about_huiban,"关于慧班","nav_about_self",navigationView));
        navigationView.addView(getSlideView(R.mipmap.yijianfankui,"意见反馈","nav_opinion",navigationView));
        navigationView.addView(getSlideView(R.mipmap.cleancouche,"清理缓存","nav_clear_cache",navigationView));
        navigationView.addView(getSlideView(R.mipmap.change_user,"切换身份","nav_change_user",navigationView));


        //头像
        iv_head = (ImageView) view.findViewById(R.id.iv_head);
        //简介
        tv_text = (TextView) view.findViewById(R.id.tv_text);
        //名字
        tv_name = (TextView) view.findViewById(R.id.tv_name);
    }

    @Override
    protected void initBottomBar(BottomBar bottomBar) {
        bottomBar.addItem(new BottomBarTab(this, R.mipmap.ic_main_tab_home_u, "首页").setTabPosition(0))
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
                        startFragment(BOTTOM2, null);
                        break;
                    case 2://报告
                        startFragment(BOTTOM3, null);
                        break;
                    case 3://错题
                        startFragment(BOTTOM4, null);
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
    protected void itemSelectedId(String id) {
        Class<?> cls;
        if (id.equals("nav_gallery")) {
            cls= SideCollectionListWebActivity.class;
        } else if (id.equals("nav_about_self")) {
            cls= AboutSelfActivity.class;
        } else if (id.equals("nav_opinion")) {
            cls=OpinionActivity.class;
        } else if(id.equals("nav_change_user")){
            cls= ChangeUserActivity.class;
        }else {//nav_clear_cache
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
                fragment = new ParentsHomePageFragment();
                break;
            case BOTTOM2:
                fragment = new HomeworkFragment();
                break;
            case BOTTOM3:
                fragment = new ReportFragment();
                break;
            case BOTTOM4:
                fragment = new ErrorHomeworkWebFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public void updateSlideView(ParentsUser.DataBean data) {
        if (null != data) {
            String realName = data.getRealName();
            tv_name.setText(com.bshuiban.baselibrary.utils.TextUtils.cleanNull(realName)+"家长");
            String schoolName = com.bshuiban.baselibrary.utils.TextUtils.cleanNull(data.getSchoolName());
            //2018年7月12日应产品要求，去除年级
            String gradleName = "";//com.bshuiban.baselibrary.utils.TextUtils.cleanNull(data.getGradeName());
            List<String> classNames = data.getClassName();
            String className = "";
            if (null != classNames && classNames.size() > 0) {
                className = com.bshuiban.baselibrary.utils.TextUtils.cleanNull(classNames.get(0));
            }
            tv_text.setText(schoolName + gradleName + className);
            String icoPath = data.getIcoPath();
            if (TextUtils.isEmpty(icoPath)) {
                iv_head.setImageResource(R.mipmap.default_head);
            } else {
                RequestOptions requestOptions = new RequestOptions()
                        .circleCrop()
                        .error(R.mipmap.default_head);
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
