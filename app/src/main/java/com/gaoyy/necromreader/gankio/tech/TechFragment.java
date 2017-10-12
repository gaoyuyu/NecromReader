package com.gaoyy.necromreader.gankio.tech;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.adapter.TechListAdapter;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.api.bean.TechInfo;
import com.gaoyy.necromreader.base.BaseFragment;
import com.gaoyy.necromreader.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class TechFragment extends BaseFragment implements TechContract.View, SwipeRefreshLayout.OnRefreshListener, TechListAdapter.OnItemClickListener
{
    private static final String LOG_TAG = TechFragment.class.getSimpleName();
    private SwipeRefreshLayout techSwipeRefreshLayout;
    private RecyclerView techRv;
    private ProgressBar techProgressBar;
    private List<TechInfo.ResultsBean> techList = new ArrayList<>();
    private TechListAdapter techListAdapter;
    private LinearLayoutManager manager;
    private TechContract.Presenter mTechPresenter;

    private int pageNum = 1;
    private int lastVisibleItem;

    private String tabType;

    public TechFragment()
    {
        // Required empty public constructor
    }

    public static TechFragment newInstance()
    {
        TechFragment fragment = new TechFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_tech;
    }

    @Override
    protected void getParamsData()
    {
        super.getParamsData();
        tabType = getActivity().getResources().getString(getArguments().getInt("type"));
    }

    @Override
    protected void assignViews(View rootView)
    {
        super.assignViews(rootView);
        techSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.tech_swipeRefreshLayout);
        techRv = (RecyclerView) rootView.findViewById(R.id.tech_rv);
        techProgressBar = (ProgressBar) rootView.findViewById(R.id.tech_progressBar);
    }

    @Override
    protected void configViews()
    {
        super.configViews();
        techListAdapter = new TechListAdapter(getActivity(), techList);
        techRv.setAdapter(techListAdapter);

        manager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        techRv.setLayoutManager(manager);
        techRv.setItemAnimator(new DefaultItemAnimator());

        //设置刷新时动画的颜色
        techSwipeRefreshLayout = CommonUtils.setProgressBackgroundColor(activity, techSwipeRefreshLayout);

        if (isAdded())
        {
            mTechPresenter.loadTechData(tabType, pageNum);
        }
    }

    @Override
    protected void setListener()
    {
        super.setListener();
        techSwipeRefreshLayout.setOnRefreshListener(this);
        techRv.setOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == techListAdapter.getItemCount())
                {
                    pageNum = pageNum + 1;
                    mTechPresenter.loadTechData(tabType, pageNum);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPosition();

            }
        });

        techListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showLoading()
    {
        techProgressBar.setVisibility(View.VISIBLE);
        techSwipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading()
    {
        techProgressBar.setVisibility(View.GONE);
        techSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishRefresh()
    {
        if (techSwipeRefreshLayout.isRefreshing())
        {
            techSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void refreshing()
    {
        techSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showTechData(List<TechInfo.ResultsBean> list)
    {
        techListAdapter.update(list);
    }

    @Override
    public boolean isActive()
    {
        return isAdded();
    }

    @Override
    public void setPresenter(TechContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mTechPresenter = presenter;
        }
    }

    @Override
    public void onRefresh()
    {
        pageNum = 1;
        techSwipeRefreshLayout.setRefreshing(true);
        Log.i(Constant.TAG, "下拉刷新pageNum-->" + pageNum);
        mTechPresenter.loadTechData(tabType, pageNum);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        TechInfo.ResultsBean tech = (TechInfo.ResultsBean) view.getTag();
        mTechPresenter.onItemClick(getActivity(),tech);
    }
}
