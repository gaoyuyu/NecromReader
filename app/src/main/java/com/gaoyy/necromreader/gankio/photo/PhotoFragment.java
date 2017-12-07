package com.gaoyy.necromreader.gankio.photo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.adapter.PhotoListAdapter;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.api.bean.PhotoInfo;
import com.gaoyy.necromreader.base.BaseFragment;
import com.gaoyy.necromreader.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyy on 2017/3/19 0019.
 */

public class PhotoFragment extends BaseFragment implements PhotoContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener
{
    private SwipeRefreshLayout photoSwipeRefreshLayout;
    private RecyclerView photoRv;
    private ProgressBar photoProgressBar;
    private static final String LOG_TAG = PhotoFragment.class.getSimpleName();
    private PhotoContract.Presenter mPhotoPresenter;
    private PhotoListAdapter photoListAdapter;
    private int pageNum = 1;
    private List<PhotoInfo.ResultsBean> photoList = new ArrayList<>();
    private int[] lastVisibleItem;

    private StaggeredGridLayoutManager manager;
    private String tabType;

    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_photo;
    }

    public PhotoFragment()
    {

    }

    public static PhotoFragment newInstance()
    {
        PhotoFragment fragment = new PhotoFragment();
        return fragment;
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
        photoSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.photo_swipeRefreshLayout);
        photoRv = (RecyclerView) rootView.findViewById(R.id.photo_rv);
        photoProgressBar = (ProgressBar) rootView.findViewById(R.id.photo_progressBar);
    }

    @Override
    protected void configViews()
    {
        super.configViews();
        photoListAdapter = new PhotoListAdapter(photoList);
        photoRv.setAdapter(photoListAdapter);

        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        photoRv.setLayoutManager(manager);
        photoRv.setItemAnimator(new DefaultItemAnimator());

        //设置刷新时动画的颜色
        photoSwipeRefreshLayout = CommonUtils.setProgressBackgroundColor(activity, photoSwipeRefreshLayout);

        if (isAdded())
        {
            mPhotoPresenter.loadPhotoData(tabType, pageNum,Constant.PULL_TO_REFRESH);
        }


    }

    @Override
    protected void setListener()
    {
        super.setListener();
        photoSwipeRefreshLayout.setOnRefreshListener(this);
        photoListAdapter.setOnLoadMoreListener(this, photoRv);
        //设置第一次加载不回凋setOnLoadMoreListener
        photoListAdapter.disableLoadMoreIfNotFullPage();
        //设置item的点击事件
        photoListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showLoading()
    {
        photoProgressBar.setVisibility(View.VISIBLE);
        photoSwipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading()
    {
        photoProgressBar.setVisibility(View.GONE);
        photoSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishRefresh()
    {
        if (photoSwipeRefreshLayout.isRefreshing())
        {
            photoSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void refreshing()
    {
        photoSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showPhotoData(List<PhotoInfo.ResultsBean> list,int refreshTag)
    {
        if (refreshTag == Constant.PULL_TO_REFRESH)
        {
            photoListAdapter.setNewData(list);
            //当第一页数据小于page size时显示“没有更多数据”
            if (list.size() < 10)
            {
                photoListAdapter.loadMoreEnd(false);
            }
        }
        else if (refreshTag == Constant.UP_TO_LOAD_MORE)
        {
            photoListAdapter.addData(list);
            photoListAdapter.loadMoreComplete();
        }
    }

    @Override
    public void setEnableLoadMore(boolean enable)
    {
        photoListAdapter.setEnableLoadMore(enable);
    }

    @Override
    public void handleStatus(boolean isSuccess, int status)
    {
        if (isSuccess)
        {
            if (status == Constant.NO_DATA)
            {
                photoListAdapter.setEmptyView(R.layout.empty_view);
            }
            else if (status == Constant.NO_MORE_DATA)
            {
                photoListAdapter.loadMoreEnd(false);
            }
        }
        else
        {
            if (status == Constant.PULL_TO_REFRESH)
            {
                photoListAdapter.setEmptyView(R.layout.error_view);
            }
            else if(status == Constant.UP_TO_LOAD_MORE)
            {
                photoListAdapter.loadMoreFail();
            }
        }
    }

    @Override
    public boolean isActive()
    {
        return isAdded();
    }

    @Override
    public void setPresenter(PhotoContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mPhotoPresenter = presenter;
        }
    }

    @Override
    public void onRefresh()
    {
        pageNum = 1;
        photoSwipeRefreshLayout.setRefreshing(true);
        Log.i(Constant.TAG, "下拉刷新pageNum-->" + pageNum);
        mPhotoPresenter.loadPhotoData(tabType, pageNum,Constant.PULL_TO_REFRESH);
    }

    @Override
    public void onLoadMoreRequested()
    {
        pageNum = pageNum + 1;
        mPhotoPresenter.loadPhotoData(tabType, pageNum,Constant.UP_TO_LOAD_MORE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position)
    {
        PhotoInfo.ResultsBean resultsBean = (PhotoInfo.ResultsBean) view.getTag();
        mPhotoPresenter.onItemClick(activity, resultsBean);
    }
}
