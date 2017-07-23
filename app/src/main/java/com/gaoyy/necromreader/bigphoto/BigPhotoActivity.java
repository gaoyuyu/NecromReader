package com.gaoyy.necromreader.bigphoto;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.base.BaseActivity;
import com.squareup.picasso.Picasso;

public class BigPhotoActivity extends BaseActivity implements BigPhotoContract.View
{
    private BigPhotoContract.Presenter mBigPresenter;
    private static final String LOG_TAG = BigPhotoActivity.class.getSimpleName();
    private PhotoView bigImg;
    private Toolbar bigToolbar;
    private LinearLayout bigPhotoTool;
    private TextView bigPhotoDownload;
    private TextView bigPhotoSettingfor;
    private RelativeLayout bigPhotoLayout;


    @Override
    protected void initContentView()
    {
        setContentView(R.layout.activity_big_photo);
    }

    @Override
    protected void assignViews()
    {
        super.assignViews();
        bigImg = (PhotoView) findViewById(R.id.big_img);
        bigToolbar = (Toolbar) findViewById(R.id.big_toolbar);
        bigPhotoTool = (LinearLayout) findViewById(R.id.big_photo_tool);
        bigPhotoDownload = (TextView) findViewById(R.id.big_photo_download);
        bigPhotoSettingfor = (TextView) findViewById(R.id.big_photo_settingfor);
        bigPhotoLayout = (RelativeLayout) findViewById(R.id.big_photo_layout);
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
                .fit()
                .into(bigImg);
        bigImg.enable();
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
