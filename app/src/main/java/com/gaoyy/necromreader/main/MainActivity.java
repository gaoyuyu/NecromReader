package com.gaoyy.necromreader.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.base.BaseActivity;
import com.gaoyy.necromreader.util.ActivityUtils;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavView;


    @Override
    protected void initContentView()
    {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void assignViews()
    {
        super.assignViews();
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mainNavView = (NavigationView) findViewById(R.id.main_nav_view);
    }

    @Override
    protected void configViews()
    {
        super.configViews();

        mainNavView.setNavigationItemSelectedListener(this);

        showDefaultFragment();
    }

    /**
     * 显示默认的Fragment
     */
    private void showDefaultFragment()
    {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.main_content);
        int[] newsType = {R.string.top, R.string.shehui, R.string.guonei, R.string.guoji,
                R.string.yule, R.string.tiyu, R.string.junshi, R.string.keji, R.string.caijing, R.string.shishang};
        if (homeFragment == null)
        {
            homeFragment = HomeFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("titleId", R.string.app_name);
            bundle.putIntArray("newsType", newsType);
            homeFragment.setArguments(bundle);
            /**
             * 由于是BaseFragment是懒加载，add HomeFragment之前isVisibleToUser为false
             * 所以HomeFragment的UI没有渲染出来，这里需要手动设置为true，
             * 告诉LazyFragment HomeFragment是可见的
             */
//            homeFragment.setUserVisibleHint(true);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homeFragment, R.id.main_content);
        }
        //初始化MainPresenter
        new MainPresenter(homeFragment);
    }

    @Override
    public void onBackPressed()
    {
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mainDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_news:
                int[] newsType = {R.string.top, R.string.shehui, R.string.guonei, R.string.guoji,
                        R.string.yule, R.string.tiyu, R.string.junshi, R.string.keji, R.string.caijing, R.string.shishang};
                break;
        }


        mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
