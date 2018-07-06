package com.bshuiban.baselibrary.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.internet.RetrofitDownload;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.present.ImageUploadHeadPresent;
import com.bshuiban.baselibrary.utils.DialogUtils;
import com.bshuiban.baselibrary.utils.ScreenUtils;
import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;
import com.bshuiban.baselibrary.view.customer.BottomBar;
import com.bshuiban.baselibrary.view.webview.webActivity.TestWebViewActivity;

/**
 * 首页
 */
public abstract class HomePageActivity<T extends Fragment, P extends BasePresent> extends BaseFragmentActivity<T, P> {
    protected int mNowPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomBar rg_container = (BottomBar) findViewById(R.id.rg_container);
        FrameLayout head = (FrameLayout) findViewById(R.id.head);
        head.setOnClickListener(v -> {});
        LinearLayout ll_parent = (LinearLayout) findViewById(R.id.ll_parent);
        TextView tv_login_out = (TextView) findViewById(R.id.tv_login_out);
        tv_login_out.setOnClickListener(v -> DialogUtils.showMessageSureCancelDialog(this, "确定退出登录？", v1 -> {
            try {
                Class<?> cls = Class.forName("com.bshuiban.view.webActivity.LoginWebActivity");
                UserSharedPreferencesUtils.cleanUserData(getApplicationContext());
                startActivity(new Intent(getApplicationContext(), cls));
                finish();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }));
        initNavigationView(head, ll_parent);
        //ll_parent.addView(getSlideView(R.mipmap.app_logo,"H5测试","test",ll_parent));
        initBottomBar(rg_container);
        new ImageUploadHeadPresent(null).loadImageHead();
    }

    protected abstract void initNavigationView(FrameLayout rl, LinearLayout navigationView);

    protected abstract void initBottomBar(BottomBar bottomBar);
    private boolean exit;
    private Handler handler=new Handler();
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(exit){
                android.os.Process.killProcess(android.os.Process.myPid());
                return;
            }else {
                Toast toast=Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, ScreenUtils.getScreenHeight(this)/5);
                toast.show();
                exit=true;
                handler.postDelayed(()->exit=false,3500);
                return;
            }
        }
        super.onBackPressed();
    }

    protected View getSlideView(int imgId, String s, String tag, ViewGroup viewGroup) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_slide_item, viewGroup, false);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ImageView iv = view.findViewById(R.id.iv);
        iv.setImageResource(imgId);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(s);
        view.setTag(tag);
        view.setOnClickListener(onClickListener);
        return view;
    }

    private View.OnClickListener onClickListener = v -> {
        String tag = (String) v.getTag();
        if (tag.equals("test")) {
            startActivity(new Intent(getApplicationContext(), TestWebViewActivity.class));
        } else {
            itemSelectedId(tag);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    };
    /*@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        *//*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*//*
        itemSelectedId(id);
        item.setCheckable(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    /**
     * 处理menu的点击
     *
     * @param tag view的id
     */
    protected abstract void itemSelectedId(String tag);

    @Override
    protected int getFragmentContainerViewId() {
        return R.id.fl_fragment;
    }

}
