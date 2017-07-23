package com.gaoyy.necromreader.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.adapter.NewsListAdapter;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.api.bean.NewsInfo;
import com.gaoyy.necromreader.base.BaseFragment;
import com.gaoyy.necromreader.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyy on 2017/3/12 0012.
 */

public class NewsFragment extends BaseFragment implements NewsContract.View, NewsListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener
{
    private static final String LOG_TAG = NewsFragment.class.getSimpleName();
    private NewsContract.Presenter mNewsPresenter;
    private SwipeRefreshLayout newsSwipeRefreshLayout;
    private RecyclerView newsRv;
    private ProgressBar newsProgressBar;
    private NewsListAdapter newsListAdapter;
    private List<NewsInfo.ResultBean.DataBean> list = new ArrayList<>();

    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_news;
    }

    public NewsFragment()
    {

    }

    public static NewsFragment newInstance()
    {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    protected void getParamsData()
    {
        super.getParamsData();
    }

    @Override
    protected void assignViews(View rootView)
    {
        super.assignViews(rootView);

        newsSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.news_swipeRefreshLayout);
        newsRv = (RecyclerView) rootView.findViewById(R.id.news_rv);
        newsProgressBar = (ProgressBar) rootView.findViewById(R.id.news_progressBar);
    }

    @Override
    protected void configViews()
    {
        super.configViews();
        newsListAdapter = new NewsListAdapter(activity, list);
        newsRv.setAdapter(newsListAdapter);
        newsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置刷新时动画的颜色
        newsSwipeRefreshLayout = CommonUtils.setProgressBackgroundColor(activity, newsSwipeRefreshLayout);
        mNewsPresenter = new NewsPresenter(this);
        Map<String, String> map = new HashMap<>();
        if (isAdded())
        {
            //若不加isAdded就调用activity获取资源，会报异常，Fragment  not attached to Activity
            map.put("type", activity.getResources().getString(getArguments().getInt("type")));
            map.put("key", Constant.APPKEY);
            mNewsPresenter.loadNewsData(map);
        }
        newsListAdapter.setOnItemClickListener(this);
        newsSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showLoading()
    {
        newsProgressBar.setVisibility(View.VISIBLE);
        newsSwipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading()
    {
        newsProgressBar.setVisibility(View.GONE);
        newsSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishRefresh()
    {
        if (newsSwipeRefreshLayout.isRefreshing())
        {
            newsSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void showNews(List<NewsInfo.ResultBean.DataBean> list)
    {
        newsListAdapter.updateData(list);
    }

    @Override
    public boolean isActive()
    {
        return isAdded();
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mNewsPresenter = presenter;
        }
    }

    @Override
    public void onItemClick(View view, int ition)
    {
        NewsInfo.ResultBean.DataBean news = (NewsInfo.ResultBean.DataBean) view.getTag();
        mNewsPresenter.onItemClick(activity, news);
    }

    @Override
    public void onRefresh()
    {
        Map<String, String> map = new HashMap<>();
        map.put("type", getResources().getString(getArguments().getInt("type")));
        map.put("key", Constant.APPKEY);
        mNewsPresenter.loadNewsData(map);
    }
}
