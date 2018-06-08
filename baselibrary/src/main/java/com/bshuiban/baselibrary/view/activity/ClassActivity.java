package com.bshuiban.baselibrary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.view.adapter.ClassViewPagerAdapter;
import com.bshuiban.baselibrary.view.customer.TitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

/**
 * 班级容器
 */
public class ClassActivity extends BaseActivity {

    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        titleView = findViewById(R.id.titleView);
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("概况");
        arrayList.add("学习动态");
        arrayList.add("班级活动");
        arrayList.add("班级课表");
        String className = User.getInstance().getClassName();
        titleView.setTitle_text(className);
        titleView.setOnClickListener(new TitleView.OnClickListener() {
            @Override
            public void leftClick(View v) {
                finish();
            }

            @Override
            public void rightClick(View v) {
                if(User.getInstance().isTeacher()){
                    //SendClassActivityWebActivity
                    try {
                        Class<?> aClass = Class.forName("com.bshuiban.teacher.view.webView.webActivity.SendClassActivityWebActivity");
                        Intent intent=new Intent(getApplicationContext(),aClass);
                        startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        MagicIndicator magicIndicator=findViewById(R.id.magic);
        ViewPager viewPager=findViewById(R.id.viewPager);

        final ClassViewPagerAdapter classViewPagerAdapter=new ClassViewPagerAdapter(getSupportFragmentManager(),arrayList);
        viewPager.setAdapter(classViewPagerAdapter);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator=new CommonNavigator(this);
        CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(getApplication());
                simplePagerTitleView.setNormalColor(Color.BLACK);
                simplePagerTitleView.setPadding(15,0,15,0);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.guide_start_btn));
                simplePagerTitleView.setTextSize(getResources().getDimension(R.dimen.dp_6));
                simplePagerTitleView.setText(arrayList.get(index));
                simplePagerTitleView.setOnClickListener((v)->{
                    viewPager.setCurrentItem(index,false);
                });
                if(User.getInstance().isTeacher()&&index==2){
                    titleView.setRight_text("新建",Color.WHITE, (int) getResources().getDimension(R.dimen.dp_14));
                }else {
                    titleView.setRight_text(null,Color.WHITE, (int) getResources().getDimension(R.dimen.dp_14));
                }
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(getApplication());
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.guide_start_btn));
                return linePagerIndicator;
            }
        };
        commonNavigator.setAdapter(commonNavigatorAdapter);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator,viewPager);
    }
}
