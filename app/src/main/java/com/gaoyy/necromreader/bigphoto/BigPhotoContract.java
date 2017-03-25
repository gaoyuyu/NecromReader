package com.gaoyy.necromreader.bigphoto;

import com.gaoyy.necromreader.base.BasePresenter;
import com.gaoyy.necromreader.base.BaseView;

/**
 * Created by gaoyy on 2017/3/25 0025.
 */

public class BigPhotoContract
{
    interface View extends BaseView<Presenter>
    {
        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {
    }
}
