package com.gaoyy.necromreader.application;

import android.app.Application;
import android.graphics.Bitmap;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.RetrofitService;
import com.gaoyy.necromreader.greendao.entity.GankTag;
import com.gaoyy.necromreader.greendao.gen.DaoSession;
import com.gaoyy.necromreader.greendao.gen.GankTagDao;
import com.gaoyy.necromreader.util.DBUtils;
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

        initDB();




    }

    /**
     * 初始化
     */
    private void initDB()
    {
        DaoSession session = DBUtils.getDaoSession(this);
        int[] gankType = {R.string.android, R.string.ios, R.string.front_web, R.string.photo};
        GankTagDao gankDao = session.getGankTagDao();
        gankDao.deleteAll();
        for(int i=0;i<gankType.length;i++)
        {
            GankTag gankTag = new GankTag(null,gankType[i],i,getResources().getString(gankType[i]));
            gankDao.insert(gankTag);
        }

        DBUtils.getGankTagList(this);
    }


    private void initPicasso()
    {
        Picasso picasso = new Picasso.Builder(this)
                .memoryCache(new LruCache(10 << 20))
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .downloader(new MyDownloader())
                .indicatorsEnabled(false)
                .build();
        Picasso.setSingletonInstance(picasso);
    }
}
