package com.gaoyy.necromreader.gankio.tech;

import com.gaoyy.necromreader.api.bean.TechInfo;
import com.gaoyy.necromreader.base.BasePresenter;
import com.gaoyy.necromreader.base.BaseView;

import java.util.List;

/**
 * Created by gaoyy on 2017/10/11 0011.
 */

public class TechContract
{

    interface View extends BaseView<Presenter>
    {
        void showLoading();

        void hideLoading();

        void finishRefresh();

        void refreshing();

        void showTechData(List<TechInfo.ResultsBean> list);

        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {
        /**
         * @param pageNum 当前页数
         * @param type    标签类型
         */
        void loadTechData(String type, int pageNum);

    }
}
