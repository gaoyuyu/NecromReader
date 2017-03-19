package com.gaoyy.necromreader.gankio;

import com.gaoyy.necromreader.base.BasePresenter;
import com.gaoyy.necromreader.base.BaseView;

/**
 * Created by gaoyy on 2017/3/19 0019.
 */

public class GankContract
{
    interface View extends BaseView<Presenter>
    {
        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {

    }
}
