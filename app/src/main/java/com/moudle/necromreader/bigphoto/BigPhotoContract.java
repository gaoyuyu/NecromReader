package com.moudle.necromreader.bigphoto;

import com.moudle.necromreader.base.BasePresenter;
import com.moudle.necromreader.base.BaseView;

/**
 * Created by moudle on 2017/3/25 0025.
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
