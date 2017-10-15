package com.gaoyy.necromreader.gankio;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import com.gaoyy.necromreader.adapter.NewsPagerAdapter;
import com.gaoyy.necromreader.adapter.GankTagListAdapter;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.base.BaseFragment;
import com.gaoyy.necromreader.gankio.photo.PhotoFragment;
import com.gaoyy.necromreader.gankio.photo.PhotoPresenter;
import com.gaoyy.necromreader.gankio.tech.TechFragment;
import com.gaoyy.necromreader.gankio.tech.TechPresenter;
import com.gaoyy.necromreader.greendao.entity.GankTag;
import com.gaoyy.necromreader.util.CommonUtils;
import com.gaoyy.necromreader.util.DBUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by gaoyy on 2017/3/19 0019.
 */

public class GankFragment extends BaseFragment implements GankContract.View, View.OnClickListener
{

    private static final String LOG_TAG = GankFragment.class.getSimpleName();
    private String title;
    private int[] tabType;
    private Toolbar gankToolbar;
    private TabLayout gankTablayout;
    private ViewPager gankViewpager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private NewsPagerAdapter newsPagerAdapter;
    private GankContract.Presenter mGankPresenter;

    private ImageView gankSortImg;


    private TextView popupTagTv;
    private AppCompatButton popupSortBtn;
    private AppCompatButton popupFinishBtn;
    private TextView popupTagSubTv;
    private RecyclerView popupRv;

    private PopupWindow popupWindow;

    private GankTagListAdapter tagListAdapter;

    public static final int TAG_COLUMN = 2;


    public GankFragment()
    {

    }

    public static GankFragment newInstance()
    {
        GankFragment fragment = new GankFragment();
        return fragment;
    }

    @Override
    protected void getParamsData()
    {
        super.getParamsData();
        title = getArguments().getString("title");
        List<GankTag> gankTagList = DBUtils.getGankTagList(getActivity());

        initTabType(gankTagList);
    }

    /**
     * 初始化标签数据
     */
    private void initTabType(List<GankTag> gankTagList)
    {
        tabType = new int[gankTagList.size()];
        for (int i = 0; i < tabType.length; i++)
        {
            tabType[i] = gankTagList.get(i).getTagId();
        }
    }

    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_gank;
    }

    @Override
    protected void assignViews(View rootView)
    {
        super.assignViews(rootView);
        gankToolbar = (Toolbar) rootView.findViewById(R.id.gank_toolbar);
        gankTablayout = (TabLayout) rootView.findViewById(R.id.gank_tablayout);
        gankViewpager = (ViewPager) rootView.findViewById(R.id.gank_viewpager);
        gankViewpager = (ViewPager) rootView.findViewById(R.id.gank_viewpager);
        gankSortImg = (ImageView) rootView.findViewById(R.id.gank_sort_img);
    }


    @Override
    protected void initToolbar()
    {
        super.initToolbar(gankToolbar, title, false, -1);
    }

    @Override
    protected void configViews()
    {
        super.configViews();
        gankToolbar.setNavigationIcon(R.drawable.ic_menu_bar);
        Log.e(Constant.TAG, "-length->" + tabType.length);
        setUpViewPager(tabType);
    }

    /**
     * 设置viewpager
     *
     * @param tabType
     */
    private void setUpViewPager(int[] tabType)
    {
        setUpFragmentList(tabType);
        newsPagerAdapter = new NewsPagerAdapter(activity, getChildFragmentManager(), tabType, fragmentList);
        gankViewpager.setAdapter(newsPagerAdapter);
        gankViewpager.setOffscreenPageLimit(1);
        gankTablayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        gankTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        gankTablayout.setupWithViewPager(gankViewpager);
        gankTablayout.setTabsFromPagerAdapter(newsPagerAdapter);
    }

    private void setUpFragmentList(int[] tabType)
    {
        Log.d(Constant.TAG, "tabType-->" + Arrays.toString(tabType));
        fragmentList.clear();
        for (int i = 0; i < tabType.length; i++)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("type", tabType[i]);
            if (tabType[i] == R.string.photo)
            {
                PhotoFragment fragment = PhotoFragment.newInstance();
                fragment.setArguments(bundle);
                fragmentList.add(i, fragment);
                new PhotoPresenter(fragment);
            }
            else
            {
                TechFragment fragment = TechFragment.newInstance();
                fragment.setArguments(bundle);
                fragmentList.add(i, fragment);
                new TechPresenter(fragment);
            }
        }
    }

    @Override
    protected void setListener()
    {
        super.setListener();

        gankToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.main_drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START))
                {
                    drawer.closeDrawer(GravityCompat.START);
                }
                else
                {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

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


        List<GankTag> gankTagList = DBUtils.getGankTagList(getActivity());
        tagListAdapter = new GankTagListAdapter(getActivity(), gankTagList);
        popupRv.setAdapter(tagListAdapter);


        GridLayoutManager mgr = new GridLayoutManager(getActivity(), TAG_COLUMN);
        popupRv.setLayoutManager(mgr);


        ItemTouchHelper.Callback callback = new TagItemTouchCallBack(tagListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(popupRv);


        tagListAdapter.setOnItemLongClickListener(new GankTagListAdapter.OnItemClickListener()
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
                        tagListAdapter.toggleModel(true);
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
                        DBUtils.deleteGankTagBySort(getActivity(),tag);
                        List<GankTag> gankTagList = DBUtils.getGankTagList(getActivity());
                        //根据sort排序，更新标签列表
//                        Collections.sort(gankTagList);
                        tagListAdapter.update(gankTagList);
                        tagListAdapter.toggleModel(true);
                        break;
                }
            }
        });


        gankSortImg.setOnClickListener(new View.OnClickListener()
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
                    popupWindow.showAsDropDown(gankTablayout);
                }
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
        mGankPresenter.start();
    }


    @Override
    public boolean isActive()
    {
        return isAdded();
    }


    @Override
    public void setPresenter(GankContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mGankPresenter = presenter;
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

                tagListAdapter.toggleModel(true);

                break;
            case R.id.popup_finish_btn:
                popupTagSubTv.setVisibility(View.INVISIBLE);
                popupSortBtn.setVisibility(View.VISIBLE);
                popupFinishBtn.setVisibility(View.INVISIBLE);

                List<GankTag> gankTagList = DBUtils.getGankTagList(getActivity());
                //根据sort排序，更新标签列表
                Collections.sort(gankTagList);
                tagListAdapter.update(gankTagList);
                tagListAdapter.toggleModel(false);
                //更新viewpager和tablayout
                initTabType(gankTagList);
                setUpFragmentList(tabType);
                newsPagerAdapter.update(fragmentList);
                Log.d(Constant.TAG, "-->" + fragmentList.toString());
                gankTablayout.removeAllTabs();
                for (int i = 0; i < tabType.length; i++)
                {
                    gankTablayout.addTab(gankTablayout.newTab().setText(CommonUtils.getTypeName(tabType[i])));
                }
                //popupWindow消失
                popupWindow.dismiss();

                break;
        }
    }


}
