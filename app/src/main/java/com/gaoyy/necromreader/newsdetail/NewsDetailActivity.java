package com.gaoyy.necromreader.newsdetail;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.base.BaseActivity;
import com.gaoyy.necromreader.util.ActivityUtils;

public class NewsDetailActivity extends BaseActivity
{
    private static final String LOG_TAG = NewsDetailActivity.class.getSimpleName();
    private String newsTiltle;
    private String newsUrl;
    private Toolbar newsDetailToolbar;

    @Override
    protected void initContentView()
    {
        setContentView(R.layout.activity_news_detail);
    }

    @Override
    protected void assignViews()
    {
        super.assignViews();
        newsDetailToolbar = (Toolbar) findViewById(R.id.news_detail_toolbar);
    }

    @Override
    protected void initToolbar()
    {
        newsTiltle = getIntent().getStringExtra("title");
        newsUrl = getIntent().getStringExtra("url");
        super.initToolbar(newsDetailToolbar, newsTiltle, true, -1);
    }

    @Override
    protected void configViews()
    {
        super.configViews();
        NewsDetailFragment newsDetailFragment = (NewsDetailFragment) getSupportFragmentManager().findFragmentById(R.id.news_detail_content);
        if (newsDetailFragment == null)
        {
            newsDetailFragment = NewsDetailFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("url",newsUrl);
            newsDetailFragment.setArguments(bundle);
//            newsDetailFragment.setUserVisibleHint(true);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), newsDetailFragment, R.id.news_detail_content);
        }

        new NewsDetailPresenter(newsDetailFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    finishAfterTransition();
                }
                else
                {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
