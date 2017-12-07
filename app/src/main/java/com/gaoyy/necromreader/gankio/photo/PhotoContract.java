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

        void showPhotoData(List<PhotoInfo.ResultsBean> list, int refreshTag);

        boolean isActive();

        void setEnableLoadMore(boolean enable);

        void handleStatus(boolean isSuccess, int status);
    }

    interface Presenter extends BasePresenter
    {
        /**
         * 加载图片
         *
         * @param pageNum 当前页数
         * @param type    标签类型
         */
        void loadPhotoData(String type, int pageNum, int refreshTag);


        void onItemClick(Context context, PhotoInfo.ResultsBean resultsBean);
    }
}
