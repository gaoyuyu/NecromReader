package com.gaoyy.necromreader.news;

import android.content.Context;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;

import com.gaoyy.necromreader.api.RetrofitService;
import com.gaoyy.necromreader.api.bean.NewsInfo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gaoyy on 2017/3/12 0012.
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
        Call<NewsInfo> call = RetrofitService.sNewsService.getNewsData(map);
        call.enqueue(new Callback<NewsInfo>()
        {
            @Override
            public void onResponse(Call<NewsInfo> call, Response<NewsInfo> response)
            {
                mNewsView.hideLoading();
                mNewsView.finishRefresh();
                if (response.isSuccessful() && response.body() != null)
                {
                    Log.e(LOG_TAG, response.body().getResult().getData().toString());
                    List<NewsInfo.ResultBean.DataBean> list = response.body().getResult().getData();
                    mNewsView.showNews(list);
                }
            }

            @Override
            public void onFailure(Call<NewsInfo> call, Throwable t)
            {
                mNewsView.hideLoading();
                mNewsView.finishRefresh();
            }
        });
    }

    @Override
    public void onItemClick(Context context, NewsInfo.ResultBean.DataBean news, ActivityOptionsCompat options)
    {

    }

    @Override
    public void start()
    {

    }
}
