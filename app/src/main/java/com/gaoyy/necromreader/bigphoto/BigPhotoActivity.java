package com.gaoyy.necromreader.bigphoto;

import android.Manifest;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.base.BaseActivity;
import com.gaoyy.necromreader.util.CommonUtils;
import com.squareup.picasso.Picasso;

public class BigPhotoActivity extends BaseActivity implements BigPhotoContract.View, View.OnClickListener
{
    private BigPhotoContract.Presenter mBigPresenter;
    private static final String LOG_TAG = BigPhotoActivity.class.getSimpleName();
    private PhotoView bigImg;
    private Toolbar bigToolbar;
    private LinearLayout bigPhotoTool;
    private TextView bigPhotoDownload;
    private TextView bigPhotoSettingfor;
    private RelativeLayout bigPhotoLayout;


    private DownloadManager downloadManager;
    private DownloadManager.Request request;


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
        String name = getIntent().getStringExtra("name");
        Picasso.with(this)
                .load(url)
                .fit()
                .into(bigImg);
        bigImg.enable();


        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        request = new DownloadManager.Request(Uri.parse(url));

        //指定在WIFI状态下，执行下载操作。
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //指定在MOBILE状态下，执行下载操作
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);

        request.setMimeType("image/*");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);


        //创建目录
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

        //设置文件存放路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);

        Log.i(Constant.TAG, "Downloads Path->" + Environment.DIRECTORY_DOWNLOADS);


        setListener();

    }

    private void setListener()
    {
        bigPhotoDownload.setOnClickListener(this);
        bigPhotoSettingfor.setOnClickListener(this);
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

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.big_photo_download:
                CommonUtils.showToast(this, "don");
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    Log.e(Constant.TAG, "====申请权限=====");

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    {
                        Log.e(Constant.TAG, "===REQUEST_WRITE_EXTERNAL_STORAGE again=====");
                        showRequestPermissionDialog();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, Constant.REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                }
                else
                {
                    CommonUtils.showToast(this, "文件存储权限已授权");
                    long downloadId = downloadManager.enqueue(request);
                    Log.i(Constant.TAG, "download id -->" + downloadId);
                }




                break;
            case R.id.big_photo_settingfor:
                CommonUtils.showToast(this, "se");
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == Constant.REQUEST_WRITE_EXTERNAL_STORAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.e(Constant.TAG, "====PERMISSION_GRANTED=====");
                long downloadId = downloadManager.enqueue(request);
                Log.i(Constant.TAG, "download id -->" + downloadId);
            }
            else
            {
                CommonUtils.showToast(this, "已拒绝文件存储权限的申请，可在手机的设置界面中打开");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showRequestPermissionDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("申请权限")
                .setMessage("应用缺失文件存储权限")
                .setCancelable(false)
                .setPositiveButton("同意", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        ActivityCompat.requestPermissions(BigPhotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //拒绝授权
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
