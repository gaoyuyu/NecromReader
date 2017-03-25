package com.gaoyy.necromreader.application;

import android.app.Application;
import android.graphics.Bitmap;

import com.gaoyy.necromreader.api.RetrofitService;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

/**
 * Created by gaoyy on 2016/12/28.
 */

public class MyApplication extends Application
{
    private static final String LOG_TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate()
    {
        super.onCreate();

        //初始化单例的Picasso对象
        initPicasso();

        RetrofitService.init(this);
    }



    private void initPicasso()
    {
        Picasso picasso = new Picasso.Builder(this)
                .memoryCache(new LruCache(10 << 20))
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .downloader(new MyDownloader())
                .indicatorsEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);
    }
}
