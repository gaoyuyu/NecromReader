package com.moudle.necromreader.gankio;

import com.moudle.necromreader.base.BasePresenter;
import com.moudle.necromreader.base.BaseView;

/**
 * Created by moudle on 2017/3/19 0019.
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
