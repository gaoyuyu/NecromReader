package com.moudle.necromreader.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.moudle.necromreader.util.CommonUtils;

import java.util.List;

public class NewsPagerAdapter extends FragmentStatePagerAdapter
{
    private int[] newsType;
    private List<Fragment> fragmentList;
    private Context context;

    public int getItemPosition(Object object)
    {
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
        return CommonUtils.getTypeName(newsType[position]);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    public void update(List<Fragment> fragmentList)
    {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();

    }

}
