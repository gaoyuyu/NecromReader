package com.moudle.necromreader.api;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moudle on 2017/3/12 0012.
 * 整个网络通信服务的启动控制，必须先调用初始化函数才能正常使用网络通信接口
 */
public class RetrofitService
{
    public static NewsApi sNewsService;
    public static GankApi sGankService;
    private RetrofitService()
    {
        throw new AssertionError();
    }

    /**
     * 初始化网络通信服务
     */
    public static void init(Context context)
    {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(1500000, TimeUnit.MILLISECONDS)
                .writeTimeout(2000000, TimeUnit.MILLISECONDS)
                .readTimeout(2000000, TimeUnit.MILLISECONDS)
                .build();

        Retrofit newsRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_NEWS_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sNewsService = newsRetrofit.create(NewsApi.class);


        Retrofit gankRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_GANK_PHOTO_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sGankService = gankRetrofit.create(GankApi.class);

    }
}
