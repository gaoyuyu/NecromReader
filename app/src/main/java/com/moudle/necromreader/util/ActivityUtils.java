package com.moudle.necromreader.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils
{

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId)
    {
        fragment.setUserVisibleHint(true);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void addFragment(FragmentManager fragmentManager, int containerViewId, Fragment fragment, String tag)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    public static void replaceFragment(FragmentManager fragmentManager,int containerViewId, Fragment fragment, String tag) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            fragmentManager.popBackStack(tag, 0);
        }
    }

}
