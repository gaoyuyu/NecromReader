package com.gaoyy.necromreader.main;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.adapter.NewTagListAdapter;
import com.gaoyy.necromreader.adapter.NewsPagerAdapter;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.base.BaseFragment;
import com.gaoyy.necromreader.gankio.TagItemTouchCallBack;
import com.gaoyy.necromreader.greendao.entity.NewTag;
import com.gaoyy.necromreader.news.NewsFragment;
import com.gaoyy.necromreader.util.CommonUtils;
import com.gaoyy.necromreader.util.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements MainContract.View, View.OnClickListener
{
    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    //toolbar的标题
    private String title;
    //tab列表数据
    private int[] tabType;
    private Toolbar homeToolbar;
    private TabLayout homeTablayout;
    private ViewPager homeViewpager;
    private NewsPagerAdapter newsPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private MainContract.Presenter mMainPresenter;


    private TextView popupTagTv;
    private AppCompatButton popupSortBtn;
    private AppCompatButton popupFinishBtn;
    private TextView popupTagSubTv;
    private RecyclerView popupRv;

    private PopupWindow popupWindow;

    private NewTagListAdapter newTagListAdapter;

    private ImageView homeSortImg;
    public static final int TAG_COLUMN = 4;

    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_home;
    }

    public HomeFragment()
    {
        // Required empty public constructor
    }

    public static HomeFragment newInstance()
    {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected void getParamsData()
    {
        super.getParamsData();
        title = getArguments().getString("title");
        Log.d(Constant.TAG,"title-->"+title);
        List<NewTag> newTagList = DBUtils.getNewTagList(getActivity());

        initTabType(newTagList);
    }

    /**
     * 初始化标签数据
     */
    private void initTabType(List<NewTag> newTagList)
    {
        tabType = new int[newTagList.size()];
        for (int i = 0; i < tabType.length; i++)
        {
            tabType[i] = newTagList.get(i).getTagId();
        }
    }

    @Override
    protected void assignViews(View rootView)
    {
        super.assignViews(rootView);
        homeToolbar = (Toolbar) rootView.findViewById(R.id.home_toolbar);
        homeTablayout = (TabLayout) rootView.findViewById(R.id.home_tablayout);
        homeViewpager = (ViewPager) rootView.findViewById(R.id.home_viewpager);
        homeSortImg = (ImageView) rootView.findViewById(R.id.home_sort_img);

    }

    @Override
    protected void initToolbar()
    {
        super.initToolbar(homeToolbar, title, false, -1);
    }

    @Override
    protected void configViews()
    {
        super.configViews();
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, homeToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setUpViewPager(tabType);
    }

    private void setUpViewPager(int[] tabType)
    {
        setUpFragmentList(tabType);
        newsPagerAdapter = new NewsPagerAdapter(activity, getChildFragmentManager(), tabType, fragmentList);
        homeViewpager.setAdapter(newsPagerAdapter);
        homeViewpager.setOffscreenPageLimit(1);
        homeTablayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        homeTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeTablayout.setupWithViewPager(homeViewpager);
        homeTablayout.setTabsFromPagerAdapter(newsPagerAdapter);
    }

    private void setUpFragmentList(int[] tabType)
    {
        for (int i = 0; i < tabType.length; i++)
        {
            Bundle bundle = new Bundle();
            Fragment fragment = new NewsFragment();
            bundle.putInt("type", tabType[i]);
            fragment.setArguments(bundle);
            fragmentList.add(i, fragment);
        }
    }


    @Override
    protected void setListener()
    {
        super.setListener();
        setUpSortPopupWindow();
    }
    private void setUpSortPopupWindow()
    {
        final View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_layout, null);
        popupTagTv = (TextView) contentView.findViewById(R.id.popup_tag_tv);
        popupSortBtn = (AppCompatButton) contentView.findViewById(R.id.popup_sort_btn);
        popupFinishBtn = (AppCompatButton) contentView.findViewById(R.id.popup_finish_btn);
        popupTagSubTv = (TextView) contentView.findViewById(R.id.popup_tag_sub_tv);
        popupRv = (RecyclerView) contentView.findViewById(R.id.popup_rv);

        popupWindow = new PopupWindow(getActivity());
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        popupSortBtn.setOnClickListener(this);
        popupFinishBtn.setOnClickListener(this);


        List<NewTag> newTagList = DBUtils.getNewTagList(getActivity());
        newTagListAdapter = new NewTagListAdapter(getActivity(), newTagList);
        popupRv.setAdapter(newTagListAdapter);


        GridLayoutManager mgr = new GridLayoutManager(getActivity(), TAG_COLUMN);
        popupRv.setLayoutManager(mgr);


        ItemTouchHelper.Callback callback = new TagItemTouchCallBack(newTagListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(popupRv);


        newTagListAdapter.setOnItemLongClickListener(new NewTagListAdapter.OnItemClickListener()
        {
            @Override
            public void onItemLongClick(View view, int position)
            {
                switch (view.getId())
                {
                    case R.id.item_popup_layout:
                        popupTagSubTv.setVisibility(View.VISIBLE);
                        popupSortBtn.setVisibility(View.INVISIBLE);
                        popupFinishBtn.setVisibility(View.VISIBLE);
                        newTagListAdapter.toggleModel(true);
                        break;
                }
            }

            @Override
            public void onItemClick(View view, int position)
            {
                switch (view.getId())
                {
                    case R.id.item_popup_delete:
                        int tag = (int) view.getTag();
                        DBUtils.deleteNewTagBySort(getActivity(),tag);
                        List<NewTag> newTagList = DBUtils.getNewTagList(getActivity());
                        //根据sort排序，更新标签列表
//                        Collections.sort(newTagList);
                        newTagListAdapter.update(newTagList);
                        newTagListAdapter.toggleModel(true);
                        break;
                }
            }
        });


        homeSortImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (popupWindow.isShowing())
                {
                    popupWindow.dismiss();
                }
                else
                {
                    popupWindow.showAsDropDown(homeTablayout);
                }
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
        mMainPresenter.start();
        newsPagerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean isActive()
    {
        return isAdded();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mMainPresenter = presenter;
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.popup_sort_btn:
                popupTagSubTv.setVisibility(View.VISIBLE);
                popupSortBtn.setVisibility(View.INVISIBLE);
                popupFinishBtn.setVisibility(View.VISIBLE);

                newTagListAdapter.toggleModel(true);

                break;
            case R.id.popup_finish_btn:
                popupTagSubTv.setVisibility(View.INVISIBLE);
                popupSortBtn.setVisibility(View.VISIBLE);
                popupFinishBtn.setVisibility(View.INVISIBLE);

                List<NewTag> newTagList = DBUtils.getNewTagList(getActivity());
                //根据sort排序，更新标签列表
//                Collections.sort(newTagList);
                newTagListAdapter.update(newTagList);
                newTagListAdapter.toggleModel(false);
                //更新viewpager和tablayout
                initTabType(newTagList);
                setUpFragmentList(tabType);
                newsPagerAdapter.update(fragmentList);
                Log.d(Constant.TAG, "-->" + fragmentList.toString());
                homeTablayout.removeAllTabs();
                for (int i = 0; i < tabType.length; i++)
                {
                    homeTablayout.addTab(homeTablayout.newTab().setText(CommonUtils.getTypeName(tabType[i])));
                }
                //popupWindow消失
                popupWindow.dismiss();

                break;
        }
    }
}
