package com.moudle.necromreader.gankio.tech;

import android.content.Context;
import android.content.Intent;

import com.moudle.necromreader.api.Constant;
import com.moudle.necromreader.api.RetrofitService;
import com.moudle.necromreader.api.bean.TechInfo;
import com.moudle.necromreader.newsdetail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moudle on 2017/10/11 0011.
 */

public class TechPresenter implements TechContract.Presenter
{
    private TechContract.View mTechView;
    private List<TechInfo.ResultsBean> techList = new ArrayList<>();

    public TechPresenter(TechContract.View mTechView)
    {
        this.mTechView = mTechView;
        mTechView.setPresenter(this);
    }

    @Override
    public void loadTechData(String type, final int pageNum, final int refreshTag)
    {
        Call<TechInfo> call = RetrofitService.sGankService.getTechsData(type, pageNum);
        if (refreshTag == Constant.PULL_TO_REFRESH)
        {
            mTechView.setEnableLoadMore(false);
        }
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
                mTechView.setEnableLoadMore(true);
                if (response.isSuccessful() && response.body() != null)
                {

                    List<TechInfo.ResultsBean> list = response.body().getResults();
                    if(refreshTag == Constant.PULL_TO_REFRESH)
                    {
                        if (list.size() == 0)
                        {
                            mTechView.handleStatus(true, Constant.NO_DATA);
                        }
                        else
                        {
                            mTechView.showTechData(list, refreshTag);
                        }
                    }
                    else if(refreshTag == Constant.UP_TO_LOAD_MORE)
                    {
                        if (list.size() == 0)
                        {
                            mTechView.handleStatus(true, Constant.NO_MORE_DATA);
                        }
                        else
                        {
                            mTechView.showTechData(list, refreshTag);
                        }
                    }
                }
                else
                {
                    mTechView.handleStatus(false, refreshTag);
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

                mTechView.setEnableLoadMore(true);
                mTechView.handleStatus(false,refreshTag);
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
