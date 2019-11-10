package com.moudle.necromreader.util;

import android.content.Context;
import android.util.Log;

import com.moudle.necromreader.R;
import com.moudle.necromreader.api.Constant;
import com.moudle.necromreader.greendao.entity.GankTag;
import com.moudle.necromreader.greendao.entity.NewTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moudle on 2017/10/14 0014.
 */

public class DBUtils
{

    /**
     * 查询gank_tag的所有记录
     *
     * @param context
     * @return
     */
    public static List<GankTag> getGankTagList(Context context)
    {
        int[] gankType = {R.string.android, R.string.ios, R.string.front_web, R.string.photo};
        List<GankTag> data = new ArrayList<>();
        for (int i = 0; i < gankType.length; i++)
        {
            GankTag gankTag = new GankTag(null, gankType[i], i, context.getResources().getString(gankType[i]));
            data.add(gankTag);
        }
        Log.d(Constant.TAG, "getGankTagList-->" + data.toString());
        return data;
    }


    /**
     * 查询new_tag的所有记录
     *
     * @param context
     * @return
     */
    public static List<NewTag> getNewTagList(Context context)
    {
        List<NewTag> data = new ArrayList<>();
        int[] newsType = {R.string.top, R.string.shehui, R.string.guonei, R.string.guoji,
                R.string.yule, R.string.tiyu, R.string.junshi, R.string.keji, R.string.caijing, R.string.shishang};
        for (int i = 0; i < newsType.length; i++)
        {
            NewTag newTag = new NewTag(null, newsType[i], i, context.getResources().getString(newsType[i]));
            data.add(newTag);
        }
        Log.d(Constant.TAG, "getNewTagList-->" + data.toString());
        return data;
    }


}
