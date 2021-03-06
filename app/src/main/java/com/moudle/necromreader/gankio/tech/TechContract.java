package com.moudle.necromreader.gankio.tech;

import android.content.Context;

import com.moudle.necromreader.api.bean.TechInfo;
import com.moudle.necromreader.base.BasePresenter;
import com.moudle.necromreader.base.BaseView;

import java.util.List;

/**
 * Created by moudle on 2017/10/11 0011.
 */

public class TechContract
{

    interface View extends BaseView<Presenter>
    {
        void showLoading();

        void hideLoading();

        void finishRefresh();

        void refreshing();

        void showTechData(List<TechInfo.ResultsBean> list, int refreshTag);

        boolean isActive();

        void setEnableLoadMore(boolean enable);

        void handleStatus(boolean isSuccess,int status);
    }

    interface Presenter extends BasePresenter
    {
        /**
         * @param pageNum 当前页数
         * @param type    标签类型
         */
        void loadTechData(String type, int pageNum,int refreshTag);

        /**
         *  item click
         * @param context
         * @param tech
         */
        void onItemClick(Context context,TechInfo.ResultsBean tech);

    }
}
