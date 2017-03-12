package com.gaoyy.necromreader.main;

/**
 * Created by gaoyy on 2016/12/26.
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
