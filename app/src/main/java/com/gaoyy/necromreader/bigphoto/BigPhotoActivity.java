package com.gaoyy.necromreader.bigphoto;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.base.BaseActivity;
import com.squareup.picasso.Picasso;

public class BigPhotoActivity extends BaseActivity implements BigPhotoContract.View
{
    private BigPhotoContract.Presenter mBigPresenter;
    private static final String LOG_TAG = BigPhotoActivity.class.getSimpleName();
    private ImageView bigImg;
    private Toolbar bigToolbar;

    @Override
    protected void initContentView()
    {
        setContentView(R.layout.activity_big_photo);
    }

    @Override
    protected void assignViews()
    {
        super.assignViews();
        bigImg = (ImageView) findViewById(R.id.big_img);
        bigToolbar = (Toolbar) findViewById(R.id.big_toolbar);
    }

    @Override
    protected void initToolbar()
    {
        super.initToolbar(bigToolbar, "", true, android.R.color.transparent);
    }

    @Override
    protected void configViews(Bundle savedInstanceState)
    {
        super.configViews(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        Picasso.with(this)
                .load(url)
                .placeholder(R.mipmap.loading_bg)
                .error(R.mipmap.error_bg)
                .fit()
                .into(bigImg);

    }

    @Override
    public boolean isActive()
    {
        return true;
    }

    @Override
    public void setPresenter(BigPhotoContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mBigPresenter = presenter;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
