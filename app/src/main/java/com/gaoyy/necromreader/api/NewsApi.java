package com.gaoyy.necromreader.api;

import com.gaoyy.necromreader.api.bean.NewsInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by gaoyy on 2016/12/27.
 */

public interface NewsApi
{
    @GET("index")
    Call<NewsInfo> getNewsData(@QueryMap Map<String, String> map);
}
