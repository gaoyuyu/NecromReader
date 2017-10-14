package com.gaoyy.necromreader.util;

import android.content.Context;
import android.util.Log;

import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.greendao.entity.GankTag;
import com.gaoyy.necromreader.greendao.gen.DaoMaster;
import com.gaoyy.necromreader.greendao.gen.DaoSession;
import com.gaoyy.necromreader.greendao.gen.GankTagDao;

import java.util.List;

/**
 * Created by gaoyy on 2017/10/14 0014.
 */

public class DBUtils
{
    public static DaoSession getDaoSession(Context context)
    {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "tags-db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession;
    }

    /**
     * 查询gank_tag的所有记录
     *
     * @param context
     * @return
     */
    public static List<GankTag> getGankTagList(Context context)
    {
        DaoSession session = DBUtils.getDaoSession(context);
        GankTagDao gankDao = session.getGankTagDao();
        List<GankTag> data = gankDao.queryBuilder()
                .build().list();
        Log.d(Constant.TAG, "data-->" + data.toString());
        return data;
    }


    /**
     * 根据id，获取tag，更新sort
     *
     * @param context
     * @param id
     * @param sort
     */
    public static void updateTagById(Context context, Long id, int sort)
    {
        DaoSession session = DBUtils.getDaoSession(context);
        GankTagDao gankTagDao = session.getGankTagDao();
        GankTag gankTag = gankTagDao.queryBuilder().where(GankTagDao.Properties.Id.eq(id)).build().unique();
        if (gankTag != null)
        {
            gankTag.setSort(sort);
            gankTagDao.update(gankTag);
        }
    }

    /**
     * 根据id，获取tag的sort
     *
     * @param context
     * @param id
     * @return
     */
    public static int getTagSortById(Context context, int id)
    {
        DaoSession session = DBUtils.getDaoSession(context);
        GankTagDao gankTagDao = session.getGankTagDao();
        GankTag gankTag = gankTagDao.queryBuilder().where(GankTagDao.Properties.Id.eq(id)).build().unique();
        return gankTag.getSort();
    }

    /**
     * 根据sort，删除tag
     *
     * @param context
     * @param sort
     */
    public static void deleteTagBySort(Context context, int sort)
    {
        DaoSession session = DBUtils.getDaoSession(context);
        GankTagDao gankTagDao = session.getGankTagDao();
        GankTag gankTag = gankTagDao.queryBuilder().where(GankTagDao.Properties.Sort.eq(sort)).build().unique();
        if (gankTag != null)
        {
            gankTagDao.deleteByKey(gankTag.getId());
        }
    }

}
