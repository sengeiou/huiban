package com.bshuiban.baselibrary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.TeachClassContract;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.TeachClassBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.TeachClassPresent;
import com.bshuiban.baselibrary.utils.ClassChange;
import com.bshuiban.baselibrary.view.adapter.ClassViewPagerAdapter;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bshuiban.baselibrary.view.fragment.ClassActivityFragment;
import com.bshuiban.baselibrary.view.fragment.ClassScheduleFragment;
import com.bshuiban.baselibrary.view.fragment.GeneralSituationFragment;
import com.xinheng.date_dialog.dialog.SeleTwoDialog;
import com.xinheng.date_dialog.dialog.SelectDateDialog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级容器
 */
public class ClassActivity extends BaseActivity<TeachClassPresent> implements TeachClassContract.View {

    private TitleView titleView;
    private List<TeachClassBean.DataBean> data;
    private ClassViewPagerAdapter classViewPagerAdapter;
    private int mPosition;
    private String className;
    private ClassChange classChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        titleView = findViewById(R.id.titleView);
        if (User.getInstance().isTeacher()) {
            tPresent = new TeachClassPresent(this);
            tPresent.loadTeachClass();
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("概况");
        arrayList.add("学习动态");
        arrayList.add("班级活动");
        arrayList.add("班级课表");
        className = User.getInstance().getClassName();
        if (TextUtils.isEmpty(className)) {
            className = "我的班级";
        }
        titleView.setTitle_text(className);
        titleView.setOnClickListener(new TitleView.OnClickListener() {
            @Override
            public void leftClick(View v) {
                finish();
            }

            @Override
            public void rightClick(View v) {
                if (User.getInstance().isTeacher()) {
                    //SendClassActivityWebActivity
                    try {
                        Class<?> aClass = Class.forName("com.bshuiban.teacher.view.webView.webActivity.SendClassActivityWebActivity");
                        Intent intent = new Intent(getApplicationContext(), aClass);
                        startActivityForResult(intent,100);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void centerClick(View v) {
                if (User.getInstance().isTeacher()&&HomeworkBean.isEffictive(data)) {
                    startClassDialog();
                }
            }
        });
        MagicIndicator magicIndicator = findViewById(R.id.magic);
        ViewPager viewPager = findViewById(R.id.viewPager);
        classChange = new ClassChange();
        classViewPagerAdapter = new ClassViewPagerAdapter(getSupportFragmentManager(), arrayList, classChange);
        viewPager.setAdapter(classViewPagerAdapter);
        magicIndicator.setBackgroundColor(Color.WHITE);

        CommonNavigator commonNavigator = new CommonNavigator(this) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (User.getInstance().isTeacher()) {
                    mPosition=position;
                    switch (position) {
                        case 2:
                            titleView.setTitle_text(className);
                            titleView.setRight_text("新建", Color.WHITE, (int) getResources().getDimension(R.dimen.dp_14));
                            //titleView.setCompoundDrawablesWithIntrinsicBounds(null,null, ContextCompat.getDrawable(getApplicationContext(),R.mipmap.add_activity));
                            //titleView.invalidate();
                            break;
                        case 3:
                            titleView.setTitle_text(className);
                            titleView.setRight_text(null, Color.WHITE, (int) getResources().getDimension(R.dimen.dp_14));
                            break;
                        case 1:
                            titleView.setTitle_text("学习动态");
                            titleView.setRight_text(null, Color.WHITE, (int) getResources().getDimension(R.dimen.dp_14));
                            break;
                        default:
                            titleView.setTitle_text(className);
                            titleView.setRight_text(null, Color.WHITE, (int) getResources().getDimension(R.dimen.dp_14));
                    }
                }
            }
        };
        CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(getApplication());
                simplePagerTitleView.setNormalColor(Color.BLACK);
                simplePagerTitleView.setPadding(15, 0, 15, 0);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.guide_start_btn));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.dp_4));
                simplePagerTitleView.setText(arrayList.get(index));
                simplePagerTitleView.setOnClickListener((v) -> {
                    viewPager.setCurrentItem(index, false);
                });

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
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void startClassDialog() {
        List<String> list=new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String className = data.get(i).getClassName();
            list.add(className);
        }
        new SeleTwoDialog(this, new SeleTwoDialog.OnClickListener() {
            @Override
            public boolean onSure(String left, int leftIndex, String right, int rightIndex) {
                TeachClassBean.DataBean dataBean = data.get(leftIndex);
                String classId = dataBean.getClassId();
//                Fragment fragment = classViewPagerAdapter.getFragment(mPosition);
//                if(fragment instanceof ClassScheduleFragment) {
//                    ((ClassScheduleFragment) fragment).updateSchedule(classId);
//                }else if(fragment instanceof GeneralSituationFragment){
//                    ((GeneralSituationFragment) fragment).update(classId);
//                }else if(fragment instanceof ClassActivityFragment){
//                    ((ClassActivityFragment) fragment).update(classId);
//                }
                classChange.setDataBean(dataBean);
                titleView.setTitle_text(dataBean.getClassName());
                return false;
            }

            @Override
            public boolean onCancel() {
                return false;
            }
        }, list, null).show();
    }

    @Override
    public void updateData(List<TeachClassBean.DataBean> data) {
        this.data = data;
        if (HomeworkBean.isEffictive(data)) {
            className = data.get(0).getClassName();
            titleView.setTitle_text(className);
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
        toast(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                Fragment fragment = classViewPagerAdapter.getFragment(mPosition);
                if(fragment instanceof ClassActivityFragment){
                    ((ClassActivityFragment) fragment).update(null);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(classChange!=null){
            classChange.clear();
            classChange=null;
        }
        super.onDestroy();
    }
}
