package com.gaoyy.necromreader.newsdetail;


import android.webkit.WebView;

import com.gaoyy.necromreader.base.BasePresenter;
import com.gaoyy.necromreader.base.BaseView;

/**
 * Created by gaoyy on 2017/3/18 0018.
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
