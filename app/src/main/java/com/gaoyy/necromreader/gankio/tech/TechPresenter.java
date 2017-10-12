package com.gaoyy.necromreader.gankio.tech;

import android.content.Context;
import android.content.Intent;

import com.gaoyy.necromreader.api.RetrofitService;
import com.gaoyy.necromreader.api.bean.TechInfo;
import com.gaoyy.necromreader.newsdetail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gaoyy on 2017/10/11 0011.
 */

public class TechPresenter implements  TechContract.Presenter
{
    private TechContract.View mTechView;
    private List<TechInfo.ResultsBean> techList = new ArrayList<>();
    public TechPresenter(TechContract.View mTechView)
    {
        this.mTechView = mTechView;
        mTechView.setPresenter(this);
    }
    
    @Override
    public void loadTechData(String type, final int pageNum)
    {
        Call<TechInfo> call = RetrofitService.sGankService.getTechsData(type,pageNum);
        call.enqueue(new Callback<TechInfo>()
        {
            @Override
            public void onResponse(Call<TechInfo> call, Response<TechInfo> response)
            {
                if (!mTechView.isActive())
                {
                    return;
                }
                mTechView.hideLoading();
                mTechView.finishRefresh();
                if (response.isSuccessful() && response.body() != null)
                {
                    List<TechInfo.ResultsBean> list = response.body().getResults();
                    //pageNum=1 下拉刷新，清空list，pageNum不等于1，上拉加载更多，不清空
                    if(pageNum == 1) techList.clear();
                    techList.addAll(list);
                    mTechView.showTechData(techList);
                }
            }

            @Override
            public void onFailure(Call<TechInfo> call, Throwable t)
            {
                if (!mTechView.isActive())
                {
                    return;
                }
                mTechView.hideLoading();
                mTechView.finishRefresh();
            }
        });

    }

    @Override
    public void onItemClick(Context context, TechInfo.ResultsBean tech)
    {
        Intent intent = new Intent();
        intent.putExtra("title", tech.getDesc());
        intent.putExtra("url", tech.getUrl());
        intent.setClass(context, NewsDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void start()
    {

    }
}
