package com.gaoyy.necromreader.api;

import com.gaoyy.necromreader.api.bean.PhotoInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gaoyy on 2017/3/19 0019.
 */

public interface GankApi
{
    /**
     * eg.http://gank.io/api/data/福利/10/2
     * 10-一页的总数，2-页数
     *
     * @param pageNum
     * @return
     */
    @GET("10/{pageNum}")
    Call<PhotoInfo> getPhotosData(@Path("pageNum") int pageNum);
}
