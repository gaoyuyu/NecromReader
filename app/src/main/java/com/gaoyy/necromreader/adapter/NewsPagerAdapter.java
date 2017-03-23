package com.gaoyy.necromreader.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gaoyy.necromreader.R;

import java.util.List;

public class NewsPagerAdapter extends FragmentStatePagerAdapter
{
    private int[] newsType;
    private List<Fragment> fragmentList;
    private Context context;
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    public NewsPagerAdapter(Context context, FragmentManager fm, int[] newsType, List<Fragment> fragmentList)
    {
        super(fm);
        this.context = context;
        this.newsType = newsType;
        this.fragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return getTypeName(newsType[position]);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return newsType.length;
    }

    public String getTypeName(int type)
    {
        String typeName = "";
        switch (type)
        {
            case R.string.top:
                typeName = "头条";
                break;
            case R.string.shehui:
                typeName = "社会";
                break;
            case R.string.guonei:
                typeName = "国内";
                break;
            case R.string.guoji:
                typeName = "国际";
                break;
            case R.string.yule:
                typeName = "娱乐";
                break;
            case R.string.tiyu:
                typeName = "体育";
                break;
            case R.string.junshi:
                typeName = "军事";
                break;
            case R.string.keji:
                typeName = "科技";
                break;
            case R.string.caijing:
                typeName = "财经";
                break;
            case R.string.shishang:
                typeName = "时尚";
                break;
            case R.string.photo:
                typeName="福利";
                break;
            case R.string.android:
                typeName="Android";
                break;
        }
        return typeName;
    }
}
