package com.moudle.necromreader.gankio.tech;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.moudle.necromreader.R;
import com.moudle.necromreader.adapter.TechListAdapter;
import com.moudle.necromreader.api.Constant;
import com.moudle.necromreader.api.bean.TechInfo;
import com.moudle.necromreader.base.BaseFragment;
import com.moudle.necromreader.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class TechFragment extends BaseFragment implements TechContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener
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
        techListAdapter = new TechListAdapter(techList);
        techRv.setAdapter(techListAdapter);

        manager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        techRv.setLayoutManager(manager);
        techRv.setItemAnimator(new DefaultItemAnimator());

        //设置刷新时动画的颜色
        techSwipeRefreshLayout = CommonUtils.setProgressBackgroundColor(activity, techSwipeRefreshLayout);

        if (isAdded())
        {
            mTechPresenter.loadTechData(tabType, pageNum,Constant.PULL_TO_REFRESH);
        }
    }

    @Override
    protected void setListener()
    {
        super.setListener();
        techSwipeRefreshLayout.setOnRefreshListener(this);

        techListAdapter.setOnLoadMoreListener(this, techRv);
        //设置第一次加载不回凋setOnLoadMoreListener
        techListAdapter.disableLoadMoreIfNotFullPage();
        //设置item的点击事件
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
    public void showTechData(List<TechInfo.ResultsBean> list,int refreshTag)
    {
        if (refreshTag == Constant.PULL_TO_REFRESH)
        {
            techListAdapter.setNewData(list);
            //当第一页数据小于page size时显示“没有更多数据”
            if (list.size() < 10)
            {
                techListAdapter.loadMoreEnd(false);
            }
        }
        else if (refreshTag == Constant.UP_TO_LOAD_MORE)
        {
            techListAdapter.addData(list);
            techListAdapter.loadMoreComplete();
        }
    }

    @Override
    public boolean isActive()
    {
        return isAdded();
    }

    @Override
    public void setEnableLoadMore(boolean enable)
    {
        techListAdapter.setEnableLoadMore(enable);
    }

    @Override
    public void handleStatus(boolean isSuccess, int status)
    {
        if (isSuccess)
        {
            if (status == Constant.NO_DATA)
            {
                techListAdapter.setEmptyView(R.layout.empty_view);
            }
            else if (status == Constant.NO_MORE_DATA)
            {
                techListAdapter.loadMoreEnd(false);
            }
        }
        else
        {
            if (status == Constant.PULL_TO_REFRESH)
            {
                techListAdapter.setEmptyView(R.layout.error_view);
            }
            else if(status == Constant.UP_TO_LOAD_MORE)
            {
                techListAdapter.loadMoreFail();
            }
        }
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
        mTechPresenter.loadTechData(tabType, pageNum,Constant.PULL_TO_REFRESH);
    }

    @Override
    public void onLoadMoreRequested()
    {
        Log.i(Constant.TAG, "onLoadMoreRequested");
        pageNum = pageNum + 1;
        mTechPresenter.loadTechData(tabType, pageNum,Constant.UP_TO_LOAD_MORE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position)
    {
        TechInfo.ResultsBean tech = (TechInfo.ResultsBean) view.getTag();
        mTechPresenter.onItemClick(getActivity(), tech);
    }

}
