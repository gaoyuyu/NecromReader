package com.gaoyy.necromreader.main;


import com.gaoyy.necromreader.base.BasePresenter;
import com.gaoyy.necromreader.base.BaseView;

/**
 * Created by gaoyy on 2016/12/26.
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
