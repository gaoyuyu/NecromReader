package com.gaoyy.necromreader.gankio.photo;

import android.content.Context;
import android.content.Intent;

import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.api.RetrofitService;
import com.gaoyy.necromreader.api.bean.PhotoInfo;
import com.gaoyy.necromreader.bigphoto.BigPhotoActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gaoyy on 2017/3/23 0023.
 */

public class PhotoPresenter implements PhotoContract.Presenter
{
    private static final String LOG_TAG = PhotoPresenter.class.getSimpleName();
    private PhotoContract.View mPhotoView;
    private List<PhotoInfo.ResultsBean> photoList = new ArrayList<>();
    public PhotoPresenter(PhotoContract.View mPhotoView)
    {
        this.mPhotoView = mPhotoView;
        mPhotoView.setPresenter(this);
    }

    @Override
    public void loadPhotoData(String type,final int pageNum,final int refreshTag)
    {
        Call<PhotoInfo> call = RetrofitService.sGankService.getPhotosData(type,pageNum);
        if (refreshTag == Constant.PULL_TO_REFRESH)
        {
            mPhotoView.setEnableLoadMore(false);
        }
        call.enqueue(new Callback<PhotoInfo>()
        {
            @Override
            public void onResponse(Call<PhotoInfo> call, Response<PhotoInfo> response)
            {
                if (!mPhotoView.isActive())
                {
                    return;
                }
                mPhotoView.hideLoading();
                mPhotoView.finishRefresh();
                mPhotoView.setEnableLoadMore(true);
                if (response.isSuccessful() && response.body() != null)
                {
                    List<PhotoInfo.ResultsBean> list = response.body().getResults();
                    if(refreshTag == Constant.PULL_TO_REFRESH)
                    {
                        if (list.size() == 0)
                        {
                            mPhotoView.handleStatus(true, Constant.NO_DATA);
                        }
                        else
                        {
                            mPhotoView.showPhotoData(list, refreshTag);
                        }
                    }
                    else if(refreshTag == Constant.UP_TO_LOAD_MORE)
                    {
                        if (list.size() == 0)
                        {
                            mPhotoView.handleStatus(true, Constant.NO_MORE_DATA);
                        }
                        else
                        {
                            mPhotoView.showPhotoData(list, refreshTag);
                        }
                    }
                }
                else
                {
                    mPhotoView.handleStatus(false, refreshTag);
                }
            }

            @Override
            public void onFailure(Call<PhotoInfo> call, Throwable t)
            {
                if (!mPhotoView.isActive())
                {
                    return;
                }
                mPhotoView.hideLoading();
                mPhotoView.finishRefresh();

                mPhotoView.setEnableLoadMore(true);
                mPhotoView.handleStatus(false,refreshTag);
            }
        });
    }

    @Override
    public void onItemClick(Context context, PhotoInfo.ResultsBean resultsBean)
    {
        String url = resultsBean.getUrl();
        int index = url.lastIndexOf("/");
        String ImgName = url.substring(index+1,url.length());
        Intent intent = new Intent(context, BigPhotoActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("name",ImgName);
        context.startActivity(intent);
    }

    @Override
    public void start()
    {

    }
}
