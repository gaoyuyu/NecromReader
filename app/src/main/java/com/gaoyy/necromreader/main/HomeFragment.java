package com.gaoyy.necromreader.main;


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
import com.gaoyy.necromreader.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements MainContract.View
{
    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    //toolbar的标题
    private String title;
    //tab列表数据
    private int[] tabType;
    private Toolbar homeToolbar;
    private TabLayout homeTablayout;
    private ViewPager homeViewpager;
    private NewsPagerAdapter newsPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();


    private MainContract.Presenter mMainPresenter;

    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_home;
    }

    public HomeFragment()
    {
        // Required empty public constructor
    }

    public static HomeFragment newInstance()
    {
        HomeFragment fragment = new HomeFragment();
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
    protected void assignViews(View rootView)
    {
        super.assignViews(rootView);
        homeToolbar = (Toolbar) rootView.findViewById(R.id.home_toolbar);
        homeTablayout = (TabLayout) rootView.findViewById(R.id.home_tablayout);
        homeViewpager = (ViewPager) rootView.findViewById(R.id.home_viewpager);
    }

    @Override
    protected void initToolbar()
    {
        super.initToolbar(homeToolbar, title, false, -1);
    }

    @Override
    protected void configViews()
    {
        super.configViews();
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, homeToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        for (int i = 0; i < tabType.length; i++)
        {
            Bundle bundle = new Bundle();
            Fragment fragment = new NewsFragment();
            bundle.putInt("type", tabType[i]);
            fragment.setArguments(bundle);
            fragmentList.add(i, fragment);
        }
        newsPagerAdapter = new NewsPagerAdapter(activity, getChildFragmentManager(), tabType, fragmentList);
        homeViewpager.setAdapter(newsPagerAdapter);
        homeViewpager.setOffscreenPageLimit(1);
        homeTablayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        homeTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeTablayout.setupWithViewPager(homeViewpager);
        homeTablayout.setTabsFromPagerAdapter(newsPagerAdapter);

    }


    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
        mMainPresenter.start();
        newsPagerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean isActive()
    {
        return isAdded();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mMainPresenter = presenter;
        }
    }
}
