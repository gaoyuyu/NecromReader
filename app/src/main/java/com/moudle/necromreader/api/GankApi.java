package com.moudle.necromreader.api;

import com.moudle.necromreader.api.bean.PhotoInfo;
import com.moudle.necromreader.api.bean.TechInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by moudle on 2017/3/19 0019.
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
    @GET("{type}/10/{pageNum}")
    Call<PhotoInfo> getPhotosData(@Path("type") String type, @Path("pageNum") int pageNum);

    /**
     * eg.http://gank.io/api/data/福利/10/2
     * 10-一页的总数，2-页数
     *
     * @param pageNum
     * @return
     */
    @GET("{type}/10/{pageNum}")
    Call<TechInfo> getTechsData(@Path("type") String type, @Path("pageNum") int pageNum);


}
