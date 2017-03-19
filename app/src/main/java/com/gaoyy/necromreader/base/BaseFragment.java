package com.gaoyy.necromreader.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaoyy.necromreader.R;

/**
 * Created by gaoyy on 2017/3/11 0011.
 */

public abstract class BaseFragment extends LazyFragment
{
    private static final String LOG_TAG = BaseFragment.class.getSimpleName();
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private View rootView;
    private int toolbarColor = R.color.colorPrimary;
    protected BaseActivity activity;

    /**
     * 设置Fragment的layout布局
     *
     * @return
     */
    protected abstract int getFragmentLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            //get Params here
            getParamsData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(getFragmentLayoutId(), container, false);
        }
        activity = (BaseActivity)getActivity();

        isPrepared = true;
        lazyLoad();
        return rootView;
    }

    @Override
    protected void lazyLoad()
    {
        if (!isPrepared || !isVisible)
        {
            return;
        }
        assignViews(rootView);
        initToolbar();
        configViews();
    }

    /**
     * 获取传递过来的参数
     */
    protected void getParamsData()
    {

    }

    protected void assignViews(View rootView)
    {

    }

    protected void initToolbar()
    {

    }

    protected void configViews()
    {
    }

    /**
     * @param toolbar
     * @param titleId      string id
     * @param enabled      toolbar返回键是否可用，true-可用，false-不可用
     * @param toolbarColor toolbar背景颜色
     */
    public void initToolbar(Toolbar toolbar, int titleId, boolean enabled, int toolbarColor)
    {
        if (-1 == toolbarColor)
        {
            toolbarColor = this.toolbarColor;
        }
//        BaseActivity activity = ((BaseActivity) getActivity());
        //设置toolbat标题
        toolbar.setTitle(titleId);
        //设置toolbar背景色
        toolbar.setBackgroundColor(getResources().getColor(toolbarColor));
        activity.setSupportActionBar(toolbar);
        //设置toolbar返回键是否可用
        activity.getSupportActionBar().setHomeButtonEnabled(enabled);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
    }
    /**
     * @param toolbar
     * @param title      string
     * @param enabled      toolbar返回键是否可用，true-可用，false-不可用
     * @param toolbarColor toolbar背景颜色
     */
    public void initToolbar(Toolbar toolbar, String title, boolean enabled, int toolbarColor)
    {
        if (-1 == toolbarColor)
        {
            toolbarColor = this.toolbarColor;
        }
//        BaseActivity activity = ((BaseActivity) getActivity());
        //设置toolbat标题
        toolbar.setTitle(title);
        //设置toolbar背景色
        toolbar.setBackgroundColor(getResources().getColor(toolbarColor));
        activity.setSupportActionBar(toolbar);
        //设置toolbar返回键是否可用
        activity.getSupportActionBar().setHomeButtonEnabled(enabled);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
    }
}
