package com.moudle.necromreader.news;

import android.content.Context;

import com.moudle.necromreader.api.bean.NewsInfo;
import com.moudle.necromreader.base.BasePresenter;
import com.moudle.necromreader.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by moudle on 2017/3/12 0012.
 */

public class NewsContract
{
    interface View extends BaseView<Presenter>
    {
        void showLoading();

        void hideLoading();

        void finishRefresh();

        void showNews(List<NewsInfo.ResultBean.DataBean> list);

        boolean isActive();

        void showToast();
    }

    interface Presenter extends BasePresenter
    {
        void loadNewsData(Map<String, String> map);

        void onItemClick(Context context, NewsInfo.ResultBean.DataBean news);

    }
}
