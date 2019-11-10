package com.moudle.necromreader.newsdetail;


import android.webkit.WebView;

import com.moudle.necromreader.base.BasePresenter;
import com.moudle.necromreader.base.BaseView;

/**
 * Created by moudle on 2017/3/18 0018.
 */

public class NewsDetailContract
{
    interface View extends BaseView<Presenter>
    {
        void showLoading(String msg);

        void hideLoading();

        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {
        void showNewsInWebView(WebView webView, String url);
    }
}
