package com.gaoyy.necromreader.gankio;

/**
 * Created by gaoyy on 2017/3/19 0019.
 */

public class GankPresenter implements GankContract.Presenter
{

    private GankContract.View mGankView;

    public GankPresenter(GankContract.View mMainView)
    {
        this.mGankView = mMainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void start()
    {

    }
}
