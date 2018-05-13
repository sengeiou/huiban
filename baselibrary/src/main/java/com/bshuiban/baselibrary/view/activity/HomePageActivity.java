package com.bshuiban.baselibrary.view.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.fragment.InteractionBaseFragment;

/**
 * 首页
 */
public abstract class HomePageActivity extends BaseFragmentActivity<InteractionBaseFragment>
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        RadioGroup rg_container = (RadioGroup) findViewById(R.id.rg_container);
        navigationView.setNavigationItemSelectedListener(this);
        initNavigationView(navigationView);
        initRadioGroup(rg_container);
    }
    protected abstract void initNavigationView(NavigationView navigationView);
    protected abstract void initRadioGroup(RadioGroup radioGroup);
    @SuppressLint("ResourceType")
    protected RadioButton getRadioButton(){
        RadioButton radioButton=new RadioButton(this);
        radioButton.setLayoutParams(new RadioGroup.LayoutParams(0,RadioGroup.LayoutParams.WRAP_CONTENT,1));
        //radioButton.setText();
        radioButton.setTextColor(getResources().getColorStateList(R.drawable.home_pager_color));
        return radioButton;
    }
    protected StateListDrawable getStateListDrawable(int selectedId,int unSelectedId){
        return ViewUtils.getSeleDrawable(getApplicationContext(),selectedId,unSelectedId);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/
        itemSelectedId(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 处理menu的点击
     * @param id view的id
     */
    protected abstract void itemSelectedId(int id);
    @Override
    protected int getFragmentContainerViewId() {
        return R.id.fl_fragment;
    }
}