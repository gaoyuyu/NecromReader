package com.moudle.necromreader.news;

import android.content.Context;
import android.content.Intent;

import com.moudle.necromreader.api.RetrofitService;
import com.moudle.necromreader.api.bean.NewsInfo;
import com.moudle.necromreader.newsdetail.NewsDetailActivity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moudle on 2017/3/12 0012.
 */

public class NewsPresenter implements NewsContract.Presenter
{
    private static final String LOG_TAG = NewsPresenter.class.getSimpleName();
    private NewsContract.View mNewsView;

    public NewsPresenter(NewsContract.View mNewsView)
    {
        this.mNewsView = mNewsView;
        mNewsView.setPresenter(this);
    }

    @Override
    public void loadNewsData(Map<String, String> map)
    {
        mNewsView.showLoading();
        Call<NewsInfo> call = RetrofitService.sNewsService.getNewsData(map);
        call.enqueue(new Callback<NewsInfo>()
        {
            @Override
            public void onResponse(Call<NewsInfo> call, Response<NewsInfo> response)
            {
                if (!mNewsView.isActive())
                {
                    return;
                }
                mNewsView.hideLoading();
                mNewsView.finishRefresh();
                if (response.isSuccessful() && response.body() != null)
                {

                    NewsInfo newsInfo = response.body();
                    if (newsInfo.getError_code() == 10012)
                    {
                        mNewsView.showToast();
                    }
                    else
                    {
                        List<NewsInfo.ResultBean.DataBean> list = response.body().getResult().getData();
                        mNewsView.showNews(list);
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsInfo> call, Throwable t)
            {
                if (!mNewsView.isActive())
                {
                    return;
                }
                mNewsView.hideLoading();
                mNewsView.finishRefresh();
            }
        });
    }

    @Override
    public void onItemClick(Context context, NewsInfo.ResultBean.DataBean news)
    {
        Intent intent = new Intent();
        intent.putExtra("title", news.getTitle());
        intent.putExtra("url", news.getUrl());
        intent.setClass(context, NewsDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void start()
    {

    }
}
