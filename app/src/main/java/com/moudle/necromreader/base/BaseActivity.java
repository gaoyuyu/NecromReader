package com.moudle.necromreader.base;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.moudle.necromreader.R;
import com.moudle.necromreader.api.Constant;
import com.moudle.necromreader.main.MainActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by moudle on 2017/3/11 0011.
 */

public abstract class BaseActivity extends AppCompatActivity
{
    private int toolbarColor = R.color.colorPrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (this instanceof MainActivity)
            {
                Log.d(Constant.TAG, "===MainActivity status bar=====");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            else
            {
                Log.d(Constant.TAG, "===not MainActivity status bar=====");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        else
        {
            Log.d(Constant.TAG, "===api 19=====");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                setTranslucentStatus(true);
            }

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }


        //加载布局
        initContentView();
        //初始化view
        assignViews();
        //初始化toolbar
        initToolbar();
        //配置views
        configViews(savedInstanceState);
        //设置监听器
        setListener();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(boolean on)
    {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on)
        {
            winParams.flags |= bits;
        }
        else
        {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected abstract void initContentView();

    protected void assignViews()
    {

    }

    protected void initToolbar()
    {

    }

    protected void configViews(Bundle savedInstanceState)
    {

    }

    protected void setListener()
    {

    }


    /**
     * @param toolbar
     * @param titleId      string id
     * @param enabled      toolbar返回键是否可用，true-可用，false-不可用
     * @param toolbarColor toolbar背景颜色，-1为默认色
     */
    public void initToolbar(Toolbar toolbar, int titleId, boolean enabled, int toolbarColor)
    {
        if (-1 == toolbarColor)
        {
            toolbarColor = this.toolbarColor;
        }
        //设置toolbat标题
        toolbar.setTitle(titleId);
        //设置toolbar背景色
        toolbar.setBackgroundColor(getResources().getColor(toolbarColor));
        setSupportActionBar(toolbar);
        //设置toolbar返回键是否可用
        getSupportActionBar().setHomeButtonEnabled(enabled);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
    }

    /**
     * @param toolbar
     * @param title        string id
     * @param enabled      toolbar返回键是否可用，true-可用，false-不可用
     * @param toolbarColor toolbar背景颜色，-1为默认色
     */
    public void initToolbar(Toolbar toolbar, String title, boolean enabled, int toolbarColor)
    {
        if (-1 == toolbarColor)
        {
            toolbarColor = this.toolbarColor;
        }
        //设置toolbat标题
        toolbar.setTitle(title);
        //设置toolbar背景色
        toolbar.setBackgroundColor(getResources().getColor(toolbarColor));
        setSupportActionBar(toolbar);
        //设置toolbar返回键是否可用
        getSupportActionBar().setHomeButtonEnabled(enabled);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
    }

}
