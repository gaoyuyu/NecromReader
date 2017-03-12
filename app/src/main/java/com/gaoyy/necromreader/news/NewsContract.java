package com.gaoyy.necromreader.news;

import android.content.Context;
import android.support.v4.app.ActivityOptionsCompat;

import com.gaoyy.necromreader.api.bean.NewsInfo;
import com.gaoyy.necromreader.base.BasePresenter;
import com.gaoyy.necromreader.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by gaoyy on 2017/3/12 0012.
 */

public class NewsContract
{
    interface View extends BaseView<Presenter>
    {
        void showLoading(String msg);

        void hideLoading();

        void finishRefresh();

        void showNews(List<NewsInfo.ResultBean.DataBean> list);

        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {
        void loadNewsData(Map<String, String> map);

        void onItemClick(Context context, NewsInfo.ResultBean.DataBean news, ActivityOptionsCompat options);

    }
}
