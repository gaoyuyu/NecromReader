package com.gaoyy.necromreader.gankio.photo;

import android.content.Context;

import com.gaoyy.necromreader.api.bean.PhotoInfo;
import com.gaoyy.necromreader.base.BasePresenter;
import com.gaoyy.necromreader.base.BaseView;

import java.util.List;

/**
 * Created by gaoyy on 2017/3/23 0023.
 */

public class PhotoContract
{
    interface View extends BaseView<Presenter>
    {
        void showLoading();

        void hideLoading();

        void finishRefresh();

        void refreshing();

        void showPhotoData(List<PhotoInfo.ResultsBean> list);

        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {
        /**
         * 加载图片
         *
         * @param pageNum 当前页数
         */
        void loadPhotoData(int pageNum);


        void onItemClick(Context context, PhotoInfo.ResultsBean resultsBean);
    }
}
