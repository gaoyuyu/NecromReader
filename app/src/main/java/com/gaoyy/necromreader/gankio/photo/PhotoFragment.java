package com.gaoyy.necromreader.gankio.photo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.adapter.PhotoListAdater;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.api.bean.PhotoInfo;
import com.gaoyy.necromreader.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyy on 2017/3/19 0019.
 */

public class PhotoFragment extends BaseFragment implements PhotoContract.View, SwipeRefreshLayout.OnRefreshListener, PhotoListAdater.OnItemClickListener
{
    private SwipeRefreshLayout photoSwipeRefreshLayout;
    private RecyclerView photoRv;
    private ProgressBar photoProgressBar;
    private static final String LOG_TAG = PhotoFragment.class.getSimpleName();
    private PhotoContract.Presenter mPhotoPresenter;
    private PhotoListAdater photoListAdater;
    private int pageNum = 1;
    private List<PhotoInfo.ResultsBean> photoList = new ArrayList<>();
    private int[] lastVisibleItem;

    private StaggeredGridLayoutManager manager;

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
        photoListAdater = new PhotoListAdater(activity, photoList);
        photoRv.setAdapter(photoListAdater);

        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        photoRv.setLayoutManager(manager);
        photoRv.setItemAnimator(new DefaultItemAnimator());

        //设置刷新时动画的颜色，可以设置4个
        photoSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        photoSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        photoSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mPhotoPresenter.loadPhotoData(pageNum);


    }

    @Override
    protected void setListener()
    {
        super.setListener();
        photoSwipeRefreshLayout.setOnRefreshListener(this);
        photoRv.setOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                for(int i=0;i<lastVisibleItem.length;i++)
                {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem[i] + 1 == photoListAdater.getItemCount())
                    {
                        Log.i(Constant.TAG,"上拉加载更多 before pageNum-->"+pageNum);
                        pageNum= pageNum+1;
                        mPhotoPresenter.loadPhotoData(pageNum);
                        Log.i(Constant.TAG,"上拉加载更多 after pageNum-->"+pageNum);
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPositions(null);

            }
        });


        photoListAdater.setOnItemClickListener(this);

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
        if(photoSwipeRefreshLayout.isRefreshing())
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
    public void showPhotoData(List<PhotoInfo.ResultsBean> list)
    {
        photoListAdater.update(list);
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
        Log.i(Constant.TAG,"下拉刷新pageNum-->"+pageNum);
        mPhotoPresenter.loadPhotoData(pageNum);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        PhotoInfo.ResultsBean resultsBean = (PhotoInfo.ResultsBean) view.getTag();
        mPhotoPresenter.onItemClick(activity,resultsBean);
    }
}
