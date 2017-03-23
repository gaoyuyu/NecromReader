package com.gaoyy.necromreader.gankio;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.adapter.NewsPagerAdapter;
import com.gaoyy.necromreader.base.BaseFragment;
import com.gaoyy.necromreader.gankio.photo.PhotoFragment;
import com.gaoyy.necromreader.gankio.photo.PhotoPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyy on 2017/3/19 0019.
 */

public class GankFragment extends BaseFragment implements GankContract.View
{

    private static final String LOG_TAG = GankFragment.class.getSimpleName();
    private String title;
    private int[] tabType;
    private Toolbar gankToolbar;
    private TabLayout gankTablayout;
    private ViewPager gankViewpager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private NewsPagerAdapter newsPagerAdapter;
    private GankContract.Presenter mGankPresenter;

    public GankFragment()
    {

    }

    public static GankFragment newInstance()
    {
        GankFragment fragment = new GankFragment();
        return fragment;
    }

    @Override
    protected void getParamsData()
    {
        super.getParamsData();
        title = getArguments().getString("title");
        tabType = getArguments().getIntArray("tabType");
    }

    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_gank;
    }

    @Override
    protected void assignViews(View rootView)
    {
        super.assignViews(rootView);
        gankToolbar = (Toolbar) rootView.findViewById(R.id.gank_toolbar);
        gankTablayout = (TabLayout) rootView.findViewById(R.id.gank_tablayout);
        gankViewpager = (ViewPager) rootView.findViewById(R.id.gank_viewpager);
    }


    @Override
    protected void initToolbar()
    {
        super.initToolbar(gankToolbar, title, true, -1);
    }

    @Override
    protected void configViews()
    {
        super.configViews();
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, gankToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        for (int i = 0; i < tabType.length; i++)
        {
            Bundle bundle = new Bundle();
            PhotoFragment fragment = new PhotoFragment();
            new PhotoPresenter(fragment);
            bundle.putInt("type",tabType[i]);
            fragment.setArguments(bundle);
            fragmentList.add(i, fragment);
        }

        newsPagerAdapter = new NewsPagerAdapter(activity, getChildFragmentManager(), tabType, fragmentList);
        gankViewpager.setAdapter(newsPagerAdapter);
        gankViewpager.setOffscreenPageLimit(1);
        gankTablayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        gankTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        gankTablayout.setupWithViewPager(gankViewpager);
        gankTablayout.setTabsFromPagerAdapter(newsPagerAdapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(LOG_TAG,"onResume");
        mGankPresenter.start();
    }


    @Override
    public boolean isActive()
    {
        return isAdded();
    }


    @Override
    public void setPresenter(GankContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mGankPresenter = presenter;
        }
    }
}
