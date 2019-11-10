package com.moudle.necromreader.newsdetail;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.moudle.necromreader.R;
import com.moudle.necromreader.base.BaseFragment;

/**
 * Created by moudle on 2017/3/18 0018.
 */

public class NewsDetailFragment extends BaseFragment implements NewsDetailContract.View
{
    private static final String LOG_TAG = NewsDetailFragment.class.getSimpleName();
    private NewsDetailContract.Presenter mNewsDetailPresenter;
    private String newsUrl;
    private WebView newsDetailWebview;
    private ProgressBar newsDetailProgressbar;

    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_news_detail;
    }


    public NewsDetailFragment()
    {

    }

    public static NewsDetailFragment newInstance()
    {
        NewsDetailFragment fragment = new NewsDetailFragment();
        return fragment;
    }


    @Override
    protected void getParamsData()
    {
        super.getParamsData();
        newsUrl = getArguments().getString("url");
        Log.e(LOG_TAG, "newsUrl getParamsData-->" + newsUrl);

    }

    @Override
    protected void assignViews(View rootView)
    {
        super.assignViews(rootView);
        newsDetailProgressbar = (ProgressBar) rootView.findViewById(R.id.news_detail_progressbar);
        newsDetailWebview = (WebView) rootView.findViewById(R.id.news_detail_webview);
    }

    @Override
    protected void configViews()
    {
        super.configViews();
        mNewsDetailPresenter.showNewsInWebView(newsDetailWebview,newsUrl);
    }

    @Override
    public void showLoading(String msg)
    {
        newsDetailProgressbar.setVisibility(View.VISIBLE);
        newsDetailWebview.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading()
    {
        newsDetailProgressbar.setVisibility(View.GONE);
        newsDetailWebview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.e(LOG_TAG, "onResume");
        mNewsDetailPresenter.start();
    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter)
    {
        Log.e(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mNewsDetailPresenter = presenter;
        }
    }

    @Override
    public boolean isActive()
    {
        return isAdded();
    }
}
