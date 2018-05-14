package com.bshuiban.baselibrary.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bshuiban.baselibrary.view.fragment.InteractionBaseFragment;
import com.bshuiban.baselibrary.view.interfaces.OnFragmentInteractionListener;

/**
 * Created by xinheng on 2018/4/25.<br/>
 * describe：fragment切换封装
 */
public abstract class BaseFragmentActivity<T extends Fragment> extends BaseActivity implements OnFragmentInteractionListener {
    protected  T tFragment;

    /**
     * 启动fragment
     * @param tag
     * @param bundle
     */
    public void startFragment(String tag, Bundle bundle){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(tag);
        if(null!=tFragment){//隐藏上一个fragment
            fragmentTransaction.hide(tFragment);
        }
        if(fragmentByTag!=null){
            fragmentTransaction.show(fragmentByTag);
            tFragment= (T) fragmentByTag;
        }else{
            tFragment = getFragment(tag, bundle);
            fragmentTransaction.add(getFragmentContainerViewId(), tFragment,tag);
        }
        fragmentTransaction.commit();
    }

    /**
     * 获取 新的 fragment
     * @param tag
     * @param bundle
     * @return
     */
    protected abstract T getFragment(String tag,Bundle bundle);

    /**
     * fragment 容器 id
     * @return
     */
    protected abstract int getFragmentContainerViewId();

    @Override
    public void onFragmentInteraction(String tag, Bundle bundle) {
        startFragment(tag,bundle);
    }
}
