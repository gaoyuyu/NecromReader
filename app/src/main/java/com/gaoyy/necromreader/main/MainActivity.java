package com.gaoyy.necromreader.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.base.BaseActivity;
import com.gaoyy.necromreader.gankio.GankFragment;
import com.gaoyy.necromreader.gankio.GankPresenter;
import com.gaoyy.necromreader.mydownload.MyDownloadActivity;
import com.gaoyy.necromreader.util.ActivityUtils;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavView;

    private Fragment currentFragment;
    private HomeFragment homeFragment;
    private GankFragment gankFragment;


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
    protected void configViews(Bundle savedInstanceState)
    {
        super.configViews(savedInstanceState);

        mainNavView.setNavigationItemSelectedListener(this);

        showDefaultFragment(savedInstanceState);
    }

    /**
     * 显示默认的Fragment
     */
    private void showDefaultFragment(Bundle savedInstanceState)
    {
        int[] newsType = {R.string.top, R.string.shehui, R.string.guonei, R.string.guoji,
                R.string.yule, R.string.tiyu, R.string.junshi, R.string.keji, R.string.caijing, R.string.shishang};
        if (savedInstanceState == null)
        {
            if (homeFragment == null)
            {
                homeFragment = HomeFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("title", "头条新闻");
                bundle.putIntArray("tabType", newsType);
                homeFragment.setArguments(bundle);
                homeFragment.setUserVisibleHint(true);
            }
            currentFragment = homeFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homeFragment, R.id.main_content);
        }
        //初始化MainPresenter
        new MainPresenter(homeFragment);
    }


    /**
     * 当fragment进行切换时，采用隐藏与显示的方法加载fragment以防止数据的重复加载
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to)
    {
        if (currentFragment != to)
        {
            currentFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (!to.isAdded())
            {    // 先判断是否被add过
                ft.hide(from).add(R.id.main_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            }
            else
            {
                ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
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
                if (!item.isChecked())
                {
                    int[] newsType = {R.string.top, R.string.shehui, R.string.guonei, R.string.guoji,
                            R.string.yule, R.string.tiyu, R.string.junshi, R.string.keji, R.string.caijing, R.string.shishang};
                    if (homeFragment == null)
                    {
                        homeFragment = HomeFragment.newInstance();
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "头条新闻");
                        bundle.putIntArray("tabType", newsType);
                        homeFragment.setArguments(bundle);
                        homeFragment.setUserVisibleHint(true);
                    }
                    switchContent(currentFragment, homeFragment);
                    //初始化MainPresenter
                    new MainPresenter(homeFragment);

                }
                break;
            case R.id.nav_gank:
                int[] gankType = {R.string.android, R.string.ios, R.string.front_web, R.string.photo};
                if (gankFragment == null)
                {
                    gankFragment = GankFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "干活集中营");
                    bundle.putIntArray("tabType", gankType);
                    gankFragment.setArguments(bundle);
                    gankFragment.setUserVisibleHint(true);
                }
                switchContent(currentFragment, gankFragment);
                new GankPresenter(gankFragment);
                break;

            case R.id.nav_my_download:
                Intent myDownload = new Intent(MainActivity.this, MyDownloadActivity.class);
                startActivity(myDownload);
                break;
        }


        mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
