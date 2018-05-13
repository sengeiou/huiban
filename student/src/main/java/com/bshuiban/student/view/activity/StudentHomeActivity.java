package com.bshuiban.student.view.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bshuiban.baselibrary.view.activity.HomePageActivity;
import com.bshuiban.baselibrary.view.fragment.BaseFragment;
import com.bshuiban.baselibrary.view.fragment.InteractionBaseFragment;
import com.bshuiban.student.R;

/**
 * 学生端首页
 */
public class StudentHomeActivity extends HomePageActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initNavigationView(NavigationView navigationView) {

    }

    @Override
    protected void initRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < 4; i++) {
            RadioButton radioButton = getRadioButton();
            //radioButton.setButtonDrawable(ViewUtils.getSeleDrawable(this, R.mipmap.ic_launcher,R.mipmap.ic_launcher_round));
            radioButton.setText(i+"ads");
            radioButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            radioGroup.addView(radioButton);
        }
    }

    @Override
    protected void itemSelectedId(int id) {

    }

    @Override
    protected InteractionBaseFragment getFragment(String tag, Bundle bundle) {
        return null;
    }
}
