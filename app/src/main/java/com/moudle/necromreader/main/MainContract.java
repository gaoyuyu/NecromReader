package com.moudle.necromreader.main;


import com.moudle.necromreader.base.BasePresenter;
import com.moudle.necromreader.base.BaseView;

/**
 * Created by moudle on 2016/12/26.
 */

public class MainContract
{
    interface View extends BaseView<Presenter>
    {
        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {

    }

}
