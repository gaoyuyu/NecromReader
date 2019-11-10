package com.moudle.necromreader.main;

/**
 * Created by moudle on 2016/12/26.
 */

public class MainPresenter implements MainContract.Presenter
{

    private MainContract.View mMainView;

    public MainPresenter(MainContract.View mMainView)
    {
        this.mMainView = mMainView;
        mMainView.setPresenter(this);
    }


    @Override
    public void start()
    {

    }

}
