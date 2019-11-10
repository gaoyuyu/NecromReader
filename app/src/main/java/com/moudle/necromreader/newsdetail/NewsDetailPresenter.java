package com.moudle.necromreader.newsdetail;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by moudle on 2017/3/18 0018.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter
{
    private static final String LOG_TAG = NewsDetailPresenter.class.getSimpleName();
    private NewsDetailContract.View mNewsDetailView;

    public NewsDetailPresenter(NewsDetailContract.View mNewsDetailView)
    {
        this.mNewsDetailView = mNewsDetailView;
        mNewsDetailView.setPresenter(this);
    }

    @Override
    public void start()
    {

    }

    @Override
    public void showNewsInWebView(WebView webView, String url)
    {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Log.e(LOG_TAG, "onPageStarted");
                mNewsDetailView.showLoading(null);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                Log.e(LOG_TAG, "onPageFinished");
                mNewsDetailView.hideLoading();
                super.onPageFinished(view, url);
            }
        });

    }
}
